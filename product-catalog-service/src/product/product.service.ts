import { BadRequestException, Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { firstValueFrom } from 'rxjs';
import { HttpService } from 'src/common/http.service';
import { products_dummy } from 'src/dummy/products.dummy';
import { CreateProductDto, UpdateProductDto } from 'src/model/dtos/product.dto';
import { CreateRatingDto } from 'src/model/dtos/rating.dto';
import { UserDto } from 'src/model/dtos/user.dto';
import { RatingService } from 'src/rating/rating.service';
import { Product } from '../model/schemas/product.schema';

@Injectable()
export class ProductService {

  constructor(
    @InjectModel('Product') private productModel: Model<Product>,
    private httpService: HttpService,
    private ratingService: RatingService,
  ) { }

  async create(createProductDto: CreateProductDto, blindCreation?: boolean): Promise<Product> {

    let sellerId = createProductDto.sellerId;

    if (!blindCreation) {
      const user = this.httpService.getCurrentUser();

      if (!(user.roles.includes('seller') || !user.roles.includes('admin'))) {
        throw new BadRequestException('Unauthorized');
      }

      if (user.roles.includes('admin') && sellerId) {
        const isValidSeller = await this.validateUserId(sellerId);
        if (!isValidSeller) {
          throw new BadRequestException('Invalid sellerId');
        }
      } else {
        sellerId = user.id;
      }
    }

    const finalCreateProductDto = { ...createProductDto, sellerId };
    const createdProduct = new this.productModel(finalCreateProductDto);
    createdProduct.creationDate = new Date();
    createdProduct.lastUpdateDate = createdProduct.creationDate;
    return createdProduct.save();
  }

  async findAll(): Promise<Product[]> {
    const products = await this.productModel.find().exec();
    return Promise.all(products.map(product => this.fillTransientFields(product?.toObject())));
  }

  async findOne(productId: string): Promise<Product> {
    const product = await this.productModel.findOne({ productId }).exec();
    return this.fillTransientFields(product?.toObject());
  }

  async update(productId: string, updateProductDto: UpdateProductDto): Promise<Product> {
    const user = await this.httpService.getCurrentUser();

    if (!user.roles.includes('seller') && !user.roles.includes('admin')) {
      throw new BadRequestException('Unauthorized');
    }

    const product = await this.productModel.findOne({ productId }).exec();

    if (!product) {
      throw new BadRequestException('Product not found');
    }

    if (!user.roles.includes('seller') && product.sellerId?.toString() !== user.id?.toString()) {
      throw new BadRequestException('Unauthorized');
    }

    if (updateProductDto.sellerId && user.roles.includes('admin')) {
      const isValidSeller = await this.validateUserId(updateProductDto.sellerId);
      if (!isValidSeller) {
        throw new BadRequestException('Invalid sellerId');
      }
      product.sellerId = updateProductDto.sellerId;
    }

    product.name = updateProductDto.name || product.name;
    product.description = updateProductDto.description || product.description;
    product.price = updateProductDto.price || product.price;
    product.category = updateProductDto.category || product.category;
    product.lastUpdateDate = new Date();

    return this.fillTransientFields(await product.save());
  }


  async delete(productId: string): Promise<Product> {
    await this.ratingService.deleteByProduct(productId);
    return this.productModel.findOneAndDelete({ productId }).exec();
  }


  async deleteAllProducts(): Promise<DeleteResult> {
    const user = await this.httpService.getCurrentUser();

    if (!user.roles.includes('admin')) {
      // TODO uncomment this line
      // throw new BadRequestException('Unauthorized');
    }
    this.ratingService.deleteAllRatings(true);
    return this.productModel.deleteMany({}).exec();
  }

  async findBySeller(sellerId: number): Promise<Product[]> {
    const products = await this.productModel.find({ sellerId }).exec();
    return Promise.all(products.map(product => this.fillTransientFields(product?.toObject())));
  }

  async searchByName(name: string): Promise<Product[]> {
    const products = await this.productModel.find({ name: new RegExp(name, 'i') }).exec();
    return Promise.all(products.map(product => this.fillTransientFields(product?.toObject())));
  }

  async findByCategory(category: string): Promise<Product[]> {
    const products = await this.productModel.find({ category }).exec();
    return Promise.all(products.map(product => this.fillTransientFields(product?.toObject())));
  }

  async getTopRated(): Promise<Product[]> {
    const products = await this.productModel.find().exec();
    const productsWithRatings = await Promise.all(products.map(async (product) => {
      const avgRating = await this.ratingService.getAverageRating(product.productId);
      const productObj = product;
      return {
        ...productObj,
        avgRating
      }
    }));
    const topRatedProducts = productsWithRatings.sort((a, b) => b.avgRating - a.avgRating).slice(0, 10);
    return topRatedProducts;
  }

  async getLowStock(): Promise<Product[]> {
    const products = await this.productModel.find().exec();
    const filledProducts = await Promise.all(products.map(product => this.fillTransientFields(product?.toObject())));
    return filledProducts.filter(product => product.inventoryCount < 10);
  }

  private async fillTransientFields(product: Product): Promise<Product | any> {
    if (!product) {
      return null;
    }

    const [sellerName, inventoryCount] = await Promise.all([
      this.fetchSellerName(product.sellerId),
      this.fetchInventoryCount(product.productId),
    ]);

    product.sellerName = sellerName;
    product.inventoryCount = inventoryCount;
    product.ratings = undefined;

    return product;
  }

  async fetchSellerName(sellerId: number) {
    try {
      const response$ = this.httpService.get(`${process.env.USER_MANAGEMENT_SERVICE_URL}/api/users/${sellerId}`);
      const response = await firstValueFrom(response$);
      const userDto: UserDto = response.data as UserDto;
      return `${userDto.firstName} ${userDto.lastName}`;
    } catch (error) {
      return '';
      throw new BadRequestException(`Error fetching seller name with id: ${sellerId}`);
    }
  }


  async fetchInventoryCount(productId: string): Promise<number> {
    // TODO delete this line
    return 143;
    try {
      const response$ = this.httpService.get(`${process.env.INVENTORY_SERVICE_URL}/inventory/${productId}`);
      const response = await firstValueFrom(response$);
      return response.data.count;
    } catch (error) {
      if (error.name === 'EmptyError') {
        return 0; // return 0 if no data was emitted
      } else {
        throw error; // rethrow other errors
      }
    }
  }

  private async validateUserId(userId: number): Promise<boolean> {
    try {
      const response$ = this.httpService.get(`${process.env.USER_MANAGEMENT_SERVICE_URL}/api/users/${userId}`);
      await firstValueFrom(response$);
      return true;
    } catch (error) {
      if (error.response && error.response.status === 404) {
        return false;
      }
      throw error;
    }
  }

  async loadDummyProducts(): Promise<boolean> {
    try {
      const products = products_dummy;
      let index: number = 0;
      for (const productData of products) {
        const existingProduct = await this.productModel.findOne({ name: productData.name }).exec();
        if (!existingProduct) {
          await this.create(productData, true);
          index++;
          for (let j = 1; j <= index * 3; j++) {
            const rating: CreateRatingDto = {
              userId: Math.floor(Math.random() * 15 + 1).toString(),
              rating: Math.floor(Math.random() * 10 + 1),
              review: `Rating ${j} for ${productData.name}`
            };

            await this.ratingService.addRatingToProduct(productData.productId, rating);
          }
        }
      }
      return true;
    } catch (error) {
      throw new BadRequestException(`Failed to load dummy products: ${error.message}`);
    }
  }

  async deleteBySeller(sellerId: number): Promise<{ deletedCount: number }> {
    try {
      const products = await this.findBySeller(sellerId);
      for (const product of products) {
        await this.ratingService.deleteByProduct(product.productId);
      }
      const result = await this.productModel.deleteMany({ sellerId: sellerId }).exec();
      return { deletedCount: result.deletedCount };
    } catch (error) {
      throw new BadRequestException(`Failed to delete products by sellerId: ${error.message}`);
    }
  }

}

import { HttpService } from '@nestjs/axios';
import { BadRequestException, Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { firstValueFrom } from 'rxjs';
import { Product } from '../model/schemas/product.schema';
import { RatingService } from 'src/rating/rating.service';

@Injectable()
export class ProductService {
  constructor(
    @InjectModel('Product') private productModel: Model<Product>,
    private httpService: HttpService,
    private ratingService: RatingService,
  ) { }

  async create(createProductDto: any): Promise<Product> {
    const { sellerId } = createProductDto;
    if (sellerId) {
      const isValidSeller = await this.validateUserId(sellerId);
      if (!isValidSeller) {
        throw new BadRequestException('Invalid sellerId');
      }
    }
    const { ratings, ...dtoWithoutRatings } = createProductDto;
    dtoWithoutRatings.creationDate = new Date();
    dtoWithoutRatings.lastUpdateDate = dtoWithoutRatings.creationDate;
    const createdProduct = new this.productModel(dtoWithoutRatings);
    return createdProduct.save();
  }

  async findAll(): Promise<Product[]> {
    const products = await this.productModel.find().exec();
    return Promise.all(products.map(product => this.fillTransientFields(product)));
  }

  async findOne(productId: string): Promise<Product> {
    const product = await this.productModel.findOne({ productId }).exec();
    return this.fillTransientFields(product);
  }

  async update(productId: string, updateProductDto: any): Promise<Product> {
    const { sellerId } = updateProductDto;
    if (sellerId) {
      const isValidSeller = await this.validateUserId(sellerId);
      if (!isValidSeller) {
        throw new BadRequestException('Invalid sellerId');
      }
    }
    const { ratings, creationDate, ...dtoWithoutRatings } = updateProductDto;
    dtoWithoutRatings.lastUpdateDate = new Date();
    const product = await this.productModel.findOneAndUpdate({ productId }, dtoWithoutRatings, { new: true }).exec();
    return this.fillTransientFields(product);
  }

  async delete(productId: string): Promise<Product> {
    return this.productModel.findOneAndDelete({ productId }).exec();
  }

  async findBySeller(sellerId: string): Promise<Product[]> {
    const products = await this.productModel.find({ sellerId }).exec();
    return Promise.all(products.map(product => this.fillTransientFields(product)));
  }

  async searchByName(name: string): Promise<Product[]> {
    const products = await this.productModel.find({ name: new RegExp(name, 'i') }).exec();
    return Promise.all(products.map(product => this.fillTransientFields(product)));
  }

  async findByCategory(category: string): Promise<Product[]> {
    const products = await this.productModel.find({ category }).exec();
    return Promise.all(products.map(product => this.fillTransientFields(product)));
  }

  async getTopRated(): Promise<Product[]> {
    const products = await this.productModel.find().exec();
        const productsWithRatings = await Promise.all(products.map(async (product) => {
      const avgRating = await this.ratingService.getAverageRating(product.productId);
      const productObj = product.toObject();
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
    const filledProducts = await Promise.all(products.map(product => this.fillTransientFields(product)));
    return filledProducts.filter(product => product.inventoryCount < 10);
  }

  private async fillTransientFields(product: Product): Promise<Product> {
    if (!product) {
      return null;
    }

    const [sellerName, inventoryCount] = await Promise.all([
      this.fetchSellerName(product.sellerId),
      this.fetchInventoryCount(product.productId),
    ]);

    product.sellerName = sellerName;
    product.inventoryCount = inventoryCount;

    return product;
  }

  async fetchSellerName(sellerId: string) {
    const response$ = this.httpService.get(`${process.env.USER_MANAGEMENT_SERVICE_URL}/users/${sellerId}`);
    const response = await firstValueFrom(response$);
    return response.data.name;
  }

  async fetchInventoryCount(productId: string): Promise<number> {
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

  private async validateUserId(userId: string): Promise<boolean> {
    try {
      const response$ = this.httpService.get(`${process.env.USER_MANAGEMENT_SERVICE_URL}/users/${userId}`);
      await firstValueFrom(response$);
      return true;
    } catch (error) {
      if (error.response && error.response.status === 404) {
        return false;
      }
      throw error;
    }
  }

}

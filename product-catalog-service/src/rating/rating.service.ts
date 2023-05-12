import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { HttpService } from 'src/common/http.service';
import { CreateRatingDto, UpdateRatingDto } from 'src/model/dtos/rating.dto';
import { ProductDocument } from '../model/schemas/product.schema';
import { Rating, RatingDocument } from '../model/schemas/rating.schema';

@Injectable()
export class RatingService {
  constructor(
    @InjectModel('Rating') private ratingModel: Model<RatingDocument>,
    @InjectModel('Product') private productModel: Model<ProductDocument>,
    private httpService: HttpService,
  ) { }

  async addRatingToProduct(productId: string, createRatingDto: CreateRatingDto): Promise<Rating> {
    const product = await this.productModel.findOne({ productId });
    if (!product) {
      throw new NotFoundException('Product not found');
    }
    const createdRating = new this.ratingModel({ ...createRatingDto, product: product._id });
    await createdRating.save();

    product.ratings.push(createdRating);
    await product.save();

    return createdRating;
  }

  async getAllRatingsForProduct(productId: string): Promise<Rating[]> {
    const product = await this.productModel.findOne({ productId }).populate('ratings');
    if (!product) {
      throw new NotFoundException('Product not found');
    }
    return product.ratings;
  }

  async updateRating(id: string, updateRatingDto: UpdateRatingDto): Promise<Rating> {
    return this.ratingModel.findByIdAndUpdate(id, updateRatingDto, { new: true }).exec();
  }

  async deleteRating(id: string): Promise<Rating> {
    return this.ratingModel.findByIdAndRemove(id).exec();
  }

  async getAverageRating(productId: string): Promise<number> {
    const product = await this.productModel.findOne({ productId }).populate('ratings');
    if (!product) {
      throw new NotFoundException('Product not found');
    }
    if (!product.ratings.length) {
      return 0;
    }
    const sum = product.ratings.reduce((a, b) => a + b.rating, 0);
    return sum / product.ratings.length;
  }

  async deleteByProduct(productId: string): Promise<void> {
    const product = await this.productModel.findOne({ productId });
    if (!product) {
      throw new NotFoundException('Product not found');
    }
    await this.ratingModel.deleteMany({ product: product._id }).exec();
    product.ratings = [];
    await product.save();
  }

  async deleteAllRatings(blindDelete?: boolean): Promise<void> {
    await this.ratingModel.deleteMany({}).exec();
    if (!blindDelete) {
      const products = await this.productModel.find();
      for (const product of products) {
        product.ratings = [];
        await product.save();
      }
    }
  }
}

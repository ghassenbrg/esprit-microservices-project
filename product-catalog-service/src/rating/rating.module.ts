import { HttpModule } from '@nestjs/axios';
import { Module, forwardRef } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { MongooseModule } from '@nestjs/mongoose';
import { Product, ProductSchema } from 'src/model/schemas/product.schema';
import { ProductModule } from 'src/product/product.module';
import { Rating, RatingSchema } from '../model/schemas/rating.schema';
import { RatingController } from './rating.controller';
import { RatingService } from './rating.service';
import { CommonModule } from 'src/common/common.module';


@Module({
  imports: [
    MongooseModule.forFeature([
      { name: Rating.name, schema: RatingSchema },
      { name: Product.name, schema: ProductSchema },
    ]),
    ConfigModule.forRoot(),
    HttpModule,
    CommonModule,
    forwardRef(() => ProductModule),
  ],
  controllers: [RatingController],
  providers: [RatingService],
  exports: [RatingService],
})
export class RatingModule { }

import { HttpModule } from '@nestjs/axios';
import { Module, forwardRef } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { MongooseModule } from '@nestjs/mongoose';
import { RatingModule } from 'src/rating/rating.module';
import { Product, ProductSchema } from '../model/schemas/product.schema';
import { ProductController } from './product.controller';
import { ProductService } from './product.service';

@Module({
  imports: [
    MongooseModule.forFeature([{ name: Product.name, schema: ProductSchema }]),
    ConfigModule.forRoot(),
    HttpModule,
    forwardRef(() => RatingModule),
  ],
  controllers: [ProductController],
  providers: [ProductService],
})
export class ProductModule { }

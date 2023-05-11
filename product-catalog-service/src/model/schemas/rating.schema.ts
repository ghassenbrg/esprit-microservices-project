import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document, Schema as MongooseSchema } from 'mongoose';
import { Product } from './product.schema';
import { ApiProperty } from '@nestjs/swagger';

export type RatingDocument = Rating & Document;

@Schema()
export class Rating {
  @ApiProperty({ type: String, description: 'Product ID' })
  @Prop({ type: MongooseSchema.Types.ObjectId, ref: 'Product' })
  product: Product;

  @ApiProperty({ description: 'ID of the user who gave the rating' })
  @Prop({ required: true })
  userId: string;

  @ApiProperty({ description: 'Rating given to the product by the user' })
  @Prop({ required: true })
  rating: number;

  @ApiProperty({ description: 'Review given to the product by the user', required: false })
  @Prop()
  review: string;
}

export const RatingSchema = SchemaFactory.createForClass(Rating);

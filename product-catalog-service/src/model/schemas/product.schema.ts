import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document, Schema as MongooseSchema } from 'mongoose';
import { Rating } from './rating.schema';
import { ApiProperty } from '@nestjs/swagger';

export type ProductDocument = Product & Document;

@Schema()
export class Product {
  @ApiProperty({ description: 'Unique product ID' })
  @Prop({ required: true, unique: true })
  productId: string;

  @ApiProperty({ description: 'ID of the seller' })
  @Prop({ required: true })
  sellerId: string;

  @ApiProperty({ description: 'Product name' })
  @Prop({ required: true })
  name: string;

  @ApiProperty({ description: 'Product description', required: false })
  @Prop()
  description: string;

  @ApiProperty({ description: 'Product categories', type: [String], required: false })
  @Prop([String])
  category: string[];

  @ApiProperty({ description: 'Product price' })
  @Prop({ required: true })
  price: number;

  @ApiProperty({ description: 'Product images', type: [String], required: false })
  @Prop([String])
  images: string[];

  @ApiProperty({ description: 'Product ratings', type: [String], required: false })
  @Prop([{ type: MongooseSchema.Types.ObjectId, ref: 'Rating' }])
  ratings: Rating[];

  @ApiProperty({ description: 'Product creation date', default: 'Date.now' })
  @Prop({ default: Date.now })
  creationDate: Date;

  @ApiProperty({ description: 'Product last update date', default: 'Date.now' })
  @Prop({ default: Date.now })
  lastUpdateDate: Date;

  // Transient fields
  @ApiProperty({ description: 'Seller name', required: false })
  sellerName?: string;

  @ApiProperty({ description: 'Inventory count', required: false })
  inventoryCount?: number;
}

export const ProductSchema = SchemaFactory.createForClass(Product);

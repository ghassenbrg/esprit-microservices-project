// product.schema.ts
import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document, Schema as MongooseSchema } from 'mongoose';
import { Rating } from './rating.schema';

export type ProductDocument = Product & Document;

@Schema()
export class Product {
    @Prop({ required: true, unique: true })
    productId: string;

    @Prop({ required: true })
    sellerId: string;

    @Prop({ required: true })
    name: string;

    @Prop()
    description: string;

    @Prop([String])
    category: string[];

    @Prop({ required: true })
    price: number;

    @Prop([String])
    images: string[];

    @Prop([{ type: MongooseSchema.Types.ObjectId, ref: 'Rating' }])
    ratings: Rating[];

    @Prop({ default: Date.now })
    creationDate: Date;

    @Prop({ default: Date.now })
    lastUpdateDate: Date;

    // Transient fields
    sellerName?: string;
    inventoryCount?: number;
}

export const ProductSchema = SchemaFactory.createForClass(Product);

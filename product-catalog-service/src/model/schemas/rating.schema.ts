import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document, Schema as MongooseSchema } from 'mongoose';
import { Product } from './product.schema';

export type RatingDocument = Rating & Document;

@Schema()
export class Rating {
    @Prop({ type: MongooseSchema.Types.ObjectId, ref: 'Product' })
    product: Product;

    @Prop({ required: true })
    userId: string;

    @Prop({ required: true })
    rating: number;

    @Prop()
    review: string;
}

export const RatingSchema = SchemaFactory.createForClass(Rating);

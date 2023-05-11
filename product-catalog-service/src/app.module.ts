import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ProductModule } from './product/product.module';
import { RatingModule } from './rating/rating.module';

@Module({
  imports: [
    MongooseModule.forRoot(process.env.MONGODB_URI),
    ProductModule,
    RatingModule
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule { }

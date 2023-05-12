import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { EurekaModule } from 'nestjs-eureka';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { JwtAuthModule } from './jwt-auth-module/jwt-auth-module.module';
import { ProductModule } from './product/product.module';
import { RatingModule } from './rating/rating.module';

@Module({
  imports: [
    EurekaModule.forRoot({
      disable: process.env.DISABLE_EUREKA === 'true',
      disableDiscovery: process.env.DISABLE_EUREKA === 'true',
      eureka: {
        host: process.env.EUREKA_HOST || 'eureka-server',
        port: process.env.EUREKA_PORT || 8761,
        servicePath: '/eureka/apps',
        maxRetries: 10,
        requestRetryDelay: 10000,
      },
      service: {
        name: 'product-catalog-service',
        port: parseInt(process.env.APP_PORT) || 3000,
      },
    }),
    MongooseModule.forRoot(process.env.MONGODB_URI),
    ProductModule,
    RatingModule,
    JwtAuthModule
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule { }

import { Body, Controller, Delete, Get, Param, Post, Put } from '@nestjs/common';
import { RatingService } from './rating.service';

@Controller('ratings')
export class RatingController {
  constructor(private readonly ratingService: RatingService) {}

  @Post(':productId')
  async addRatingToProduct(@Param('productId') productId: string, @Body() createRatingDto: any) {
    return this.ratingService.addRatingToProduct(productId, createRatingDto);
  }

  @Get(':productId')
  async getAllRatingsForProduct(@Param('productId') productId: string) {
    return this.ratingService.getAllRatingsForProduct(productId);
  }

  @Put(':id')
  async updateRating(@Param('id') id: string, @Body() updateRatingDto: any) {
    return this.ratingService.updateRating(id, updateRatingDto);
  }

  @Delete(':id')
  async deleteRating(@Param('id') id: string) {
    return this.ratingService.deleteRating(id);
  }
}
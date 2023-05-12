import { Body, Controller, Delete, Get, Param, Post, Put, UseGuards } from '@nestjs/common';
import { ApiOperation, ApiParam, ApiResponse, ApiTags } from '@nestjs/swagger';
import { JwtAuthGuard } from 'src/jwt-auth-module/jwt-auth.guard';
import { CreateRatingDto, UpdateRatingDto } from 'src/model/dtos/rating.dto';
import { Rating } from 'src/model/schemas/rating.schema';
import { RatingService } from './rating.service';

@ApiTags('ratings')
@Controller('ratings')
@UseGuards(JwtAuthGuard)
export class RatingController {
  constructor(private readonly ratingService: RatingService) { }

  @Post(':productId')
  @ApiOperation({ summary: 'Add rating to a product' })
  @ApiParam({ name: 'productId', required: true, description: 'Id of the product' })
  @ApiResponse({ status: 201, description: 'The rating has been successfully created', type: Rating })
  async addRatingToProduct(@Param('productId') productId: string, @Body() createRatingDto: CreateRatingDto) {
    return this.ratingService.addRatingToProduct(productId, createRatingDto);
  }

  @Get(':productId')
  @ApiOperation({ summary: 'Get all ratings for a product' })
  @ApiParam({ name: 'productId', required: true, description: 'Id of the product' })
  @ApiResponse({ status: 200, description: 'List of ratings for the product', type: [Rating] })
  async getAllRatingsForProduct(@Param('productId') productId: string) {
    return this.ratingService.getAllRatingsForProduct(productId);
  }

  @Put(':id')
  @ApiOperation({ summary: 'Update a rating' })
  @ApiParam({ name: 'id', required: true, description: 'Id of the rating' })
  @ApiResponse({ status: 200, description: 'The rating has been successfully updated', type: Rating })
  async updateRating(@Param('id') id: string, @Body() updateRatingDto: UpdateRatingDto) {
    return this.ratingService.updateRating(id, updateRatingDto);
  }

  @Delete(':id')
  @ApiOperation({ summary: 'Delete a rating' })
  @ApiParam({ name: 'id', required: true, description: 'Id of the rating' })
  @ApiResponse({ status: 200, description: 'The rating has been successfully deleted', type: Rating })
  async deleteRating(@Param('id') id: string) {
    return this.ratingService.deleteRating(id);
  }
}
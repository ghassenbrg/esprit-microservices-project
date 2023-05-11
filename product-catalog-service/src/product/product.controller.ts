import { Controller, Get, Post, Put, Delete, Param, Body, HttpCode, HttpStatus } from '@nestjs/common';
import { ProductService } from './product.service';
import { Product } from '../model/schemas/product.schema';
import { CreateProductDto, UpdateProductDto } from 'src/model/dtos/product.dto';

@Controller('products')
export class ProductController {
  constructor(private readonly productService: ProductService) {}

  @Post()
  @HttpCode(HttpStatus.CREATED)
  async create(@Body() createProductDto: CreateProductDto): Promise<Product> {
    return this.productService.create(createProductDto);
  }

  @Get()
  async findAll(): Promise<Product[]> {
    return this.productService.findAll();
  }

  @Get(':id')
  async findOne(@Param('id') id: string): Promise<Product> {
    return this.productService.findOne(id);
  }

  @Put(':id')
  async update(@Param('id') id: string, @Body() updateProductDto: UpdateProductDto): Promise<Product> {
    return this.productService.update(id, updateProductDto);
  }

  @Delete(':id')
  @HttpCode(HttpStatus.NO_CONTENT)
  async delete(@Param('id') id: string): Promise<void> {
    await this.productService.delete(id);
  }

  @Get('seller/:sellerId')
  async findBySeller(@Param('sellerId') sellerId: string): Promise<Product[]> {
    return this.productService.findBySeller(sellerId);
  }

  @Get('search/:name')
  async searchByName(@Param('name') name: string): Promise<Product[]> {
    return this.productService.searchByName(name);
  }

  @Get('category/:category')
  async findByCategory(@Param('category') category: string): Promise<Product[]> {
    return this.productService.findByCategory(category);
  }

  @Get('top-rated')
  async getTopRated(): Promise<Product[]> {
    return this.productService.getTopRated();
  }

  @Get('low-stock')
  async getLowStock(): Promise<Product[]> {
    return this.productService.getLowStock();
  }
}

import { Controller, Get, Post, Put, Delete, Param, Body, HttpCode, HttpStatus } from '@nestjs/common';
import { ApiTags, ApiOperation, ApiParam, ApiBody, ApiResponse, ApiBadRequestResponse } from '@nestjs/swagger';
import { ProductService } from './product.service';
import { Product } from '../model/schemas/product.schema';
import { CreateProductDto, UpdateProductDto } from 'src/model/dtos/product.dto';

@ApiTags('products')
@Controller('products')
export class ProductController {
  constructor(private readonly productService: ProductService) {}

  @Post()
  @HttpCode(HttpStatus.CREATED)
  @ApiOperation({ summary: 'Create a new product' })
  @ApiBody({ type: CreateProductDto })
  @ApiResponse({ status: 201, description: 'Product successfully created.', type: Product })
  @ApiBadRequestResponse({ description: 'Invalid payload.' })
  async create(@Body() createProductDto: CreateProductDto): Promise<Product> {
    return this.productService.create(createProductDto);
  }

  @Get()
  @ApiOperation({ summary: 'Retrieve all products' })
  @ApiResponse({ status: 200, description: 'List of all products', type: [Product] })
  async findAll(): Promise<Product[]> {
    return this.productService.findAll();
  }

  @Get(':id')
  @ApiOperation({ summary: 'Retrieve a product by ID' })
  @ApiParam({ name: 'id', required: true, description: 'ID of the product' })
  @ApiResponse({ status: 200, description: 'The product', type: Product })
  @ApiBadRequestResponse({ description: 'Invalid ID.' })
  async findOne(@Param('id') id: string): Promise<Product> {
    return this.productService.findOne(id);
  }

  @Put(':id')
  @ApiOperation({ summary: 'Update a product' })
  @ApiParam({ name: 'id', required: true, description: 'ID of the product' })
  @ApiBody({ type: UpdateProductDto })
  @ApiResponse({ status: 200, description: 'The updated product', type: Product })
  @ApiBadRequestResponse({ description: 'Invalid ID or payload.' })
  async update(@Param('id') id: string, @Body() updateProductDto: UpdateProductDto): Promise<Product> {
    return this.productService.update(id, updateProductDto);
  }

  @Delete(':id')
  @HttpCode(HttpStatus.NO_CONTENT)
  @ApiOperation({ summary: 'Delete a product' })
  @ApiParam({ name: 'id', required: true, description: 'ID of the product' })
  @ApiResponse({ status: 204, description: 'The product has been deleted.' })
  @ApiBadRequestResponse({ description: 'Invalid ID.' })
  async delete(@Param('id') id: string): Promise<void> {
    await this.productService.delete(id);
  }

  @Get('seller/:sellerId')
  @ApiOperation({ summary: 'Retrieve products by seller ID' })
  @ApiParam({ name: 'sellerId', required: true, description: 'ID of the seller' })
  @ApiResponse({ status: 200, description: 'List of products', type: [Product] })
  async findBySeller(@Param('sellerId') sellerId: string): Promise<Product[]> {
    return this.productService.findBySeller(sellerId);
  }

  @Get('search/:name')
  @ApiOperation({ summary: 'Search products by name' })
  @ApiParam({ name: 'name', required: true, description: 'Name of the product' })
  @ApiResponse({ status: 200, description: 'List of matching products', type: [Product] })
  async searchByName(@Param('name') name: string): Promise<Product[]> {
    return this.productService.searchByName(name);
  }

  @Get('category/:category')
  @ApiOperation({ summary: 'Retrieve products by category' })
  @ApiParam({ name: 'category', required: true, description: 'Category of the product' })
  @ApiResponse({ status: 200, description: 'List of products in the category', type: [Product] })
  async findByCategory(@Param('category') category: string): Promise<Product[]> {
    return this.productService.findByCategory(category);
  }

  @Get('top-rated')
  @ApiOperation({ summary: 'Retrieve top rated products' })
  @ApiResponse({ status: 200, description: 'List of top rated products', type: [Product] })
  async getTopRated(): Promise<Product[]> {
    return this.productService.getTopRated();
  }

  @Get('low-stock')
  @ApiOperation({ summary: 'Retrieve products with low stock' })
  @ApiResponse({ status: 200, description: 'List of products with low stock', type: [Product] })
  async getLowStock(): Promise<Product[]> {
    return this.productService.getLowStock();
  }
}
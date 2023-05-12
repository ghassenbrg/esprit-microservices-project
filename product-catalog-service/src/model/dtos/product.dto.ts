import { IsString, IsNotEmpty, IsNumber, IsArray, IsOptional } from 'class-validator';
import { ApiProperty, PartialType } from '@nestjs/swagger';

export class CreateProductDto {
    @ApiProperty({description: 'Unique identifier for the product'})
    @IsString()
    @IsNotEmpty()
    readonly productId: string;

    @ApiProperty({description: 'Unique identifier for the seller'})
    @IsString()
    readonly sellerId: number;

    @ApiProperty({description: 'Product name'})
    @IsString()
    @IsNotEmpty()
    readonly name: string;

    @ApiProperty({description: 'Description of the product', required: false})
    @IsString()
    @IsOptional()
    readonly description: string;

    @ApiProperty({description: 'Categories of the product', required: false})
    @IsArray()
    @IsOptional()
    readonly category: string[];

    @ApiProperty({description: 'Price of the product'})
    @IsNumber()
    @IsNotEmpty()
    readonly price: number;

    @ApiProperty({description: 'Image URLs of the product', required: false})
    @IsArray()
    @IsOptional()
    readonly images: string[];
}

export class UpdateProductDto extends PartialType(CreateProductDto) {}

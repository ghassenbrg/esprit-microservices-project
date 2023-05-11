import { IsString, IsNotEmpty, IsNumber, IsArray, IsOptional } from 'class-validator';
import { PartialType } from '@nestjs/swagger';

export class CreateProductDto {
    @IsString()
    @IsNotEmpty()
    readonly productId: string;

    @IsString()
    @IsNotEmpty()
    readonly sellerId: string;

    @IsString()
    @IsNotEmpty()
    readonly name: string;

    @IsString()
    @IsOptional()
    readonly description: string;

    @IsArray()
    @IsOptional()
    readonly category: string[];

    @IsNumber()
    @IsNotEmpty()
    readonly price: number;

    @IsArray()
    @IsOptional()
    readonly images: string[];

    @IsArray()
    @IsOptional()
    readonly ratings: string[];
}

export class UpdateProductDto extends PartialType(CreateProductDto) {}

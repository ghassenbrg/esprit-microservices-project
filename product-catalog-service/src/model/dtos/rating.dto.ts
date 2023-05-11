import { IsNotEmpty, IsNumber, IsOptional, IsString } from 'class-validator';
import { ApiProperty } from '@nestjs/swagger';

export class CreateRatingDto {
    @ApiProperty({description: 'User ID'})
    @IsString()
    @IsNotEmpty()
    userId: string;

    @ApiProperty({description: 'Rating value'})
    @IsNumber()
    @IsNotEmpty()
    rating: number;

    @ApiProperty({description: 'Review text', required: false})
    @IsString()
    @IsOptional()
    review?: string;
}

export class UpdateRatingDto {
    @ApiProperty({description: 'New rating value', required: false})
    @IsNumber()
    @IsOptional()
    rating?: number;

    @ApiProperty({description: 'New review text', required: false})
    @IsString()
    @IsOptional()
    review?: string;
}
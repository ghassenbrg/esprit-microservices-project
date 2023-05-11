import { Test, TestingModule } from '@nestjs/testing';
import { ProductService } from './product.service';
import { getModelToken } from '@nestjs/mongoose';
import { Product } from '../model/schemas/product.schema';
import { HttpService } from '@nestjs/axios';

describe('ProductService', () => {
  let service: ProductService;
  let httpService: HttpService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductService,
        {
          provide: getModelToken('Product'),
          useValue: {
            new: jest.fn().mockResolvedValue({}),
            constructor: jest.fn().mockResolvedValue({}),
            find: jest.fn().mockResolvedValue([]),
            findOne: jest.fn().mockResolvedValue({}),
            findOneAndUpdate: jest.fn().mockResolvedValue({}),
            findOneAndDelete: jest.fn().mockResolvedValue({}),
            save: jest.fn().mockResolvedValue({})
          },
        },
        {
          provide: HttpService,
          useValue: {
            get: jest.fn().mockImplementation(() => ({
              toPromise: jest.fn().mockResolvedValue({ data: { name: 'sellerName', count: 10 } }),
            })),
          },
        },
      ],
    }).compile();

    service = module.get<ProductService>(ProductService);
    httpService = module.get<HttpService>(HttpService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  it('should create a product', async () => {
    const productDto = {
      name: 'Test',
      description: 'Test Product',
      price: 100,
      sellerId: 'sellerId',
      images: [],
      category: 'test',
      ratings: [],
    };
    const result = await service.create(productDto);
    expect(result).toBeDefined();
  });

  it('should find all products', async () => {
    const result = await service.findAll();
    expect(result).toBeDefined();
  });

  it('should find one product', async () => {
    const result = await service.findOne('productId');
    expect(result).toBeDefined();
  });

  it('should update a product', async () => {
    const productDto = {
      name: 'Test Update',
    };
    const result = await service.update('productId', productDto);
    expect(result).toBeDefined();
  });

  it('should delete a product', async () => {
    const result = await service.delete('productId');
    expect(result).toBeDefined();
  });
});

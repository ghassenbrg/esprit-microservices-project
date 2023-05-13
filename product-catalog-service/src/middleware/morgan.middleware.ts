import { Injectable, NestMiddleware } from '@nestjs/common';
import { Request, Response } from 'express';
import * as morgan from 'morgan';

@Injectable()
export class MorganMiddleware implements NestMiddleware {
    use(req: Request, res: Response, next: () => void) {
        morgan('combined')(req, res, (err) => {
            if (err) {
                // handle the error here
            }
            next();
        });
    }
}

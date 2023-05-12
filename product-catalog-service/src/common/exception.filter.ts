import { ArgumentsHost, Catch, ExceptionFilter, HttpException } from '@nestjs/common';
import { MongoError } from 'mongodb';

@Catch()
export class AllExceptionsFilter implements ExceptionFilter {
    catch(exception: unknown, host: ArgumentsHost) {
        const ctx = host.switchToHttp();
        const response = ctx.getResponse();
        const request = ctx.getRequest();

        let message = 'Internal server error';
        let statusCode = 500;

        if (exception instanceof HttpException) {
            statusCode = exception.getStatus();
            message = exception.message;
        } else if (exception instanceof MongoError) {
            message = exception.message;
        }

        response.status(statusCode).json({
            statusCode: statusCode,
            timestamp: new Date().toISOString(),
            path: request.url,
            message: message,
        });
    }
}

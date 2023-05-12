import { HttpService as NestHttpService } from '@nestjs/axios';
import { Inject, Injectable, Scope } from '@nestjs/common';
import { REQUEST } from '@nestjs/core';
import { AxiosRequestConfig, AxiosResponse } from 'axios';
import { Request } from 'express';
import * as jwt from 'jsonwebtoken';
import { Observable } from 'rxjs';

@Injectable({ scope: Scope.REQUEST })
export class HttpService extends NestHttpService {
    private readonly req: Request;

    constructor(@Inject(REQUEST) request: Request) {
        super();
        this.req = request;
    }

    private getRequestConfig(): AxiosRequestConfig {
        const token = this.req.headers.authorization;
        return {
            headers: {
                Authorization: token,
            },
        };
    }

    get<T = any>(url: string, config: AxiosRequestConfig = {}): Observable<AxiosResponse<T>> {
        return super.get<T>(url, { ...this.getRequestConfig(), ...config });
    }

    post<T = any>(url: string, data?: any, config: AxiosRequestConfig = {}): Observable<AxiosResponse<T>> {
        return super.post<T>(url, data, { ...this.getRequestConfig(), ...config });
    }

    put<T = any>(url: string, data?: any, config: AxiosRequestConfig = {}): Observable<AxiosResponse<T>> {
        return super.put<T>(url, data, { ...this.getRequestConfig(), ...config });
    }

    delete<T = any>(url: string, config: AxiosRequestConfig = {}): Observable<AxiosResponse<T>> {
        return super.delete<T>(url, { ...this.getRequestConfig(), ...config });
    }

    getCurrentUser(): any {
        return {
            "firstName": "John",
            "lastName": "Doe",
            "roles": ["seller"],
            "email": "john.doe@abcd.com",
            "password": "seller",
            "username": "seller1",
            "id": 1
        }
        const token = this.req.headers.authorization?.split(' ')[1]; // Assuming 'Bearer <token>'
        if (!token) {
            return null;
        }

        try {
            const decoded = jwt.decode(token);
            return decoded;
        } catch (err) {
            return null;
        }
    }

}

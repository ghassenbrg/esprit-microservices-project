import { Injectable } from '@nestjs/common';
import { PassportStrategy } from '@nestjs/passport';
import * as jwksRsa from 'jwks-rsa';
import { ExtractJwt, Strategy } from 'passport-jwt';

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
    constructor() {
        super({
            jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
            secretOrKeyProvider: (request, rawJwtToken, done) => {
                const client = jwksRsa({
                    cache: true,
                    rateLimit: true,
                    jwksRequestsPerMinute: 5,
                    jwksUri: process.env.JWKS_URI,
                });

                const header = JSON.parse(Buffer.from(rawJwtToken.split('.')[0], 'base64').toString());
                client.getSigningKey(header.kid, (err, key) => {
                    if (err) {
                        return done(err);
                    }
                    const signingKey = key.getPublicKey();
                    done(null, signingKey);
                });
            },
            audience: process.env.JWT_AUDIENCE,
            issuer: process.env.JWT_ISSUER,
            algorithms: ['RS256'],
        });
    }

    validate(payload: any) {
        return { userId: payload.sub, username: payload.username };
    }
}

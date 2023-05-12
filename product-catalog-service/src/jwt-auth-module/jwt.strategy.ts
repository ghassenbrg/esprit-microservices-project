import { Injectable } from '@nestjs/common';
import { PassportStrategy } from '@nestjs/passport';
import * as jwt from 'jsonwebtoken';
import * as jwksRsa from 'jwks-rsa';
import { ExtractJwt, Strategy, VerifiedCallback } from 'passport-jwt';

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
    constructor() {
        super({
            jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
            secretOrKeyProvider: (req, rawJwtToken, done) => {
                const jwksClient = jwksRsa({
                    cache: true,
                    rateLimit: true,
                    jwksRequestsPerMinute: 5,
                    jwksUri: process.env.JWKS_URI,
                });

                const decodedToken = jwt.decode(rawJwtToken, { complete: true });
                const kid = decodedToken.header.kid;

                jwksClient.getSigningKey(kid, (err, key) => {
                    if (err) {
                        done(err, null);
                    } else {
                        done(null, key.getPublicKey());
                    }
                });
            },
            audience: process.env.JWT_AUDIENCE,
            issuer: process.env.JWT_ISSUER,
            algorithms: ['RS256'],
        });
    }

    async validate(payload: any, done: VerifiedCallback) {
        try {
            // You could add more validation logic here
            done(null, { userId: payload.sub, username: payload.username });
        } catch (err) {
            done(err, false);
        }
    }
}

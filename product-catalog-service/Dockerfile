# Stage 1: Building the code
FROM node:18-alpine AS builder

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

# Stage 2: Running the code
FROM node:18-alpine

WORKDIR /app

COPY --from=builder /app/dist ./dist
COPY package*.json ./
RUN npm install --only=production

CMD ["node", "dist/main"]
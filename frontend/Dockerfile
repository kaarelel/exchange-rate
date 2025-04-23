FROM node:20 AS builder
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build --prod

FROM nginx:alpine
WORKDIR /usr/share/nginx/html

RUN rm -rf ./*
COPY --from=builder /app/dist/exchange-rate-frontend /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

FROM node:22.19.0-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Step 2
FROM nginx_alpine
COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 3000

CMD [ "nginx", "-g", "daemon off;" ]



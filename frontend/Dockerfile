FROM node:22.19.0-alpine

WORKDIR /usr

COPY package*.json .
RUN npm i

COPY . .

EXPOSE 3000

CMD ["npm", "start"]



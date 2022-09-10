FROM node:latest

WORKDIR /usr/frontend

COPY ./package.json /usr/frontend

RUN npm install

COPY . /usr/frontend

EXPOSE 3000

CMD yarn dev
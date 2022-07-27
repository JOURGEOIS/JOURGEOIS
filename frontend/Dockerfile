FROM node:lts-alpine as builder

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

#RUN npm run build


FROM nginx:stable-alpine as production-build
RUN rm -rf /etc/nginx/conf.d/*
COPY --from=builder /app/nginx /etc/nginx/conf.d

RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 80
ENTRYPOINT ["nginx", "-g", "daemon off;"]

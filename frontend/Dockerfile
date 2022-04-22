FROM node as build
WORKDIR /app
COPY package.json .
COPY tsconfig.json .
RUN yarn install
COPY . .
RUN yarn build

FROM nginx
COPY --from=build /app/build /usr/share/nginx/html

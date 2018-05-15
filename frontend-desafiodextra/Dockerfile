FROM node:8.9.4-alpine

# Install git and adjusts the time
RUN apk update && apk add yarn && apk add git && apk add tzdata \
    && cp -r -f /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime

# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Install app dependencies
COPY yarn.lock package.json /usr/src/app/
RUN yarn install

# Bundle app source
COPY . /usr/src/app

RUN yarn run build

EXPOSE 8081
CMD [ "yarn", "run", "deploy" ]

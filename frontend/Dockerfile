FROM node:18-alpine
 
WORKDIR /opt/app/frontend
 
COPY package*.json ./
 
RUN npm install
 
COPY . .
 
CMD [ "npm", "start" ]
# Базовый образ с Node.js (версия LTS)
FROM node:24

WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .

# Установка dev-зависимостей (например, nodemon)
RUN npm install -g nodemon

# Генерация выполнится при запуске, чтобы использовать актуальные файлы из volume
CMD ["sh", "-c", "npm run start"]
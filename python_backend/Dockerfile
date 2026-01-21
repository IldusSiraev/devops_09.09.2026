# Базовый образ
FROM python:3.10-slim

# Рабочая директория внутри контейнера
WORKDIR /app

# Установка зависимостей
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Копируем код приложения
COPY app.py .

# Открываем порт
EXPOSE 5000

# Команда запуска
CMD ["python", "app.py"]

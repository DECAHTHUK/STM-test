# STM-test
Test task for STM Labs
## Заметки
#### 1. Создан docker-compose, чтобы локально протестировать сервис и поотправлять запросы(нужно склонировать репозиторий и прописать команду docker compose up --build). После того, как все сбилдится может понадобиться перезапуск(docker compose down и снова up). Сервис доступен по localhost:8080
#### 2. Swagger документация доступна по http://localhost:8080/swagger-ui/index.html
#### 3. Superuser: 
  login: supersecretAdmin@protonmail.com   
  password: 8J1^sb6bhzr9iF2LMg1P
  
  login payload:
```
{
    "email": "supersecretAdmin@protonmail.com",
    "password": "8J1^sb6bhzr9iF2LMg1P"
}
```
#### 4. Есть миграция с предзаполненными данными и несколькими сущностями для тестирования.
#### 5. Postman коллекция с самыми необходимыми запросами: https://api.postman.com/collections/26554232-febb736f-1d6d-4f9b-90c3-b050afe4d83c?access_key=PMAT-01HB35ETDR5ND4HGKE1FJ65MP7
#### 6. Реализовано все кроме 3-го доп. задания.

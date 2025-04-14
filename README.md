# GRPC Link Shortener — Server

## Инструкция по запуску gRPC-сервера

### 1. Склонируйте репозиторий

```bash
git clone https://github.com/sour-soup/grpc-link-shortener.git
cd grpc-link-shortener
```

---

### 2. Соберите проект

```bash
./gradlew clean build
```

> Убедитесь, что установлен JDK 21 и Docker

---

### 3. Запустите через Docker Compose

```bash
docker-compose up --build
```

- gRPC-сервер будет доступен на порту `9090`
- PostgreSQL — на порту `5432`


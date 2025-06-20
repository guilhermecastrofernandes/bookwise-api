# 📚 BookWise API

API RESTful desenvolvida em Java com Spring Boot usando arquitetura hexagonal (Ports & Adapters) para gerenciar livros, leituras, avaliações e usuários.

---

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 3**
- **Spring Web, Spring Data JPA, Spring Security**
- **PostgreSQL** (ambiente Docker)
- **Swagger (OpenAPI 3)**
- **Arquitetura Hexagonal (Hexagonal Architecture / Clean Architecture)**
- **Testes automatizados com JUnit e Mockito**
- **Maven**
- **Docker & Docker Compose**

---

## 🔧 Executando com Docker

### 1. Build da aplicação

```bash
./mvnw clean package -DskipTests
```

### 2. Subir os containers

```bash
docker-compose up --build
```

A aplicação estará acessível em:  
`http://localhost:8080`

O banco PostgreSQL estará disponível internamente via:  
`jdbc:postgresql://bookwise-db:5432/bookwise`

---

## ✅ Variáveis de Ambiente / Perfis

Use o perfil `docker` para conectar com o banco do container:

```yml
# application-docker.yml
spring:
  datasource:
    url: jdbc:postgresql://bookwise-db:5432/bookwise
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

Certifique-se que o perfil está sendo ativado no container:

```dockerfile
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
```

---

## 🧪 Testes

Execute os testes com:

```bash
./mvnw test
```

---

## 🧭 Swagger / Documentação

Após subir o projeto, acesse:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 📂 Estrutura de Pastas

```bash
.
├── adapters
│   ├── in  → Controllers REST
│   └── out → Persistência (JPA)
├── application
│   └── usecases → Regras de negócio (casos de uso)
├── domain
│   ├── model → Entidades do domínio
│   └── ports → Interfaces (driven/inbound/outbound ports)
├── infrastructure
│   ├── config → Configurações Spring/Swagger/Security
│   └── security → JWT e autenticação
```

---

## 📌 Endpoints Principais

| Método | Endpoint                      | Descrição                                                  |
| ------ | ----------------------------- | ---------------------------------------------------------- |
| POST   | `/auth/login`                 | Autenticar e gerar token JWT                               |
| POST   | `/usuarios`                   | Cadastro de novo usuário                                   |
| PUT    | `/usuarios/{id}`              | Atualizar um usuário por ID                                |
| DELETE | `/usuarios/{id}`              | Remover um usuário por ID                                  |
| POST   | `/api/v1/livros`              | Cadastrar um novo livro                                    |
| GET    | `/api/v1/livros`              | Listar todos os livros do usuário autenticado              |
| PUT    | `/api/v1/livros/{id}`         | Atualizar um livro existente por ID                        |
| DELETE | `/api/v1/livros/{id}`         | Remover um livro por ID                                    |
| GET    | `/api/v1/livros/recomendados` | Listar recomendações de livros com base nos gêneros        |
| GET    | `/api/v1/livros/filtro`       | Filtrar livros por título, autor, gênero e data de leitura |


## Instruções de Uso

### 1. Cadastro do Usuário

- Faça uma requisição `POST /usuarios` para cadastrar um novo usuário.
- Envie no corpo da requisição os dados do usuário (`nome`, `email`, `senha`, `dataNascimento`).
- Exemplo:

```json
{
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "senha": "senha123",
  "dataNascimento": "1990-05-20"
}
```

### 2. Autenticação

- Após o cadastro, faça uma requisição `POST /auth/login` para autenticar o usuário.
- Envie no corpo da requisição o email e senha cadastrados.
- A resposta trará um token JWT para ser usado nas próximas requisições.
- Exemplo:

```json
{
  "email": "joao.silva@example.com",
  "senha": "senha123"
}
```

- Exemplo de resposta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 3. Usando o Token JWT

- Para acessar os endpoints protegidos, inclua o token JWT no header `Authorization` da requisição, no formato:

```
Authorization: Bearer <token>
```

### 4. Gerenciamento de Livros

- Com o token, você pode criar, atualizar, listar, filtrar, deletar livros através dos endpoints `/api/v1/livros`.
- Para cadastrar um livro, faça um `POST /api/v1/livros` enviando os dados do livro no corpo da requisição.
- Exemplo:

```json
{
  "titulo": "Dom Casmurro",
  "autor": "Machado de Assis",
  "anoPublicacao": 1899,
  "generos": ["Romance", "Clássico"],
  "sinopse": "Um romance clássico da literatura brasileira...",
  "lido": true,
  "nota": 5,
  "dataLeitura": "2023-12-01"
}
```

### 5. Recomendação de Livros

- Usuários autenticados podem obter recomendações personalizadas via `GET /api/v1/livros/recomendados`.
- Esse endpoint retorna livros recomendados baseados nos gêneros favoritos do usuário.

---

[![GitHub last commit](https://img.shields.io/github/last-commit/guilhermecastrofernandes/bookwise-api)](https://github.com/guilhermecastrofernandes/bookwise-api)
[![GitHub repo size](https://img.shields.io/github/repo-size/guilhermecastrofernandes/bookwise-api)](https://github.com/guilhermecastrofernandes/bookwise-api)
[![GitHub issues](https://img.shields.io/github/issues/guilhermecastrofernandes/bookwise-api)](https://github.com/guilhermecastrofernandes/bookwise-api/issues)
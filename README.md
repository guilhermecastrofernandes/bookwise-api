# 📚 BookWise API

BookWise é uma API RESTful para gerenciamento e recomendação de livros, desenvolvida com **Java 21**, **Spring Boot**, e arquitetura **hexagonal (ports and adapters)**. O projeto é voltado para estudos de boas práticas de design, testes automatizados e deploy com Docker.

## 🔧 Tecnologias

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- H2 Database
- Swagger / OpenAPI
- Arquitetura Hexagonal
- Docker
- JUnit + Mockito

## 📦 Estrutura Hexagonal

```
com.bookwise
├── adapters       # Interfaces externas (REST, DB)
├── application    # Casos de uso e lógica da aplicação
├── domain         # Modelo de domínio e interfaces
└── infrastructure # Configurações e utilitários
```

## 🚀 Executando o Projeto

### Com Docker

```bash
docker build -t bookwise-api .
docker run -p 8080:8080 bookwise-api
```

### Localmente com Maven

```bash
./mvnw clean install
java -jar target/bookwise-api-0.0.1-SNAPSHOT.jar
```

## 🛠 Variáveis de Ambiente (Render)

No Render, adicione as seguintes variáveis:

| Variável                        | Valor                                                         |
|---------------------------------|---------------------------------------------------------------|
| `SPRING_DATASOURCE_URL`        | `jdbc:h2:file:/data/bookwise;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=FALSE` |
| `SPRING_DATASOURCE_DRIVER`     | `org.h2.Driver`                                               |
| `SPRING_DATASOURCE_USERNAME`   | `sa`                                                          |
| `SPRING_DATASOURCE_PASSWORD`   | *(deixe em branco ou defina)*                                |
| `SPRING_JPA_HIBERNATE_DDL_AUTO`| `update`                                                      |
| `SPRINGDOC_API_DOCS_PATH`      | `/v3/api-docs`                                                |
| `SPRINGDOC_SWAGGER_UI_PATH`    | `/swagger-ui.html`                                           |

A url para testar o mesmo via Render está disponível em:

```
https://bookwise-api.onrender.com/swagger-ui/index.html
```

## 🧪 Testes

Execute os testes com:

```bash
./mvnw test
```

## 📘 Swagger

A documentação da API está disponível em:

```
http://localhost:8080/swagger-ui.html
```

## 🧱 Funcionalidades

- Cadastro e autenticação de usuários
- CRUD de livros
- Marcar livros como lidos
- Avaliação de obras
- Recomendação de leituras

## 📂 Banco de Dados

O projeto usa **H2 Database** embarcado por padrão. O console pode ser acessado em:

```
http://localhost:8080/h2-console
```

- JDBC URL: `jdbc:h2:file:/data/bookwise`
- Usuário: `sa`


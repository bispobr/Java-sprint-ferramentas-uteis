# API REST de Gerenciamento de Ferramentas

API REST desenvolvida em Java e Spring Boot para gerenciamento de ferramentas úteis. Cada ferramenta é cadastrada com título, link, descrição e tags, permitindo organizar e consultar recursos por categorias.

A aplicação possui autenticação baseada em JWT, controle de acesso com Spring Security, persistência em banco de dados, paginação, cache, auditoria e monitoramento.

## Funcionalidades

- Cadastro de ferramentas com título, link, descrição e tags.
- Listagem de ferramentas.
- Busca de ferramentas por uma ou mais tags.
- Listagem paginada de ferramentas.
- Atualização de ferramentas pelo identificador.
- Remoção de ferramentas.
- Registro e autenticação de usuários.
- Autenticação baseada em JWT.
- Controle de acesso por roles com Spring Security.
- Padronização das respostas da API.
- Validação dos dados recebidos.
- Paginação e cache de consultas.
- Auditoria de operações.
- Logs estruturados em JSON.
- Monitoramento com Spring Boot Actuator, Prometheus e Grafana.
- Documentação interativa com OpenAPI e Swagger UI.
- Migrações de banco de dados com Flyway.
- Testes automatizados com JUnit e Mockito.

## Tecnologias

- **Linguagem:** Java 21
- **Framework:** Spring Boot 4
- **API REST:** Spring Web e Bean Validation
- **Segurança:** Spring Security e JWT
- **Persistência:** Spring Data JPA
- **Banco de dados:** PostgreSQL e H2
- **Migrações:** Flyway
- **Cache:** Spring Cache e Caffeine
- **Mapeamento:** MapStruct
- **Documentação:** Springdoc OpenAPI / Swagger UI
- **Observabilidade:** Spring Boot Actuator, Micrometer, Prometheus e Grafana
- **Logs:** Logstash Logback Encoder
- **Testes:** Spring Boot Test, JUnit e Mockito
- **Containerização:** Docker e Docker Compose
- **Build:** Maven
- **Utilitários:** Lombok

## Requisitos

- Java 21 ou superior
- Maven
- PostgreSQL para execução local
- Docker e Docker Compose (opcionais)

## Como executar

### Execução local

1. Clone o repositório:

   ```bash
   git clone https://github.com/bispobr/Java-sprint-ferramentas-uteis.git
   ```

2. Acesse o diretório:

   ```bash
   cd Java-sprint-ferramentas-uteis
   ```

3. Configure as propriedades de conexão com o PostgreSQL conforme o perfil de execução utilizado.

4. Inicie a aplicação com Maven:

   ```bash
   mvn spring-boot:run
   ```

### Execução com Docker Compose

Na raiz do projeto, execute:

```bash
docker compose up --build
```

O ambiente definido no Docker Compose inicia a aplicação, o PostgreSQL, o Prometheus e o Grafana.

Para encerrar os serviços:

```bash
docker compose down
```

## Endpoints

Os endpoints de gerenciamento de ferramentas são protegidos por autenticação. Após realizar o login, envie o token JWT no cabeçalho:

```http
Authorization: Bearer <token>
```

### Autenticação

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `POST` | `/auth/register` | Registra um usuário. |
| `POST` | `/auth/login` | Autentica um usuário e retorna o token JWT. |

#### Registrar usuário

**Requisição**

```http
POST /auth/register
Content-Type: application/json
```

**Corpo**

```json
{
  "name": "Nome do usuário",
  "email": "usuario@example.com",
  "password": "senha",
  "role": "ROLE_USER"
}
```

#### Autenticar usuário

**Requisição**

```http
POST /auth/login
Content-Type: application/json
```

**Corpo**

```json
{
  "email": "usuario@example.com",
  "password": "senha"
}
```

A resposta contém o token JWT utilizado para acessar os endpoints protegidos.

### Gerenciamento de ferramentas

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `POST` | `/tools` | Cadastra uma ferramenta. |
| `GET` | `/tools` | Lista ferramentas, com opção de filtragem por tags. |
| `GET` | `/tools/paginated` | Lista ferramentas com paginação. |
| `PUT` | `/tools/{id}` | Atualiza uma ferramenta pelo identificador. |
| `DELETE` | `/tools/{id}` | Remove uma ferramenta pelo identificador. |

#### Cadastrar ferramenta

**Requisição**

```http
POST /tools
Content-Type: application/json
Authorization: Bearer <token>
```

**Corpo**

```json
{
  "title": "Ferramenta de exemplo",
  "link": "https://example.com",
  "description": "Descrição da ferramenta",
  "tags": ["produtividade", "desenvolvimento"]
}
```

Campos:

- `title`: título da ferramenta, obrigatório.
- `link`: endereço URL válido, obrigatório.
- `description`: descrição da ferramenta, obrigatória.
- `tags`: lista de tags com pelo menos um item.

#### Listar ferramentas

```http
GET /tools
Authorization: Bearer <token>
```

#### Filtrar por tags

Para consultar ferramentas associadas a uma tag:

```http
GET /tools?tag=desenvolvimento
```

Também é possível informar múltiplas tags:

```http
GET /tools?tag=desenvolvimento&tag=produtividade
```

#### Listar ferramentas com paginação

```http
GET /tools/paginated?page=0&size=10
Authorization: Bearer <token>
```

- `page`: número da página, iniciado em zero. Padrão: `0`.
- `size`: quantidade de registros por página. Padrão: `10`.

#### Atualizar ferramenta

```http
PUT /tools/{id}
Content-Type: application/json
Authorization: Bearer <token>
```

**Corpo**

```json
{
  "title": "Ferramenta atualizada",
  "link": "https://example.com",
  "description": "Descrição atualizada",
  "tags": ["utilidades"]
}
```

Substitua `{id}` pelo identificador da ferramenta.

#### Remover ferramenta

```http
DELETE /tools/{id}
Authorization: Bearer <token>
```

Substitua `{id}` pelo identificador da ferramenta que deseja remover.

## Documentação e monitoramento

Com a aplicação em execução, os serviços ficam disponíveis nos endereços padrão abaixo:

| Serviço | URL |
| --- | --- |
| API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| Actuator Health | http://localhost:8080/actuator/health |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3000 |

O Swagger UI permite consultar os endpoints e testar as requisições. O acesso aos endpoints de monitoramento pode depender das configurações de segurança da aplicação.

## Testes

Para executar os testes automatizados:

```bash
mvn test
```

O projeto contém testes para os componentes de controller e service, utilizando a infraestrutura de testes do Spring Boot, JUnit e Mockito.

## Estrutura do projeto

O código está organizado por responsabilidades, incluindo controladores REST, DTOs, configuração, autenticação, tratamento de exceções, logs, mapeadores, modelos, repositórios e serviços. O projeto também contém migrações Flyway, configurações de aplicação, testes automatizados e arquivos de Docker e monitoramento.

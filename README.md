# API REST Gerenciamento de Ferramentas

## Descrição

API REST desenvolvidapara o gerenciamento de ferramentas, contendo as seguintes informações: **título**, **link**, **descrição** e **tags**. Inclui operações de listagem, filtragem por tag, cadastro e remoção de ferramentas, com persistência em banco de dados.  


## 📝 Funcionalidades


- Realiza operações C.R.U.D
- Filtrar ferramentas:** permitir busca por uma *tag* específica.
- Autenticação via JWT com endpoints protegidos por Spring Security e controle por roles (RBAC)
- Padronização de respostas da API com wrapper (success, data, errors, meta)
- Logs estruturados em JSON, métricas com Actuator, integração com Prometheus e dashboards no Grafana
- Auditoria automática com classe de auditoria
- Controle de acesso e rastreabilidade de operações sensíveis
- CORS configurável por ambiente (origins, headers, métodos)
- Suporte a profiles dev, test e prod com configurações isoladas
- Migração de banco versionada com Flyway
- Testes unitários  com JUnit 5 e Mockito
- Documentação da API com Swagger/OpenAPI
- Containerização com Docker e docker-compose
- Uso de MapStruct para mapeamento entre DTOs e entidades
- Lombok para redução de boilerplate

---


## 🛠️ Tecnologias

- Java 21 + Spring Boot
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL / H2
- Flyway
- MapStruct
- Lombok
- Actuator
- Micrometer + Prometheus
- Grafana
- Swagger / OpenAPI
- Docker
- Maven
---

## 📦 Requisitos

- Java 21
- Maven
- PostgreSQL
- Docker(opcional)

## ▶️ Como Executar

## Executando o Projeto (Sem docker)

1. Clone o repositório:
```bash
git https://github.com/bispobr/Java-spring-ferramentas-uteis.git
```
2. Configure o arquivo **.env** com as credenciais do seu ambiente.
3. Execute a aplicação

---

## Executando com Docker (Opcional)

1. Gere a imagem Docker e inicie o container:

```bash
docker compose up --build
```
---

## Acessando a API (Localmente)

- **Base URL:** http://localhost:8080
- **Documentação (Swagger):**  http://localhost:8080/swagger-ui/index.html#/
- **Health Check (Actuator):**  http://localhost:8080/actuator/health
- **Grafana (Docker):**  http://localhost:3000
- usuario: admin
- senha: admin
- **Prometheus (Docker):**  http://localhost:9090
---

## Endpoints da API

### 🔑 Autenticação

1. Registrar Usuário

```http
POST /auth/register
Content-Type: application/json
```

Body

```http
{
  "name": "string",
  "email": "string",
  "password": "string",
  "role": "ROLE_ADMIN"
}
```

---

1. Obter Token JWT

```http
POST /auth/login
Content-Type: application/json
```

Body

```http
{
 "email": "string",
  "password": "string"
}
```

---

1. **Usar token no Swagger**
2. Clique em Authorize
3. informe o token obtido

---
**tool**

1. **Cadastrar Ferramenta**

```http request
POST /tools
Content-Type: application/json
```

**Body**

```http request
{
  "title": "string",
  "link": "string",
  "description": "string",
  "tags": ["string"]
}
```
| Parâmetro | Tipo       | Descrição                           |
|:----------| :--------- | :---------------------------------- |
| `title`    | `String` |   Nome do ferramenta(**Obrigatório**)
| `link`     | `String` |   URL da ferramenta(**Obrigatório**) 
| `description`    | `String` |   Descricao da ferramenta(**Obrigatório**)
| `tags`     | `String` |   Tags da ferrramenta(**Obrigatório**) 

2. **Listar ferramentas**: Retorna a lista de ferramentas com paginação

```http request
GET /tools/paginated?page=0&size=10
```

3. **Listar ferramentas por tags**: Retorna a lista de ferramentas por tags
#### busca Tag unica
```http request
GET /tools?tag=tag
```
#### busca multiplas  Tags 
```http request
GET /tools?tag=node&tag=web
```

4**Atualizar Ferramentas**

```http request
POST /tools/{id}
Content-Type: application/json
```
```
**Body:**
```http request
{
  "title": "string",
  "link": "string",
  "description": "string",
  "tags": ["string"]
}
```

5. **Remover Cliente**

```http request
DELETE /tools/{id}
```




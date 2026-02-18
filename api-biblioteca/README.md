# Sistema Gerenciador de Bibliotecas (API REST)

## 1. Descrição do Problema

Bibliotecas precisam controlar seus livros, exemplares disponíveis, empréstimos, clientes e usuários administrativos (administradores e bibliotecários). Além disso, é comum existir mais de uma biblioteca sob o mesmo sistema (por exemplo, uma rede de bibliotecas), cada uma com seus próprios livros, usuários e clientes.

Sem um sistema centralizado, esse controle tende a ser feito manualmente ou em planilhas, o que gera erros, atrasos e falta de visibilidade sobre estoque, atrasos e histórico de empréstimos.

---

## 2. Objetivo do Sistema

O objetivo deste sistema é fornecer uma **API REST** para gerenciar múltiplas bibliotecas, permitindo:

- Cadastro de bibliotecas
- Cadastro de usuários administrativos (admins e bibliotecários)
- Cadastro de clientes por biblioteca
- Cadastro de livros por biblioteca
- Gestão de estoque (inventory)
- Registro de empréstimos e devoluções
- Consulta de empréstimos por biblioteca e por cliente
- Relatório de empréstimos em atraso
- Autenticação via JWT
- Autorização baseada em papéis (roles) e escopo de biblioteca

O sistema foi desenvolvido com foco em **clareza arquitetural**, **boas práticas** e **separação de responsabilidades**, servindo também como material didático para um curso de Arquitetura de Software da ADA.

---

## 3. Estilo Arquitetural Adotado

O sistema adota um estilo arquitetural de um monolito modular inspirado em:

- **DDD (Domain-Driven Design)**
- **Clean Architecture / Hexagonal Architecture**

Características principais:

- Separação clara entre:
    - Camada de Domínio (regras de negócio)
    - Camada de Aplicação (casos de uso)
    - Camada de Infraestrutura (persistência, segurança, web)
- Uso de **Value Objects** (ex.: `LibraryId`, `UserId`, `LoanId`)
- Repositórios definidos por **interfaces** no domínio e implementados via JPA
- Serviços de aplicação orquestrando casos de uso
- Infraestrutura desacoplada do domínio

Inicialmente o sistema é um **monólito modular**, mas com estrutura que permite a extração futura de serviços (ex.: empréstimos, autenticação) para SOA ou microsserviços.

---

## 4. Diagrama Simples da Arquitetura

Exemplo simplificado de diagrama do projeto, com apenas alguns dos UseCases.

```text
+---------------------+
|     Controller      |
|  (Web / REST API)   |
+----------+----------+
           |
           v
+---------------------+
|  Application Layer  |
|  (Use Cases)        |
|  - RegisterLibrary  |
|  - RegisterBook     |
|  - CreateLoan       |
|  - ReturnLoan       |
|  - GetLoans         |
+----------+----------+
           |
           v
+---------------------+
|     Domain Layer    |
|  (Entities, VO,    |
|   Rules, Repos IF)  |
|  - Library          |
|  - User             |
|  - Book             |
|  - Inventory        |
|  - Loan             |
|  - Client           |
+----------+----------+
           |
           v
+---------------------+
| Infrastructure      |
| (JPA, Security,     |
|  JWT, Mappers)      |
| - JpaRepositories   |
| - JwtService        |
| - SecurityConfig    |
+---------------------+
```

---

## 5. Justificativa das Decisões Arquiteturais

### 5.1 Separação de Camadas

A separação entre domínio, aplicação e infraestrutura foi adotada para:

- Facilitar manutenção e evolução do sistema
- Evitar acoplamento direto entre regras de negócio e frameworks
- Tornar os casos de uso testáveis sem dependência de Spring

### 5.2 DDD e Value Objects

Foram usados Value Objects (`LibraryId`, `UserId`, etc.) para:

- Tornar o domínio mais expressivo
- Evitar erros de troca de identificadores
- Centralizar validações

### 5.3 Repositórios por Interface

Os repositórios são definidos como interfaces no domínio e implementados via JPA na infraestrutura, permitindo:

- Troca futura do mecanismo de persistência
- Isolamento do domínio de detalhes técnicos

### 5.4 Autenticação e Autorização

- Autenticação via **JWT**
- Autorização baseada em:
    - Papel do usuário (ADMIN, LIBRARIAN)
    - Escopo da biblioteca

A autorização não fica apenas no controller; ela é reforçada nos **casos de uso**, garantindo consistência mesmo em chamadas internas.

### 5.5 TokenService Abstrato

Foi criada uma abstração `TokenService` para:

- Evitar acoplamento direto do domínio e da aplicação ao JWT
- Permitir troca futura do mecanismo de geração de token

---

## 6. Instruções para Execução do Projeto

### 6.1 Pré-requisitos

- Java 21
- Maven 3.9+

---

### 6.2 Configuração

O projeto usa banco em memória H2 por padrão.

Arquivo `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:librarydb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driver-class-name: org.h2.Driver
    username: sa
    password: ""

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

  h2:
    console:
      enabled: true
      path: /h2-console

security:
  jwt:
    secret: "chave-super-secreta-para-desenvolvimento"
    expiration-minutes: 60

server:
  port: 8080
```

---

### 6.3 Executar a aplicação

```bash
mvn spring-boot:run
```

---

### 6.4 Fluxo inicial para testes

1) Criar biblioteca + admin

```http
POST /libraries
```

Body:

```json
{
  "libraryName": "Biblioteca Central",
  "libraryEmail": "contato@biblioteca.com",
  "adminName": "Admin",
  "adminEmail": "admin@biblioteca.com",
  "adminPassword": "123456"
}
```

2) Login

```http
POST /auth/login
```

3) Usar o token retornado para acessar endpoints protegidos

```http
Authorization: Bearer <TOKEN>
```
## Documentação da API (Swagger)

A API possui documentação interativa gerada automaticamente via **Swagger (OpenAPI 3)**.

Após subir a aplicação, a documentação pode ser acessada em:

- **Swagger UI:**  
  http://localhost:8080/swagger-ui.html

- **OpenAPI JSON:**  
  http://localhost:8080/v3/api-docs

### Autenticação

A maioria dos endpoints é protegida por **JWT**.

Para testar endpoints protegidos no Swagger:

1. Execute o endpoint de login ou cadastro de biblioteca
2. Copie o token retornado
3. Clique no botão **Authorize 🔒**
4. Informe o token no formato:

```text
Bearer SEU_TOKEN_AQUI
```

## 7. Observações Finais

- O sistema foi projetado para ser facilmente extensível
- Novos serviços (ex.: notificações por e-mail, multas, relatórios avançados) podem ser adicionados sem quebrar a base
- A estrutura atual permite evolução para microserviços ou SOA no futuro

---

> Projeto desenvolvido para fins educacionais em um curso de Arquitetura de Software.

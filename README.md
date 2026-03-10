# 🌐 ForumHub API

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java" />
  <img src="https://img.shields.io/badge/Spring Boot-3.3-brightgreen?style=for-the-badge&logo=springboot" />
  <img src="https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql" />
  <img src="https://img.shields.io/badge/JWT-Auth-purple?style=for-the-badge&logo=jsonwebtokens" />
  <img src="https://img.shields.io/badge/Status-Concluído-success?style=for-the-badge" />
</p>

> Projeto desenvolvido como parte do **Challenge Oracle Next Education (ONE)** em parceria com a **Alura**. O ForumHub é uma API REST para gerenciamento de tópicos de um fórum, com autenticação segura via JWT.

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Como Executar](#-como-executar)
- [Endpoints da API](#-endpoints-da-api)
- [Autenticação](#-autenticação)
- [Autor](#-autor)

---

## 💡 Sobre o Projeto

O **ForumHub** é uma API REST que simula o back-end de um fórum de discussão. Os usuários autenticados podem criar, listar, detalhar, atualizar e excluir tópicos. A API utiliza **JWT (JSON Web Token)** para garantir que apenas usuários autenticados possam interagir com os recursos.

---

## ✅ Funcionalidades

- [x] Cadastro de tópicos
- [x] Listagem de tópicos com paginação
- [x] Filtro de tópicos por curso e ano
- [x] Detalhamento de tópico por ID
- [x] Atualização de tópico
- [x] Exclusão de tópico
- [x] Autenticação com Spring Security + JWT
- [x] Validação de dados com Bean Validation
- [x] Regra de negócio: sem tópicos duplicados
- [x] Migrations com Flyway

---

## 🛠 Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 3.3 |
| Spring Security | 3.3 |
| Spring Data JPA | 3.3 |
| Flyway Migration | 9+ |
| MySQL | 8.0 |
| Lombok | 1.18.30 |
| JWT Auth0 | 4.4.0 |
| Maven | 4+ |

---

## 📁 Estrutura do Projeto

```
src/main/java/com/forumhub/oneforumhub/
│
├── controller/
│   ├── AutenticacaoController.java
│   └── TopicoController.java
│
├── domain/
│   ├── topico/
│   │   ├── Topico.java
│   │   ├── TopicoRepository.java
│   │   ├── StatusTopico.java
│   │   ├── DadosCadastroTopico.java
│   │   ├── DadosListagemTopico.java
│   │   ├── DadosDetalhamentoTopico.java
│   │   └── DadosAtualizacaoTopico.java
│   └── usuario/
│       ├── Usuario.java
│       ├── UsuarioRepository.java
│       ├── DadosAutenticacao.java
│       └── DadosTokenJWT.java
│
└── infra/
    └── security/
        ├── SecurityConfigurations.java
        ├── SecurityFilter.java
        ├── TokenService.java
        └── AutenticacaoService.java

src/main/resources/
├── application.properties
└── db/migration/
    ├── V1__create-table-usuarios.sql
    ├── V2__create-table-topicos.sql
    └── V3__insert-usuario-admin.sql
```

---

## 🚀 Como Executar

### Pré-requisitos

- Java 17+
- Maven 4+
- MySQL 8+

### Passo a passo

**1. Clone o repositório**
```bash
git clone https://github.com/seu-usuario/oneforumhub.git
cd oneforumhub
```

**2. Configure o banco de dados**

Crie um banco MySQL chamado `forumhub` ou deixe o Flyway criar automaticamente. No arquivo `src/main/resources/application.properties`, configure:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forumhub?createDatabaseIfNotExist=true
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

**3. Execute o projeto**
```bash
mvn spring-boot:run
```

O Flyway criará as tabelas automaticamente e inserirá um usuário admin para testes.

**4. Acesse a API**
```
http://localhost:8080
```

---

## 📡 Endpoints da API

### 🔐 Autenticação

| Método | URL | Descrição |
|---|---|---|
| POST | `/login` | Autenticar usuário e obter token JWT |

**Body:**
```json
{
  "email": "admin@forumhub.com",
  "senha": "123456"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### 📝 Tópicos

> Todos os endpoints abaixo exigem o header: `Authorization: Bearer {token}`

| Método | URL | Descrição |
|---|---|---|
| POST | `/topicos` | Cadastrar novo tópico |
| GET | `/topicos` | Listar todos os tópicos |
| GET | `/topicos?curso=Java&ano=2024` | Listar tópicos por curso e ano |
| GET | `/topicos/{id}` | Detalhar um tópico |
| PUT | `/topicos/{id}` | Atualizar um tópico |
| DELETE | `/topicos/{id}` | Excluir um tópico |

**Exemplo de cadastro (POST /topicos):**
```json
{
  "titulo": "Dúvida sobre Spring Boot",
  "mensagem": "Como configurar o Flyway corretamente?",
  "autorEmail": "admin@forumhub.com",
  "curso": "Java"
}
```

**Exemplo de resposta (201 Created):**
```json
{
  "id": 1,
  "titulo": "Dúvida sobre Spring Boot",
  "mensagem": "Como configurar o Flyway corretamente?",
  "dataCriacao": "2024-01-15T10:30:00",
  "status": "ABERTO",
  "autorEmail": "admin@forumhub.com",
  "curso": "Java"
}
```

---

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** para autenticação. O fluxo é:

1. Faça login em `POST /login` com email e senha
2. Copie o token retornado
3. Adicione o token no header de todas as requisições:
```
Authorization: Bearer SEU_TOKEN_AQUI
```

O token expira em **24 horas**.

---

## 👨‍💻 Autor

Desenvolvido com 💙 durante o **Challenge Oracle Next Education (ONE) - Alura**

[![GitHub](https://img.shields.io/badge/GitHub-Matheus--Emanuel-black?style=for-the-badge&logo=github)](https://github.com/matheusefs/)

---

<p align="center">
  Feito com 💙 e muito ☕
</p>

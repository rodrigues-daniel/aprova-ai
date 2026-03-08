# 📚 Concursos — Sistema de Estudos para Concursos Públicos

Aplicação web **monolítica** desenvolvida com **Java 21**, **Spring Boot**, **Vaadin Flow** e **Spring Data JDBC**, usando **records** imutáveis como modelo de domínio e **PostgreSQL** como banco de dados.

---

## 🗂 Sumário

- [Sobre o Projeto](#-sobre-o-projeto)
- [Stack Tecnológica](#-stack-tecnológica)
- [Arquitetura](#-arquitetura)
- [Estrutura de Pastas](#-estrutura-de-pastas)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração do Banco de Dados](#-configuração-do-banco-de-dados)
- [Como Executar](#-como-executar)
- [Funcionalidades](#-funcionalidades)
- [Dados Iniciais (Mock)](#-dados-iniciais-mock)
- [Decisões Técnicas](#-decisões-técnicas)

---

## 📖 Sobre o Projeto

Sistema didático para **estudo de concursos públicos** que permite ao usuário navegar por bancas, concursos e assuntos, além de responder questões de múltipla escolha com feedback imediato de acerto ou erro e placar ao final do simulado.

---

## 🛠 Stack Tecnológica

| Camada | Tecnologia                     |
|---|--------------------------------|
| Linguagem | Java 25                        |
| Framework | Spring Boot 3.3                |
| Frontend | Vaadin Flow 24.4 (server-side) |
| Persistência | Spring Data JDBC               |
| Banco de Dados | PostgreSQL                     |
| Migrations | Flyway                         |
| Build | Gradle                         |

> ⚠️ Este projeto **não usa JPA/Hibernate**. A persistência é feita inteiramente com **Spring Data JDBC** e os modelos de domínio são **Java Records** imutáveis.

---

## 🏛 Arquitetura
```
Vaadin Views (UI server-side)
        │
        ▼
   Services (regras de negócio)
        │
        ▼
  Repositories (Spring Data JDBC)
        │
        ▼
   PostgreSQL (via Flyway migrations)
```

A aplicação é **monolítica**: não há frontend separado. O Vaadin renderiza a interface no servidor e sincroniza com o browser via WebSocket.

---

## 📁 Estrutura de Pastas
```
concursos/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/example/concursos/
    │   │   ├── ConcursosApplication.java
    │   │   ├── config/
    │   │   │   └── DataInitializer.java
    │   │   ├── entity/
    │   │   │   ├── Assunto.java
    │   │   │   ├── Banca.java
    │   │   │   ├── Concurso.java
    │   │   │   └── Questao.java
    │   │   ├── repository/
    │   │   │   ├── AssuntoRepository.java
    │   │   │   ├── BancaRepository.java
    │   │   │   ├── ConcursoRepository.java
    │   │   │   └── QuestaoRepository.java
    │   │   ├── service/
    │   │   │   ├── AssuntoService.java
    │   │   │   ├── BancaService.java
    │   │   │   ├── ConcursoService.java
    │   │   │   └── QuestaoService.java
    │   │   └── view/
    │   │       ├── AssuntosView.java
    │   │       ├── BancasView.java
    │   │       ├── ConcursosView.java
    │   │       ├── InicioView.java
    │   │       ├── MainView.java
    │   │       └── QuestoesView.java
    │   └── resources/
    │       ├── application.yml
    │       └── db/migration/
    │           └── V1__create_schema.sql
    └── test/
        └── java/com/example/concursos/
            └── ConcursosApplicationTests.java
```

---

## ✅ Pré-requisitos

- Java 25+
- Gradle
- PostgreSQL 15+
- (Opcional) Docker para subir o banco rapidamente

---

## 🐘 Configuração do Banco de Dados

### Com Docker (recomendado)
```bash
docker run --name concursos-db \
  -e POSTGRES_DB=concursos_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:15
```

### Manualmente
```sql
CREATE DATABASE concursos_db;
```

> As tabelas são criadas automaticamente pelo **Flyway** na primeira execução a partir do script `V1__create_schema.sql`.

---

## ▶️ Como Executar
```bash
# Clone o repositório
git clone https://github.com/rodrigues-daniel/aprova-ai.git
cd concursos

# Compile e execute
./mvnw spring-boot:run
```

A aplicação estará disponível em:
```
http://localhost:8080
```

---

## 🖥 Funcionalidades

| Tela | Descrição |
|---|---|
| **Início** | Boas-vindas e acesso rápido às questões |
| **Bancas** | Lista todas as bancas organizadoras |
| **Concursos** | Lista concursos com filtro por banca |
| **Assuntos** | Lista todos os assuntos disponíveis |
| **Questões** | Simulado com filtro por concurso e assunto, feedback de acerto/erro e placar final |

---

## 🗄 Dados Iniciais (Mock)

Ao iniciar a aplicação pela primeira vez, o `DataInitializer` insere automaticamente:

**Banca**
- CEBRASPE

**Concurso**
- TCE-RN 2024 (Banca: CEBRASPE)

**Assuntos**
- Direito Constitucional
- Direito Administrativo
- Controle Externo
- Tecnologia da Informação

**Questões**
- 5 questões de múltipla escolha distribuídas entre os assuntos acima

---

## 💡 Decisões Técnicas

**Records em vez de classes mutáveis**
Os modelos de domínio são `records` Java, garantindo imutabilidade, `equals`, `hashCode` e `toString` automáticos e código mais conciso.

**Spring Data JDBC em vez de JPA/Hibernate**
Evita complexidades do contexto de persistência JPA (lazy loading, proxies, sessões), tornando as consultas explícitas e o comportamento previsível.

**Flyway para migrations**
O schema do banco é versionado e reproduzível. Não há `ddl-auto: update` em produção.

**Vaadin server-side**
Toda a lógica de UI roda no servidor. Não há API REST, não há framework JavaScript separado.

**Cache local de IDs nas Views**
Como os records não carregam relacionamentos automaticamente, as views mantêm `Map<Long, Entidade>` em memória para resolver nomes sem consultas extras ao banco a cada renderização.

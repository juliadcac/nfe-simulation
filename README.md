# nfe-simulation

Projeto desenvolvido com **Quarkus** para simular o processo de **emissão de Nota Fiscal Eletrônica**. Inclui cadastros de emitentes, produtos e emissão de NF-e com validações fiscais, geração de XML simulado e protocolo fictício.

---

## 🛠 Tecnologias utilizadas

- Java 17+
- Quarkus 3.x
- Jakarta REST (JAX-RS)
- Hibernate ORM com Panache
- Bean Validation
- OpenAPI (Swagger)
- Banco H2 (modo memória, padrão)
- JUnit 5 para testes

---

## 🚀 Como executar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/juliadcac/nfe-simulation.git
cd nfe-simulation
```

### 2. Executar em modo desenvolvimento

Para executar o projeto é necessário ter instalado Java 17+, Maven 3.9+ e Quarkus 3.x

```bash
./mvnw quarkus:dev
```

O Quarkus iniciará a aplicação em:  
📍 `http://localhost:8080`

---

## 📄 Documentação da API (Swagger UI)

Você pode visualizar e testar todos os endpoints da API através da interface Swagger:

🔗 **http://localhost:8080/swagger-ui**

---

## 🔧 Exemplos de Requisições

### ▶️ Cadastrar Emitente

`POST /emitters`

```json
{
  "cnpj": "12345678000195",
  "companyName": "Empresa Exemplo",
  "ie": "12345678",
  "uf": "SC"
}
```

### ▶️ Cadastrar Produto

`POST /products`

```json
{
  "code": "P001",
  "name": "Produto Exemplo",
  "cfop": "5102",
  "ncm": "12345678",
  "unitValue": 150.00
}
```

### ▶️ Emitir NF-e (simulação simples)

`POST /nfe`

```json
{
  "emitterCNPJ": "12345678000195",
  "recipientCnpjCpf": "98765432100",
  "recipientName": "Cliente Teste",
  "recipientUF": "SP",
  "items": [
    {
      "productCode": "P001",
      "quantity": 2
    }
  ]
}
```

### ▶️ Emitir NF-e com geração de XML

`POST /nfe/send`

Retorna um XML simulado da nota com protocolo fictício.

---

## 🔒 Validações implementadas

- ✅ CNPJ do emitente único e obrigatório
- ✅ Código do produto único e obrigatório
- ✅ CFOP e NCM com comprimento mínimo
- ✅ Quantidade dos itens deve ser ≥ 1
- ✅ Operações interestaduais detectadas automaticamente via UF do destinatário
- ✅ Geração de XML estruturado com dados de nota, tributos e protocolo simulado

---

## 🐞 Debug da aplicação

Para executar com suporte a depuração remota (porta 5005):

```bash
./mvnw quarkus:dev -Ddebug
```

Ou apenas:

```bash
quarkus dev
```

Conecte sua IDE à porta `5005` (modo *Remote Debug*).

---

## 🧪 Executar os testes

```bash
./mvnw test
```

---

## 🗃 Banco de dados

O projeto utiliza **H2 em memória**. Durante o desenvolvimento, você pode visualizar o console via:

🔗 **http://localhost:8080/q/dev**

---

## 📁 Organização do projeto

```
src/
├── main/
│   ├── java/
│   │   └── com.seuprojeto.nfe/
│   │       ├── domain/      → Entidades (JPA)
│   │       ├── dto/         → DTOs de entrada
│   │       ├── service/     → Lógica de negócios
│   │       ├── resource/    → Endpoints REST
│   │       ├── repository/  → Repositórios com Panache
│   │       └── util/        → Geradores de XML
│   │       └── validation/  → Bean validations
│   └── resources/
│       └── application.properties
├── test/
│   ├── java/
│   │   └── com.seuprojeto.nfe/
│   │       ├── service/     → Testes unitários dos Services
│   │       ├── validation/  → Teste de validação de entidades
```

---

## 🧩 Considerações Finais

Este projeto tem fins didáticos e de prototipagem. Não representa a complexidade completa de uma NF-e real.

---

## 📄 Licença

Uso livre para fins de estudo, testes ou internos.

---
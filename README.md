# 🚗 Sistema de Aluguel de Carros - API REST

## 📋 Visão Geral

API REST desenvolvida com **Micronaut Framework** para gerenciamento de aluguel de carros. O sistema gerencia clientes, agentes (bancos e empresas), automóveis, pedidos de aluguel, contratos e rendimentos.

## 🏗️ Estrutura do Projeto

```
src/main/java/com/aluguel/
├── Application.java                    # Classe principal da aplicação
│
├── domain/                             # Domínios da aplicação
│   ├── cliente/                        # Gestão de clientes
│   │   ├── Cliente.java               # Entidade
│   │   ├── ClienteController.java     # REST endpoints
│   │   ├── ClienteService.java        # Lógica de negócio
│   │   └── ClienteRepository.java     # Acesso a dados
│   ├── agente/                         # Gestão de agentes (Banco/Empresa)
│   ├── automovel/                      # Gestão de automóveis
│   ├── contrato/                       # Gestão de contratos
│   ├── pedido/                         # Gestão de pedidos de aluguel
│   └── rendimento/                     # Gestão de rendimentos
│
├── common/                             # Código compartilhado
│   ├── dto/                            # Data Transfer Objects
│   │   └── ApiResponse.java           # Response genérico da API
│   ├── exception/                      # Exceções customizadas
│   │   ├── RecursoNaoEncontradoException.java
│   │   └── DadosInvalidosException.java
│   └── config/                         # Configurações globais
│
├── infrastructure/                     # Infraestrutura
│   └── util/                          # Utilitários
│       └── DataInitializer.java       # Inicialização de dados
│
└── config/                             # Configurações (legacy)
    └── MicronautConfig.java           # (a mover para common/config)
```

## 🚀 Iniciando a Aplicação

### Pré-requisitos
- Java 25+
- Maven 3.8+

### Executar localmente

```bash
cd lab2
./mvnw mn:run
```

O servidor iniciará em `http://localhost:8080`

## 📡 Endpoints da API

### 👥 Clientes

```
GET    /clientes              # Lista todos os clientes
GET    /clientes/{id}         # Obtém um cliente específico
POST   /clientes              # Cria novo cliente
PUT    /clientes/{id}         # Atualiza cliente
DELETE /clientes/{id}         # Deleta cliente
```

**Exemplo de requisição POST:**
```json
{
  "login": "joao_silva",
  "senha": "senha123",
  "nome": "João Silva",
  "endereco": "Rua A, 123",
  "rg": "123456789",
  "cpf": "123.456.789-00",
  "profissao": "Engenheiro"
}
```

### 🏢 Agentes (Bancos e Empresas)

```
GET    /agentes               # Lista todos os agentes
GET    /agentes/{id}          # Obtém um agente específico
POST   /agentes               # Cria novo agente
PUT    /agentes/{id}          # Atualiza agente
DELETE /agentes/{id}          # Deleta agente
```

**Exemplo de requisição POST:**
```json
{
  "login": "banco_brasil",
  "senha": "senha123",
  "nome": "Banco do Brasil",
  "endereco": "Avenida Paulista, 1000",
  "cnpj": "00.360.305/0001-60"
}
```

### 🚙 Automóveis

```
GET    /automóveis            # Lista todos os automóveis
GET    /automóveis/{id}       # Obtém um automóvel específico
POST   /automóveis            # Cria novo automóvel
PUT    /automóveis/{id}       # Atualiza automóvel
DELETE /automóveis/{id}       # Deleta automóvel
```

### 📋 Pedidos de Aluguel

```
GET    /pedidos               # Lista todos os pedidos
GET    /pedidos/{id}          # Obtém um pedido específico
POST   /pedidos               # Cria novo pedido
PUT    /pedidos/{id}          # Atualiza pedido
DELETE /pedidos/{id}          # Deleta pedido
```

### 📄 Contratos

```
GET    /contratos             # Lista todos os contratos
GET    /contratos/{id}        # Obtém um contrato específico
POST   /contratos             # Cria novo contrato
PUT    /contratos/{id}        # Atualiza contrato
DELETE /contratos/{id}        # Deleta contrato
```

### 💰 Rendimentos

```
GET    /rendimentos           # Lista todos os rendimentos
GET    /rendimentos/{id}      # Obtém um rendimento específico
POST   /rendimentos           # Cria novo rendimento
PUT    /rendimentos/{id}      # Atualiza rendimento
DELETE /rendimentos/{id}      # Deleta rendimento
```

## 🔧 Tecnologias Utilizadas

- **Framework:** Micronaut 4.10.10
- **Linguagem:** Java 25
- **Build:** Maven
- **Runtime:** Netty
- **Serialização:** Jackson
- **Logging:** Logback
- **Testes:** JUnit 5

## 📊 Modelos de Dados

### Cliente
- ID, Login, Senha, Nome, Endereço, RG, CPF, Profissão

### Agente (Banco/Empresa)
- ID, Login, Senha, Nome, Endereço, CNPJ

### Automóvel
- ID, Placa, Marca, Modelo, Ano, Status (Disponível/Alugado)

### Pedido de Aluguel
- ID, ClienteID, AutomóvelID, DataInício, DataFim, Status

### Contrato
- ID, Número, AgenteID, ClienteID, DataAssinatura, Valor

### Rendimento
- ID, AgenteID, Valor, Data, Descricao

## 🏃 Padrões de Código

### Estrutura de Camadas
- **Controller:** Recebe requisições HTTP e coordena respostas
- **Service:** Contém lógica de negócio
- **Repository:** Acessa dados (em memória)

### Tratamento de Erros
- Exceções customizadas em `com.aluguel.common.exception`
- Responses padronizadas com `ApiResponse<T>`

### DTOs
- Localizados em `com.aluguel.common.dto`
- Utilizados para transferência de dados entre camadas

## 📝 Compilar e Testar

```bash
# Compilar
./mvnw compile

# Executar testes
./mvnw test

# Build completo
./mvnw clean package

# Executar JAR gerado
java -jar target/lab2-0.1.jar
```

## 📚 Documentação Adicional

- Arquivo de configuração: `src/main/resources/application.properties`
- Logs: `src/main/resources/logback.xml`
- Dados de inicialização: `com.aluguel.infrastructure.util.DataInitializer`

## Como Rodar o Projeto

### Caminho do Projeto

Navegue até a pasta do projeto no terminal:

```bash
cd Codigo/lab2/lab2
```

### Pré-requisitos

- **Java 25** ou superior (LTS com Java 21+)
- **Maven 3.8+**

### 1. Compilar e Gerar o JAR

Execute o comando Maven para compilar e gerar o arquivo executável:

```bash
./mvnw clean package -DskipTests
```

Este comando irá:
- Limpar arquivos anteriores
- Compilar o código-fonte
- Gerar o arquivo `target/lab2-0.1.jar`

### 2. Rodar a Aplicação

Após a compilação, execute o JAR:

```bash
java -jar target/lab2-0.1.jar
```

A aplicação iniciará na porta **8080**: `http://localhost:8080`

Você verá a mensagem:
```
Startup completed in XXX ms. Server Running: http://localhost:8080
```

### 3. Acessar a Aplicação

- **Interface Web**: http://localhost:8080 (redireciona para login)
- **Login**: http://localhost:8080/index.html
- **Cadastro**: http://localhost:8080/register.html
- **Dashboard Cliente**: http://localhost:8080/dashboard-cliente.html
- **Dashboard Agente**: http://localhost:8080/dashboard-agente.html

### 4. Endpoints da API

A aplicação fornece uma API REST completa para gerenciamento de todos os recursos:

**Clientes**
- `GET /clientes` - Listar todos os clientes
- `POST /clientes` - Criar um novo cliente
- `GET /clientes/{id}` - Obter um cliente específico
- `GET /clientes/cpf/{cpf}` - Obter cliente por CPF
- `PUT /clientes/{id}` - Atualizar um cliente
- `DELETE /clientes/{id}` - Deletar um cliente

**Agentes (Banco/Empresa)**
- `GET /agentes` - Listar todos os agentes
- `POST /agentes` - Criar um novo agente
- `GET /agentes/{id}` - Obter um agente específico
- `GET /agentes/cnpj/{cnpj}` - Obter agente por CNPJ
- `PUT /agentes/{id}` - Atualizar um agente
- `DELETE /agentes/{id}` - Deletar um agente

**Automóveis**
- `GET /automoveis` - Listar todos os automóveis
- `POST /automoveis` - Criar um novo automóvel
- `GET /automoveis/{id}` - Obter um automóvel específico
- `GET /automoveis/placa/{placa}` - Obter automóvel por placa
- `PUT /automoveis/{id}` - Atualizar um automóvel
- `DELETE /automoveis/{id}` - Deletar um automóvel

**Pedidos de Aluguel**
- `GET /pedidos` - Listar todos os pedidos
- `POST /pedidos` - Criar um novo pedido
- `GET /pedidos/{id}` - Obter um pedido específico
- `GET /pedidos/cliente/{clienteId}` - Listar pedidos de um cliente
- `PUT /pedidos/{id}` - Atualizar um pedido
- `DELETE /pedidos/{id}` - Deletar um pedido
- `PUT /pedidos/{id}/aprovar` - Aprovar um pedido
- `PUT /pedidos/{id}/rejeitar` - Rejeitar um pedido
- `PUT /pedidos/{id}/cancelar` - Cancelar um pedido

**Contratos**
- `GET /contratos` - Listar todos os contratos
- `POST /contratos` - Criar um novo contrato
- `GET /contratos/{id}` - Obter um contrato específico
- `PUT /contratos/{id}` - Atualizar um contrato
- `DELETE /contratos/{id}` - Deletar um contrato
- `PUT /contratos/{id}/assinar` - Assinar um contrato

**Rendimentos**
- `GET /rendimentos` - Listar todos os rendimentos
- `POST /rendimentos` - Criar um novo rendimento
- `GET /rendimentos/{id}` - Obter um rendimento específico
- `PUT /rendimentos/{id}` - Atualizar um rendimento
- `DELETE /rendimentos/{id}` - Deletar um rendimento

### 5. Rodar os Testes

```bash
./mvnw test
```

### 6. Limpar Artifacts de Build

Para remover arquivos gerados durante a compilação:

```bash
./mvnw clean
```

## Funcionalidades Principais

### Para Clientes
- ✅ Cadastro e login
- ✅ Visualizar automóveis disponíveis
- ✅ Criar e gerenciar pedidos de aluguel
- ✅ Cancelar pedidos pendentes
- ✅ Gerenciar rendimentos (máximo 3)
- ✅ Visualizar status dos pedidos

### Para Agentes (Banco/Empresa)
- ✅ Visualizar e avaliar pedidos
- ✅ Aprovar ou rejeitar pedidos
- ✅ Gerenciar clientes
- ✅ Gerenciar automóveis
- ✅ Gerenciar contratos
- ✅ Assinar contratos

## Estrutura do Projeto

```
lab2/
├── src/
│   ├── main/
│   │   ├── java/com/aluguel/
│   │   │   ├── Application.java
│   │   │   ├── controller/        # REST Controllers
│   │   │   ├── model/             # Entidades/Models
│   │   │   ├── repository/        # Data Access Layer
│   │   │   └── service/           # Business Logic
│   │   └── resources/
│   │       ├── public/            # Frontend (HTML/CSS/JS)
│   │       ├── application.properties
│   │       └── logback.xml
│   └── test/
└── pom.xml
```

## Tecnologias Utilizadas

- **Framework**: Micronaut 4.10.10
- **Linguagem**: Java 25
- **Build Tool**: Maven
- **Frontend**: HTML5 + CSS3 + JavaScript Vanilla
- **API**: REST com JSON

## Notas Importantes

- O sistema utiliza armazenamento em memória (pode ser migrado para banco de dados)
- A autenticação é armazenada nos objetos de usuário
- CORS está habilitado para desenvolvimento
- Arquivo estáticos são servidos da pasta `/src/main/resources/public`

## 👥 Autor

**Laboratório de Desenvolvimento de Software - LAB02**
PUC Minas - 2026

## 📄 Licença

Este projeto é parte de uma atividade acadêmica.




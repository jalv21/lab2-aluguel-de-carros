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

## 👥 Autor

**Laboratório de Desenvolvimento de Software - LAB02**
PUC Minas - 2026

## 📄 Licença

Este projeto é parte de uma atividade acadêmica.

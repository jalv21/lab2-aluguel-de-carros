# 🚗 Sistema de Aluguel de Carros - API REST

## 📋 Visão Geral

API REST desenvolvida com **Micronaut Framework** para gerenciamento completo de aluguel de carros. O sistema oferece uma interface web intuitiva com formulários CRUD para gerenciar clientes, agentes (bancos e empresas), automóveis, pedidos de aluguel, contratos e rendimentos. Inclui validações de dados, máscaras de entrada, sistema de status com color-coding e uma experiência de usuário aprimorada.

## ✨ Funcionalidades Principais

### 📝 Validações e Formatação
- **14 Validadores**: Campos obrigatórios, comprimento mínimo/máximo, CPF/CNPJ, email, datas, valores numéricos, padrões customizados
- **7 Máscaras de Entrada**: CPF (000.000.000-00), CNPJ (00.000.000/0000-00), Telefone ((00) 00000-0000), Placa (ABC-1234), Moeda (R$ format), Percentual, Data (DD/MM/YYYY)
- **Validação em Tempo Real**: Feedback instantâneo ao sair do campo com bordas vermelhas para erros e verdes para sucesso

### 🎯 Sistema de Status
- **6 Status de Contrato**: Pendente, Ativo, Assinado, Vencido, Cancelado, Em Revisão
- **7 Status de Pedido**: Pendente, Aprovado, Rejeitado, Cancelado, e mais
- **Color-Coding**: Cada status possui uma cor distinta para fácil identificação visual
- **Ícones Emoji**: Representação visual intuitiva dos estados

### 🖼️ Interface Web Completa
- **6 Páginas CRUD Dinâmicas**:
  - Clientes (CPF, RG, profissão)
  - Automóveis (placa, marca, modelo, ano)
  - Contratos (número, tipo, status)
  - Pedidos de Aluguel (cliente, veículo, data, status)
  - Agentes/Empresas (CNPJ)
  - Rendimentos (empregador, valor)
- **Formulários Inteligentes**: Gerados dinamicamente com campos validados
- **Operações CRUD Completas**: Criar, listar, editar, deletar com interface responsiva
- **Ações Especiais**: Aprovar/Rejeitar pedidos, Assinar contratos, Cancelar pedidos

### 🔄 Integração Frontend-Backend
- **JavaScript Vanilla Moderno (ES6+)**: Sem dependências externas
- **Comunicação REST**: Requisições HTTP com validação antes do envio
- **Tratamento de Erros**: Mensagens claras ao usuário em caso de falha
- **Scripts Organizados**:
  - `masks.js`: Aplicação de máscaras nos campos
  - `validation.js`: Framework de validação em tempo real
  - `field-config.js`: Definição centralizada de campos e validadores
  - `status-enum.js`: Enumeração de status com cores e ícones

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
GET    /automoveis            # Lista todos os automóveis
GET    /automoveis/{id}       # Obtém um automóvel específico
POST   /automoveis            # Cria novo automóvel
PUT    /automoveis/{id}       # Atualiza automóvel
DELETE /automoveis/{id}       # Deleta automóvel
GET    /automoveis/placa/{placa} # Busca automóvel por placa
```

### 📋 Pedidos de Aluguel

```
GET    /pedidos-aluguel                      # Lista todos os pedidos
GET    /pedidos-aluguel/{id}                 # Obtém um pedido específico
POST   /pedidos-aluguel                      # Cria novo pedido
PUT    /pedidos-aluguel/{id}                 # Atualiza pedido
DELETE /pedidos-aluguel/{id}                 # Deleta pedido
GET    /pedidos-aluguel/cliente/{clienteId}  # Busca pedidos de um cliente
POST   /pedidos-aluguel/{id}/aprovar         # Aprova um pedido
POST   /pedidos-aluguel/{id}/rejeitar        # Rejeita um pedido
POST   /pedidos-aluguel/{id}/cancelar        # Cancela um pedido
```

### 📄 Contratos

```
GET    /contratos                  # Lista todos os contratos
GET    /contratos/{id}             # Obtém um contrato específico
POST   /contratos                  # Cria novo contrato
PUT    /contratos/{id}             # Atualiza contrato
DELETE /contratos/{id}             # Deleta contrato
GET    /contratos/numero/{numero}  # Busca contrato por número
POST   /contratos/{id}/assinar     # Assina um contrato
```

### 💰 Rendimentos

```
GET    /rendimentos        # Lista todos os rendimentos
GET    /rendimentos/{id}   # Obtém um rendimento específico
POST   /rendimentos        # Cria novo rendimento
PUT    /rendimentos/{id}   # Atualiza rendimento
DELETE /rendimentos/{id}   # Deleta rendimento
```

### 🖼️ Páginas CRUD (Frontend)

```
GET    /clientes/page           # Página CRUD de Clientes
GET    /automoveis/page         # Página CRUD de Automóveis
GET    /contratos/page          # Página CRUD de Contratos
GET    /pedidos-aluguel/page    # Página CRUD de Pedidos
GET    /agentes/page            # Página CRUD de Agentes
GET    /rendimentos/page        # Página CRUD de Rendimentos
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
- `GET /pedidos-aluguel` - Listar todos os pedidos
- `POST /pedidos-aluguel` - Criar um novo pedido
- `GET /pedidos-aluguel/{id}` - Obter um pedido específico
- `GET /pedidos-aluguel/cliente/{clienteId}` - Listar pedidos de um cliente
- `PUT /pedidos-aluguel/{id}` - Atualizar um pedido
- `DELETE /pedidos-aluguel/{id}` - Deletar um pedido
- `POST /pedidos-aluguel/{id}/aprovar` - Aprovar um pedido
- `POST /pedidos-aluguel/{id}/rejeitar` - Rejeitar um pedido
- `POST /pedidos-aluguel/{id}/cancelar` - Cancelar um pedido

**Contratos**
- `GET /contratos` - Listar todos os contratos
- `POST /contratos` - Criar um novo contrato
- `GET /contratos/{id}` - Obter um contrato específico
- `GET /contratos/numero/{numero}` - Obter contrato por número
- `PUT /contratos/{id}` - Atualizar um contrato
- `DELETE /contratos/{id}` - Deletar um contrato
- `POST /contratos/{id}/assinar` - Assinar um contrato

**Rendimentos**
- `GET /rendimentos` - Listar todos os rendimentos
- `POST /rendimentos` - Criar um novo rendimento
- `GET /rendimentos/{id}` - Obter um rendimento específico
- `PUT /rendimentos/{id}` - Atualizar um rendimento
- `DELETE /rendimentos/{id}` - Deletar um rendimento

**Contratos**
- `GET /contratos` - Listar todos os contratos
- `POST /contratos` - Criar um novo contrato
- `GET /contratos/{id}` - Obter um contrato específico
- `GET /contratos/numero/{numero}` - Obter contrato por número
- `PUT /contratos/{id}` - Atualizar um contrato
- `DELETE /contratos/{id}` - Deletar um contrato
- `POST /contratos/{id}/assinar` - Assinar um contrato

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
- ✅ Acesso via página CRUD intuitiva
- ✅ Cadastro de clientes com validação de CPF e RG
- ✅ Visualizar e gerenciar pedidos de aluguel
- ✅ Edição de dados com máscaras de entrada
- ✅ Visualizar status de pedidos com color-coding

### Para Agentes (Banco/Empresa)
- ✅ Interface CRUD completa para gerenciamento de agentes
- ✅ Visualizar e avaliar pedidos de aluguel
- ✅ Aprovar, rejeitar ou cancelar pedidos
- ✅ Gerenciar frota de automóveis
- ✅ Gerenciar contratos com status e ações
- ✅ Assinar contratos digitalmente
- ✅ Validação de CNPJ com máscara de entrada
- ✅ Gerenciar rendimentos de forma centralizada

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

## 📝 Notas Importantes

### Sistema de Validação em Tempo Real
- Todos os formulários possuem validação robusta com feedback visual imediato
- Máscaras de entrada para CPF, CNPJ, telefone, placa de veículo, data e valores monetários
- Mensagens de erro claras e específicas para cada tipo de validação
- Formulário não pode ser enviado com dados inválidos

### Sistema de Status
- **Contratos**: Pendente, Ativo, Assinado, Vencido, Cancelado, Em Revisão
- **Pedidos de Aluguel**: Pendente, Aprovado, Rejeitado, Cancelado, Em Processamento, Concluído, Expirado
- Status com color-coding para melhor visualização
- Ícones emoji para representação visual intuitiva

### Interface CRUD Dinâmica
- 6 páginas CRUD geradas dinamicamente para cada entidade
- Operações completas: Criar, Ler, Atualizar, Deletar
- Interface web responsiva com formulários validados
- Integração seamless entre frontend e backend

### Arquitetura
- O sistema utiliza armazenamento em memória (pode ser migrado para banco de dados)
- A autenticação é armazenada nos objetos de usuário
- CORS está habilitado para desenvolvimento
- Arquivos estáticos são servidos da pasta `/src/main/resources/public`
- Lógica de validação centralizada em JavaScript reutilizável

## 👥 Autor

**Laboratório de Desenvolvimento de Software - LAB02**
PUC Minas - 2026

## 📄 Licença

Este projeto é parte de uma atividade acadêmica.




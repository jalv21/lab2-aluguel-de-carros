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

## Documentação Completa

Para documentação detalhada da implementação, veja o arquivo [IMPLEMENTACAO_COMPLETA.md](IMPLEMENTACAO_COMPLETA.md)

## Notas Importantes

- O sistema utiliza armazenamento em memória (pode ser migrado para banco de dados)
- A autenticação é armazenada nos objetos de usuário
- CORS está habilitado para desenvolvimento
- Arquivo estáticos são servidos da pasta `/src/main/resources/public`




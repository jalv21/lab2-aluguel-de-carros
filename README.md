## Como Rodar o Projeto

### Pré-requisitos

- **Java 25** ou superior
- **Maven 3.8+**

### 1. Compilar o Projeto

Execute o comando Maven para compilar:

```bash
mvn clean compile
```

Ou no Windows:

```bash
mvnw clean compile
```

### 2. Rodar a Aplicação

Execute o comando para iniciar a aplicação:

```bash
mvn exec:java
```

Ou no Windows:

```bash
mvnw exec:java
```

A aplicação iniciará na porta **8080**: `http://localhost:8080`

### 3. Endpoints da API

A aplicação fornece uma API REST para gerenciamento de clientes nos seguintes endpoints:

- **GET** `/clientes` - Listar todos os clientes
- **POST** `/clientes` - Criar um novo cliente
- **GET** `/clientes/{id}` - Obter um cliente específico
- **PUT** `/clientes/{id}` - Atualizar um cliente
- **DELETE** `/clientes/{id}` - Deletar um cliente

### 4. Rodar os Testes

```bash
mvn test
```

Ou no Windows:

```bash
mvnw test
```



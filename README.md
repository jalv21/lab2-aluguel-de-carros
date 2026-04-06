## Como Rodar o Projeto

### Caminho do Projeto

Navegue até a pasta do projeto no terminal:

```bash
cd Codigo/lab2/lab2
```

### Pré-requisitos

- **Java 25** ou superior
- **Maven 3.8+**

### 1. Compilar e Gerar o JAR

Execute o comando Maven para compilar e gerar o arquivo executável:

```bash
mvn clean package -DskipTests
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

### 5. Limpar Artifacts de Build

Para remover arquivos gerados durante a compilação:

```bash
mvn clean
```



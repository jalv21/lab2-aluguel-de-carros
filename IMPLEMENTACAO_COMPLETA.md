# Implementação Completa - Sistema de Aluguel de Carros

## Visão Geral

Foi implementado um sistema completo de aluguel de carros seguindo os diagramas UML fornecidos (Diagrama de Classes, Diagrama de Componentes e Diagrama de Casos de Uso) e os requisitos do Laboratório 02.

## Arquitetura do Sistema

O sistema foi desenvolvido utilizando a arquitetura **MVC (Model-View-Controller)** com tecnologias modernas:

- **Backend**: Micronaut Framework + Java 25
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **Padrão de Dados**: In-memory storage (pode ser facilmente migrado para banco de dados)
- **API**: REST com JSON

## Modelos de Dados Implementados

### 1. **Usuario** (Classe Abstrata)
- Atributos: id, login, senha, nome, endereco
- Método: autenticar(login, senha)

### 2. **Cliente** (extends Usuario)
- Atributos adicionais: rg, cpf, profissao
- Métodos: solicitarAluguel(), modificarPedido(), cancelarPedido(), assinaturContrato()

### 3. **Agente** (Classe Abstrata, extends Usuario)
- Atributos adicionais: cnpj
- Métodos: avaliarPedido(), modificarPedido()

### 4. **Banco** (extends Agente)
- Método adicional: concederCredito()

### 5. **Empresa** (extends Agente)
- Herança de Agente

### 6. **Automovel**
- Atributos: id, matricula, ano, marca, modelo, placa

### 7. **PedidoAluguel**
- Atributos: id, clienteId, automovelId, dataLocal, status, dataPedido, assinado
- Status: PENDENTE, APROVADO, REJEITADO, CANCELADO
- Método: atualizarStatus()

### 8. **Contrato**
- Atributos: id, numero, termos, tipoContrato, assinado
- Tipos de contrato: ALUGUEL, CREDITO, PROPRIEDADE
- Métodos: gerarPDF(), registrarAssinatura()

### 9. **Rendimento**
- Atributos: id, entidadeEmpregadora, valor
- Máximo de 3 rendimentos por cliente

## Componentes Implementados

### Backend

#### Repositories (Implementação In-Memory)
- `ClienteRepository` / `ClienteRepositoryImpl`
- `AgenteRepository` / `AgenteRepositoryImpl`
- `AutomovelRepository` / `AutomovelRepositoryImpl`
- `PedidoAluguelRepository` / `PedidoAluguelRepositoryImpl`
- `ContratoRepository` / `ContratoRepositoryImpl`
- `RendimentoRepository` / `RendimentoRepositoryImpl`

#### Services (Camada de Negócio)
- `ClienteService` - CRUD + validações
- `AgenteService` - CRUD + validações
- `AutomovelService` - CRUD + validações
- `PedidoAluguelService` - CRUD + Aprovação/Rejeição/Cancelamento
- `ContratoService` - CRUD + Assinatura
- `RendimentoService` - CRUD

#### Controllers (REST API)
- `ClienteController` - `/clientes`
  - GET, POST, PUT, DELETE
  - GET `/clientes/{id}`
  - GET `/clientes/cpf/{cpf}`

- `AgenteController` - `/agentes`
  - GET, POST, PUT, DELETE
  - GET `/agentes/{id}`
  - GET `/agentes/cnpj/{cnpj}`

- `AutomovelController` - `/automoveis`
  - GET, POST, PUT, DELETE
  - GET `/automoveis/{id}`
  - GET `/automoveis/placa/{placa}`

- `PedidoAluguelController` - `/pedidos`
  - GET, POST, PUT, DELETE
  - GET `/pedidos/{id}`
  - GET `/pedidos/cliente/{clienteId}`
  - PUT `/pedidos/{id}/aprovar`
  - PUT `/pedidos/{id}/rejeitar`
  - PUT `/pedidos/{id}/cancelar`

- `ContratoController` - `/contratos`
  - GET, POST, PUT, DELETE
  - PUT `/contratos/{id}/assinar`

- `RendimentoController` - `/rendimentos`
  - GET, POST, PUT, DELETE

- `HomeController` - `/`
  - Redireciona para `/index.html`

### Frontend

#### Páginas HTML
1. **index.html** - Login
   - Autenticação de usuários
   - Seleção de tipo de usuário (Cliente, Banco, Empresa)

2. **register.html** - Cadastro
   - Registro de novos usuários
   - Campos dinâmicos baseado no tipo (CPF/CNPJ)

3. **dashboard-cliente.html** - Dashboard do Cliente
   - Meus Pedidos
   - Criar Novo Pedido
   - Automóveis Disponíveis
   - Meu Perfil
   - Meus Rendimentos

4. **dashboard-agente.html** - Dashboard do Agente
   - Pedidos Pendentes
   - Todos os Pedidos
   - Gerenciar Clientes
   - Gerenciar Automóveis
   - Gerenciar Contratos
   - Gerenciar Agentes

#### Assets

**CSS** (`css/styles.css`)
- Design responsivo
- Tema modernizado
- Suporte para mobile
- Componentes reutilizáveis (botões, tabelas, cards, badges)

**JavaScript** (`js/`)
- `login.js` - Autenticação
- `register.js` - Cadastro com validações
- `dashboard-cliente.js` - Lógica do cliente
- `dashboard-agente.js` - Lógica do agente

## Endpoints da API

### Clientes
```
POST   /clientes                    - Criar cliente
GET    /clientes                    - Listar todos
GET    /clientes/{id}               - Obter por ID
GET    /clientes/cpf/{cpf}          - Obter por CPF
PUT    /clientes/{id}               - Atualizar
DELETE /clientes/{id}               - Deletar
```

### Agentes (Banco/Empresa)
```
POST   /agentes                     - Criar agente
GET    /agentes                     - Listar todos
GET    /agentes/{id}                - Obter por ID
GET    /agentes/cnpj/{cnpj}         - Obter por CNPJ
PUT    /agentes/{id}                - Atualizar
DELETE /agentes/{id}                - Deletar
```

### Automóveis
```
POST   /automoveis                  - Criar automóvel
GET    /automoveis                  - Listar todos
GET    /automoveis/{id}             - Obter por ID
GET    /automoveis/placa/{placa}    - Obter por placa
PUT    /automoveis/{id}             - Atualizar
DELETE /automoveis/{id}             - Deletar
```

### Pedidos de Aluguel
```
POST   /pedidos                     - Criar pedido
GET    /pedidos                     - Listar todos
GET    /pedidos/{id}                - Obter por ID
GET    /pedidos/cliente/{clienteId} - Listar pedidos do cliente
PUT    /pedidos/{id}                - Atualizar
DELETE /pedidos/{id}                - Deletar
PUT    /pedidos/{id}/aprovar        - Aprovar pedido
PUT    /pedidos/{id}/rejeitar       - Rejeitar pedido
PUT    /pedidos/{id}/cancelar       - Cancelar pedido
```

### Contratos
```
POST   /contratos                   - Criar contrato
GET    /contratos                   - Listar todos
GET    /contratos/{id}              - Obter por ID
PUT    /contratos/{id}              - Atualizar
DELETE /contratos/{id}              - Deletar
PUT    /contratos/{id}/assinar      - Assinar contrato
```

### Rendimentos
```
POST   /rendimentos                 - Criar rendimento
GET    /rendimentos                 - Listar todos
GET    /rendimentos/{id}            - Obter por ID
PUT    /rendimentos/{id}            - Atualizar
DELETE /rendimentos/{id}            - Deletar
```

## Como Executar

### 1. Compilar e Gerar JAR
```bash
cd Codigo/lab2/lab2
./mvnw clean package -DskipTests
```

### 2. Executar a Aplicação
```bash
java -jar target/lab2-0.1.jar
```

### 3. Acessar a Aplicação
- **URL Base**: http://localhost:8080
- **Login**: http://localhost:8080/index.html
- **API**: http://localhost:8080/api/* (dependendo dos endpoints)

## Funcionalidades Implementadas

### Para Clientes
✅ Cadastro de novo usuário  
✅ Login na plataforma  
✅ Visualizar automóveis disponíveis  
✅ Criar novo pedido de aluguel  
✅ Visualizar meus pedidos  
✅ Cancelar pedidos pendentes  
✅ Visualizar perfil pessoal  
✅ Gerenciar rendimentos (máximo 3)  
✅ Ver status dos pedidos em tempo real  

### Para Agentes (Banco/Empresa)
✅ Visualizar todos os pedidos  
✅ Visualizar pedidos pendentes  
✅ Aprovar/Rejeitar pedidos  
✅ Gerenciar clientes  
✅ Gerenciar automóveis  
✅ Gerenciar contratos  
✅ Assinatura de contratos  
✅ Listar outros agentes  

## Relacionamentos de Dados

```
Usuario (abstract)
├── Cliente
├── Agente (abstract)
    ├── Banco
    └── Empresa

PedidoAluguel
├── clienteId (referencia Cliente)
└── automovelId (referencia Automovel)

Contrato
└── Pode estar associado com PedidoAluguel

Rendimento
└── Associado com Cliente (máximo 3)

Automovel
└── Pode ser alugado através de PedidoAluguel
```

## Validações Implementadas

- CPF único por cliente
- CNPJ único por agente
- Placa única por automóvel
- Validação de status de pedidos (PENDENTE → APROVADO/REJEITADO → CANCELADO)
- Apenas clientes podem criar pedidos
- Apenas agentes podem avaliar/aprovar pedidos
- Máximo 3 rendimentos por cliente

## Estrutura de Diretórios

```
lab2/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/aluguel/
│   │   │       ├── Application.java
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── resources/
│   │       ├── public/
│   │       │   ├── index.html
│   │       │   ├── register.html
│   │       │   ├── dashboard-cliente.html
│   │       │   ├── dashboard-agente.html
│   │       │   ├── css/styles.css
│   │       │   └── js/
│   │       ├── application.properties
│   │       └── logback.xml
│   └── test/
└── pom.xml
```

## Tecnologias Utilizadas

- **Framework**: Micronaut 4.10.10
- **JDK**: Java 25 (LTS)
- **Build**: Maven 3.8+
- **Backend**: REST API com JSON
- **Frontend**: HTML5 + CSS3 + Vanilla JavaScript
- **Armazenamento**: In-Memory (Map sincronizado)

## Próximos Passos Sugeridos

1. **Integração com Banco de Dados** - Substituir implementação in-memory por Hibernate/JPA
2. **Autenticação JWT** - Implementar tokens JWT para melhorar segurança
3. **Validações Avançadas** - FormValidator para CPF/CNPJ
4. **Relatórios** - Gerar relatórios em PDF
5. **WebSocket** - Notificações em tempo real
6. **Testes Unitários** - Aumentar cobertura de testes
7. **Docker** - Containerizar a aplicação
8. **CI/CD** - Pipeline de integração contínua

## Notas Importantes

- O sistema utiliza armazenamento in-memory, portanto os dados são perdidos ao reiniciar a aplicação
- A autenticação é simples (credentials armazenados no objeto Usuario)
- O CORS está habilitado para desenvolvimento (considerar desabilitar em produção)
- Arquivos estáticos são servidos automaticamente pela pasta `public`

## Suporte e Documentação

Para mais informações sobre os endpoints, consulte a seção "Endpoints da API" neste documento.

Para questões técnicas, consulte a documentação:
- Micronaut: https://micronaut.io
- Java: https://docs.oracle.com/en/java/

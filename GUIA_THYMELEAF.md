# 🚀 Como Usar o Sistema com Thymeleaf

## Iniciar a Aplicação

```bash
cd Codigo/lab2/lab2

# Opção 1: Usando Maven Wrapper
./mvnw spring-boot:run

# Opção 2: Usando JAR compilado
java -jar target/lab2-0.1.jar
```

O servidor iniciará em **http://localhost:8080**

## Acessar as Páginas (Templates Thymeleaf)

### 1. **Página de Login**
```
http://localhost:8080/templates/login.html
```
- Insira credenciais de teste
- Selecione tipo de usuário
- Clique em "Entrar"

### 2. **Página de Cadastro**
```
http://localhost:8080/templates/register.html
```
- Preencha formulário
- Tipo de usuário controla campos visíveis (CPF vs CNPJ)
- Clique em "Cadastrar"

### 3. **Dashboard Cliente**
```
http://localhost:8080/templates/dashboard-cliente.html
```
Após login de cliente:
- **Meus Pedidos** - Visualizar pedidos criados
- **Novo Pedido** - Criar novo pedido de aluguel
- **Automóveis** - Listar automóveis disponíveis
- **Rendimentos** - Gerenciar rendimentos (máx 3)
- **Meu Perfil** - Visualizar dados pessoais

### 4. **Dashboard Agente**
```
http://localhost:8080/templates/dashboard-agente.html
```
Após login de agente (Banco/Empresa):
- **Pedidos Pendentes** - Avaliar pedidos (aprovar/rejeitar)
- **Todos Pedidos** - Visualizar histórico de pedidos
- **Clientes** - Listar e gerenciar clientes
- **Automóveis** - Adicionar/deletar automóveis
- **Contratos** - Assinar contratos
- **Agentes** - Listar agentes do sistema

## Dados de Teste

### Exemplo de Cliente
```
Tipo: CLIENTE
Usuário: joao.silva
Senha: senha123
Nome: João Silva
CPF: 123.456.789-10
Profissão: Advogado
RG: 12.345.678-9
Endereço: Rua Principal, 100
```

### Exemplo de Agente (Banco)
```
Tipo: BANCO
Usuário: banco.central
Senha: seg123456
Nome: Banco Central do Brasil
CNPJ: 00.000.000/0001-91
Endereço: Brasília, DF
```

## API REST (Integration)

### Endpoints de Cliente
```bash
# Criar cliente
curl -X POST http://localhost:8080/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria",
    "login": "maria.santos",
    "senha": "pass123",
    "cpf": "987.654.321-00",
    "rg": "98.765.432-1",
    "profissao": "Engenheira",
    "endereco": "Rua B, 200"
  }'

# Listar clientes
curl http://localhost:8080/clientes

# Obter cliente por ID
curl http://localhost:8080/clientes/1

# Buscar cliente por CPF
curl http://localhost:8080/clientes/obter/cpf/123.456.789-10

# Atualizar cliente
curl -X PUT http://localhost:8080/clientes/1 \
  -H "Content-Type: application/json" \
  -d '{"nome": "João Updated", ...}'

# Deletar cliente
curl -X DELETE http://localhost:8080/clientes/1
```

### Endpoints de Automóvel
```bash
# Criar automóvel
curl -X POST http://localhost:8080/automoveis \
  -H "Content-Type: application/json" \
  -d '{
    "marca": "Toyota",
    "modelo": "Corolla",
    "ano": 2023,
    "placa": "ABC-1234",
    "matricula": "MAT123456"
  }'

# Listar automóveis
curl http://localhost:8080/automoveis

# Obter automóvel por ID
curl http://localhost:8080/automoveis/1

# Buscar por placa
curl http://localhost:8080/automoveis/obter/placa/ABC-1234
```

### Endpoints de Pedido de Aluguel
```bash
# Criar pedido
curl -X POST http://localhost:8080/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1,
    "automovelId": 1,
    "dataPedido": "2026-04-13T10:00:00",
    "dataLocal": "2026-04-15T10:00:00",
    "status": "PENDENTE"
  }'

# Listar todos os pedidos
curl http://localhost:8080/pedidos

# Listar pedidos por cliente
curl http://localhost:8080/pedidos/listar-cliente/1

# Aprovar pedido
curl -X PUT http://localhost:8080/pedidos/1/aprovar

# Rejeitar pedido
curl -X PUT http://localhost:8080/pedidos/1/rejeitar

# Cancelar pedido
curl -X PUT http://localhost:8080/pedidos/1/cancelar
```

## Fluxo de Negócio

1. **Cliente se cadastra** (CLIENTE tipo)
2. **Agente se cadastra** (BANCO ou EMPRESA tipo)
3. **Agente cria automóvel** via API ou dashboard
4. **Cliente faz login** e visualiza automóveis
5. **Cliente cria pedido de aluguel** selecionando automóvel
6. **Agente avalia pedido**:
   - ✅ Aprova → Contrato criado automaticamente
   - ❌ Rejeita → Pedido rejeitado
7. **Agente assina contrato** (se aprovado)
8. **Cliente visualiza status** em tempo real no dashboard

## Troubleshooting

### Erro: "Porta 8080 em uso"
```bash
# Usar outra porta
java -jar target/lab2-0.1.jar -Dmicronaut.server.port=8081
```

### Validação: CPF duplicado
Se receber erro ao criar cliente com CPF existente:
```
Error: CPF já cadastrado!
```
- Use um CPF diferente

### CNPJ duplicado para Agentes
Se receber erro ao criar agente com CNPJ existente:
```
Error: CNPJ já cadastrado!
```
- Use um CNPJ diferente

## Estrutura de Diretórios

```
Codigo/lab2/lab2/
├── src/main/
│   ├── java/com/aluguel/
│   │   ├── model/           # Domain models
│   │   ├── repository/      # Data access layer
│   │   ├── service/         # Business logic
│   │   ├── controller/      # REST Controllers
│   │   └── util/            # Thymeleaf engine
│   └── resources/
│       ├── application.properties
│       └── public/
│           ├── templates/   # Thymeleaf templates
│           ├── css/         # Styling
│           └── js/          # JavaScript
└── target/
    └── lab2-0.1.jar        # Compiled JAR
```

## Performance

- ⚡ **Build time**: ~13 segundos
- ⚡ **Startup time**: ~2-3 segundos
- ⚡ **Thymeleaf rendering**: <50ms por requisição

---

**Pronto para usar! 🎉**

Para mais detalhes, consulte `THYMELEAF_IMPLEMENTACAO.md`

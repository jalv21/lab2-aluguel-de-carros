## Teste Rápido da API

Após iniciar a aplicação em http://localhost:8080, você pode testar a API usando o arquivo `postman_collection.json` ou os seguintes exemplos.

### 1. Criar um Cliente

```bash
curl -X POST http://localhost:8080/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "login": "joao.silva",
    "senha": "123456",
    "endereco": "Rua A, 123",
    "rg": "123456789",
    "cpf": "123.456.789-00",
    "profissao": "Engenheiro"
  }'
```

### 2. Listar Clientes

```bash
curl http://localhost:8080/clientes
```

### 3. Criar um Agente

```bash
curl -X POST http://localhost:8080/agentes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Banco XYZ",
    "login": "banco.xyz",
    "senha": "123456",
    "endereco": "Avenida B, 456",
    "cnpj": "12.345.678/0001-90",
    "tipo": "Banco"
  }'
```

### 4. Criar um Automóvel

```bash
curl -X POST http://localhost:8080/automoveis \
  -H "Content-Type: application/json" \
  -d '{
    "matricula": "2024001",
    "ano": 2024,
    "marca": "Toyota",
    "modelo": "Corolla",
    "placa": "ABC-1234"
  }'
```

### 5. Criar um Pedido de Aluguel

```bash
curl -X POST http://localhost:8080/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1,
    "automovelId": 1,
    "dataLocal": "2024-05-01",
    "status": "PENDENTE"
  }'
```

### 6. Aprovar um Pedido

```bash
curl -X PUT http://localhost:8080/pedidos/1/aprovar
```

### 7. Criar um Contrato

```bash
curl -X POST http://localhost:8080/contratos \
  -H "Content-Type: application/json" \
  -d '{
    "numero": 1001,
    "termos": "Contrato de aluguel de automóvel",
    "tipoContrato": "ALUGUEL"
  }'
```

### 8. Assinar um Contrato

```bash
curl -X PUT http://localhost:8080/contratos/1/assinar
```

### 9. Adicionar Rendimento

```bash
curl -X POST http://localhost:8080/rendimentos \
  -H "Content-Type: application/json" \
  -d '{
    "entidadeEmpregadora": "Empresa ABC Ltda",
    "valor": 5000.00
  }'
```

## Interface Web

Acesse a interface gráfica em:
- Login: http://localhost:8080/index.html
- Cadastro: http://localhost:8080/register.html

### Dados de Teste para Login

Após criar os usuários via API, use os mesmos dados de login/senha para acessar a interface.

**Exemplo Cliente:**
- Login: `joao.silva`
- Senha: `123456`
- Tipo: Cliente

**Exemplo Agente:**
- Login: `banco.xyz`
- Senha: `123456`
- Tipo: Banco

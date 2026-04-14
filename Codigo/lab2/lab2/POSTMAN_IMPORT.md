# 📬 Guia Completo: Postman Collection para LAB02 - Aluguel de Carros

## 🚀 Início Rápido

### 1. Importar a Coleção

#### ✅ Método 1: Arquivo Local (Recomendado)
1. Abra o **Postman**
2. Clique em **File** → **Import**
3. Escolha **Upload Files**
4. Selecione `postman_collection.json`
5. Clique em **Import**

#### ✅ Método 2: Pasta do Projeto
1. Abra o **Postman**
2. Clique em **File** → **Import** → **Folder**
3. Navegue até a pasta `lab2/`
4. Selecione e clique em **Import**

### 2. Configurar Base URL

A coleção já vem com `base_url = http://localhost:8080`

**Para mudar:**
1. Clique no ícone ⚙️ (Settings) no topo
2. Acesse **Environments** → **Edit Globals**
3. Modifique `base_url` se necessário
4. Clique em **Save**

---

## 📊 Estrutura da Coleção

A coleção está organizada em **7 grupos principais**:

### 🔐 AUTENTICAÇÃO FRONTEND (6 requisições)
Para testar o interface web (HTML forms):

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/login.html` | Carrega página de login |
| GET | `/register.html` | Carrega página de registro |
| POST | `/login` | Autentifica usuário (form data) |
| POST | `/register` | Registra novo usuário (form data) |
| GET | `/dashboard-cliente.html` | Dashboard do cliente |
| GET | `/dashboard-agente.html` | Dashboard do agente |

**Dados padrão de login:**
- Login: `cliente`
- Senha: `123`
- Tipo: `CLIENTE`

---

### 👥 CLIENTES (6 requisições)
Gerenciar clientes da plataforma:

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/clientes` | ✨ Criar novo cliente |
| GET | `/clientes` | 📋 Listar todos |
| GET | `/clientes/{id}` | 🔍 Obter por ID |
| GET | `/clientes/cpf/{cpf}` | 🔍 Buscar por CPF |
| PUT | `/clientes` | ✏️ Atualizar |
| DELETE | `/clientes/{id}` | 🗑️ Deletar |

---

### 🏛️ AGENTES - Banco/Empresa (6 requisições)
Gerenciar agentes (instituições financeiras):

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/agentes` | ✨ Criar novo agente |
| GET | `/agentes` | 📋 Listar todos |
| GET | `/agentes/{id}` | 🔍 Obter por ID |
| GET | `/agentes/cnpj/{cnpj}` | 🔍 Buscar por CNPJ |
| PUT | `/agentes` | ✏️ Atualizar |
| DELETE | `/agentes/{id}` | 🗑️ Deletar |

---

### 🚗 AUTOMÓVEIS (6 requisições)
Gerenciar frota de veículos:

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/automoveis` | ✨ Criar novo automóvel |
| GET | `/automoveis` | 📋 Listar todos |
| GET | `/automoveis/{id}` | 🔍 Obter por ID |
| GET | `/automoveis/placa/{placa}` | 🔍 Buscar por placa |
| PUT | `/automoveis` | ✏️ Atualizar |
| DELETE | `/automoveis/{id}` | 🗑️ Deletar |

---

### 📋 PEDIDOS DE ALUGUEL (9 requisições)
Gerenciar solicitações de aluguel:

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/pedidosaluguel` | ✨ Criar novo pedido |
| GET | `/pedidosaluguel` | 📋 Listar todos |
| GET | `/pedidosaluguel/{id}` | 🔍 Obter por ID |
| GET | `/pedidosaluguel/cliente/{id}` | 👤 Listar do cliente |
| PUT | `/pedidosaluguel` | ✏️ Atualizar |
| POST | `/pedidosaluguel/{id}/aprovar` | ✅ Aprovar pedido |
| POST | `/pedidosaluguel/{id}/rejeitar` | ❌ Rejeitar pedido |
| POST | `/pedidosaluguel/{id}/cancelar` | 🚫 Cancelar pedido |
| DELETE | `/pedidosaluguel/{id}` | 🗑️ Deletar |

---

### 📝 CONTRATOS (7 requisições)
Gerenciar termos e assinaturas:

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/contratos` | ✨ Criar novo contrato |
| GET | `/contratos` | 📋 Listar todos |
| GET | `/contratos/{id}` | 🔍 Obter por ID |
| GET | `/contratos/numero/{numero}` | 🔍 Buscar por número |
| PUT | `/contratos` | ✏️ Atualizar |
| POST | `/contratos/{id}/assinar` | 🖊️ Assinar contrato |
| DELETE | `/contratos/{id}` | 🗑️ Deletar |

---

### 💼 RENDIMENTOS (5 requisições)
Gerenciar comprovante de renda:

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/rendimentos` | ✨ Criar novo rendimento |
| GET | `/rendimentos` | 📋 Listar todos |
| GET | `/rendimentos/{id}` | 🔍 Obter por ID |
| PUT | `/rendimentos` | ✏️ Atualizar |
| DELETE | `/rendimentos/{id}` | 🗑️ Deletar |

---

## 💡 Exemplos de Uso

### 📝 Exemplo 1: Criar Cliente

```
1. Expanda "👥 CLIENTES"
2. Selecione "POST - Criar Cliente"
3. Na aba "Body", modifique os dados:
   {
     "login": "seu.login",
     "senha": "suaSenha",
     "nome": "Seu Nome",
     "endereco": "Seu Endereço",
     "cpf": "123.456.789-00",
     "rg": "123456789",
     "profissao": "Sua Profissão"
   }
4. Clique "Send"
5. Veja resposta em "Response"
```

### 🔑 Exemplo 2: Login via Frontend

```
1. Expanda "🔐 AUTENTICAÇÃO FRONTEND"
2. Selecione "POST - Login (Form Data)"
3. Body já vem preenchido com:
   - login: cliente
   - senha: 123
   - tipoUsuario: CLIENTE
4. Clique "Send"
5. Será redirecionado para dashboard-cliente.html
```

### 🚗 Exemplo 3: Criar Pedido de Aluguel

```
1. Primeiro, crie um cliente (se ainda não tiver)
2. Crie um automóvel
3. Expanda "📋 PEDIDOS DE ALUGUEL"
4. Selecione "POST - Criar Pedido de Aluguel"
5. Modifique:
   {
     "clienteId": 1,
     "automovelId": 1,
     "dataLocal": "2026-04-20T10:00:00",
     "dataPedido": "2026-04-13T14:00:00",
     "status": "PENDENTE"
   }
6. Clique "Send"
```

### ✅ Exemplo 4: Aprovar Pedido

```
1. Após criar um pedido (que retorna ID = 1)
2. Expanda "📋 PEDIDOS DE ALUGUEL"
3. Selecione "POST - Aprovar Pedido"
4. URL já vem como: {{base_url}}/pedidosaluguel/1/aprovar
5. Se IDfor diferente, modifique o número
6. Clique "Send"
7. Status muda para "APROVADO"
```

---

## ⚠️ Pontos Importantes

### 🔴 Erro: Port Already in Use
```
Se receber erro que porta 8080 está em uso:
1. Mateos demais processos java: taskkill /F /IM java.exe
2. Ou altere a porta em application.properties
3. Atualize base_url no Postman
```

### 🔴 Erro: 404 Not Found
```
Verifique:
- ✅ Servidor está rodando em http://localhost:8080
- ✅ base_url no Postman está correto
- ✅ Endpoint existe (chequear ortografia)
```

### 🔴 Erro: Method Not Allowed (405)
```
Motivo: Content-Type incorreto
Solução:
- ✅ Para JSON: Header "Content-Type: application/json"
- ✅ Para Form: Header "Content-Type: application/x-www-form-urlencoded"
```

### ⚡ Dados em Memória
```
IMPORTANTE:
- Todos os dados são PERDIDOS ao reiniciar o servidor
- Não há banco de dados persistente
- Para cada teste, recrie os dados de teste
```

---

## 🎯 Fluxo Completo de Teste

**Siga este roteiro para testar o sistema inteiro:**

### Passo 1: Registrar Cliente
```
POST /register
Dados: login, senha, nome
```

### Passo 2: Fazer Login
```
POST /login
Credenciais do cliente criado
```

### Passo 3: Criar Automóvel (como Agente)
```
POST /automoveis
Dados: matricula, ano, marca, modelo, placa
```

### Passo 4: Criar Pedido de Aluguel
```
POST /pedidosaluguel
Dados: clienteId (de Passo 1), automovelId (de Passo 3)
```

### Passo 5: Ver Pedidos do Cliente
```
GET /pedidosaluguel/cliente/{clienteId}
```

### Passo 6: Aprovar Pedido
```
POST /pedidosaluguel/{pedidoId}/aprovar
```

### Passo 7: Criar Rendimento
```
POST /rendimentos
Dados: clienteId, entidadeEmpregadora, valor
```

### Passo 8: Criar Contrato
```
POST /contratos
Dados: numero, termos, tipoContrato, assinado
```

### Passo 9: Assinar Contrato
```
POST /contratos/{contratoId}/assinar
```

---

## 🔧 Dicas Pro

| Atalho | Função |
|--------|--------|
| `Ctrl+K` | Buscar requisições |
| `Ctrl+Alt+C` | Gerar código (Python, JS, cURL, etc) |
| `Ctrl+S` | Salvar/Export resposta |
| `Ctrl+Shift+R` | Abrir History |

---

## 📚 Referências

- 🌐 Postman Docs: https://learning.postman.com/
- 🔗 REST Best Practices: https://restfulapi.net/
- 🎬 YouTube Postman: https://www.youtube.com/results?search_query=postman+tutorial

---

**Total de Requisições: 47+ endpoints disponíveis para testar! 🎉**

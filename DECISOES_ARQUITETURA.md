# Decisões de Design e Arquitetura

## Visão Geral da Arquitetura

O sistema foi arquitetado seguindo o padrão **MVC (Model-View-Controller)** com separação clara de responsabilidades:

```
┌─────────────────────┐
│   Frontend (View)   │
│  HTML/CSS/JS        │
└──────────┬──────────┘
           │
           │ HTTP/REST
           │
┌──────────▼──────────┐
│  Backend (API)      │
│  - Controllers      │
│  - Services         │
│  - Repositories     │
│  - Models           │
└─────────────────────┘
           │
           │ In-Memory Storage
           │
┌──────────▼──────────┐
│   Data Layer        │
│  - Map Sincronizado │
└─────────────────────┘
```

## Camadas da Aplicação

### 1. **Camada de Apresentação (View)**
- **Tecnologia**: HTML5, CSS3, JavaScript Vanilla
- **Localização**: `/src/main/resources/public/`
- **Responsabilidade**: Interface com usuário
- **Componentes**: 
  - Páginas HTML (login, registro, dashboards)
  - Estilos CSS (responsivos e modernos)
  - Scripts JavaScript (lógica do cliente)

### 2. **Camada de Controle (Controllers)**
- **Tecnologia**: Micronaut REST Controllers
- **Padrão**: Annotations (`@Controller`, `@Get`, `@Post`, etc.)
- **Responsabilidade**: Receber requisições HTTP e coordenar respostas
- **Localização**: `/src/main/java/com/aluguel/controller/`
- **Benefício**: Separação entre camadas HTTP e lógica de negócio

### 3. **Camada de Negócio (Services)**
- **Padrão**: Service Classes
- **Responsabilidade**: Implementar regras de negócio e validações
- **Localização**: `/src/main/java/com/aluguel/service/`
- **Validações**:
  - Duplicidade de CPF/CNPJ/Placa
  - Estados válidos de pedidos
  - Máximo de 3 rendimentos por cliente

### 4. **Camada de Acesso a Dados (Repository)**
- **Padrão**: Repository Interface + Implementation
- **Responsabilidade**: Persistência e recuperação de dados
- **Localização**: `/src/main/java/com/aluguel/repository/`
- **Implementação Atual**: In-Memory com Map sincronizado
- **Facilita**: Migração futura para banco de dados

### 5. **Camada de Modelos (Entities)**
- **Padrão**: POJOs com Herança
- **Localização**: `/src/main/java/com/aluguel/model/`
- **Hierarquia**:
  ```
  Usuario (abstract)
  ├── Cliente
  └── Agente (abstract)
      ├── Banco
      └── Empresa
  ```

## Decisões Arquiteturais

### 1. **Framework: Micronaut**

**Por que Micronaut?**
- Framework leve e moderno
- Excelente para APIs REST
- Compilação ahead-of-time (AOT)
- Injeção de dependência via annotations
- Desempenho superior ao Spring para microserviços

### 2. **In-Memory Storage**

**Por que não Banco de Dados?**
- Simplificar prototipagem rápida
- Foco na lógica de negócio
- Fácil migração para JPA/Hibernate

**Próximos passos sugeridos:**
```java
// Seria possível substituir Map sincronizado por:
@Repository
public class ClienteRepositoryDB implements ClienteRepository {
    @Inject
    private EntityManager em;
    
    @Override
    public Cliente save(Cliente cliente) {
        em.persist(cliente);
        return cliente;
    }
}
```

### 3. **Autenticação Simples**

**Abordagem Atual:**
- Credentials armazenados no objeto Usuario
- Validação via login/senha em memória

**Por que não JWT/OAuth?**
- Prototipagem rápida
- Requisitos do laboratório não mencionam segurança avançada

**Próximos passos:**
```java
// Implementar JWT tokenization
@Bean
public JwtTokenGenerator tokenGenerator() {
    return new JwtTokenGenerator();
}
```

### 4. **Frontend com Vanilla JavaScript**

**Por que não Framework (React/Vue)?**
- Requisitos da disciplina (tecnologias simples)
- Menor complexidade
- Mais educacional para aprendizado de Web

**Vantagens da abordagem:**
- Sem dependências externas NPM
- Execução direta no navegador
- Fácil depuração

### 5. **CORS Habilitado**

**Configuração:**
```properties
# application.properties
# Habilitado para desenvolvimento frontend/backend separados
```

**Segurança:**
- Desabilitar em produção
- Criar whitelist de domínios permitidos

### 6. **Herança de Modelos**

**Decisão: Usar herança Java para relacionamentos**

```java
// Aproveitado pelo Micronaut Serialization
@Serdeable
public abstract class Usuario { }

public class Cliente extends Usuario { }

public abstract class Agente extends Usuario { }
```

**Benefícios:**
- Polimorfismo em nivel de código
- Reutilização de características comuns
- Tipagem forte

## Padrões de Design Utilizados

### 1. **Repository Pattern**
```
Interface ClienteRepository
    ↓
Implementação ClienteRepositoryImpl
    ↓
Usada por ClienteService
```

**Benefício**: Abstração da fonte de dados

### 2. **Service Layer Pattern**
```
ClienteController
    ↓ (usa)
ClienteService
    ↓ (usa)
ClienteRepository
```

**Benefício**: Separação de responsabilidades

### 3. **Dependency Injection**
```java
@Inject
private ClienteService clienteService;
```

**Benefício**: Desacoplamento e testabilidade

### 4. **DTO (Data Transfer Object) - Implícito**
```java
// Mesmos modelos usados como DTOs
@Serdeable
public class Cliente { }
```

**Alternativa futura:**
```java
public record ClienteDTO(
    String nome,
    String login,
    String cpf
) { }
```

## Fluxo de Dados

### Exemplo: Criar um Pedido de Aluguel

```
1. Cliente acessa dashboard-cliente.html
   │
2. JavaScript faz POST /pedidos com dados do formulário
   │
3. PedidoAluguelController.criar() recebe requisição
   │ (Validação: headers, formato JSON)
   │
4. PedidoAluguelService.criar() executa lógica de negócio
   │ (Validação: clienteId, automovelId obrigatórios)
   │
5. PedidoAluguelRepository.save() armazena em memória
   │
6. PedidoAluguel objeto retorna para Controller
   │
7. HttpResponse.created() envia resposta HTTP 201
   │
8. JavaScript processa resposta e atualiza interface
```

## Considerações de Segurança

### Atual (Desenvolvimento)
- ✅ Validações básicas
- ✅ Proteção contra duplicação
- ✅ CORS habilitado

### Recomendações para Produção
- 🔒 Implementar autenticação JWT
- 🔒 HTTPS obrigatório
- 🔒 Rate limiting
- 🔒 Validação de entrada com annotations `@Valid`
- 🔒 Sanitização de dados
- 🔒 SQL Injection prevention (quando usar BD)
- 🔒 CORS com whitelist

## Performance

### Otimizações Atuais
- ✅ Índices baseados em Map
- ✅ Sincronização thread-safe com `Collections.synchronizedMap()`
- ✅ Lazy loading de dados do servidor

### Melhorias Possíveis
- Implementar cache para queries frequentes
- Paginação de resultados em listas grandes
- Compressão de resposta gzip
- Índices específicos para buscas por CPF/CNPJ/Placa

## Escalabilidade

### Limitações Atuais
- Dados em memória (perdidos ao reiniciar)
- Single-instance (sem load balancing)

### Para Escalar
1. **Persistência**: Migrar para banco de dados (PostgreSQL/MySQL)
2. **Cache Distribuído**: Redis para sessões
3. **Mensageria**: RabbitMQ para operações assíncronas
4. **Containerização**: Docker + Kubernetes

## Testabilidade

### Estrutura Favorável ao Teste
```java
// Services podem ser testados isoladamente
ClienteService service = new ClienteService();
service.clienteRepository = new MockClienteRepository();
```

### Adicionar em Futuro
```java
@Test
public void testCriarClienteComCpfDuplicado() {
    Cliente c1 = new Cliente(...);
    cliente1Repository.save(c1);
    
    assertThrows(IllegalArgumentException.class, () -> {
        clienteService.criar(c1);
    });
}
```

## Manutenibilidade

### Código Limpo
- ✅ Nomes descritivos
- ✅ Métodos pequenos e focados
- ✅ Sem código duplicado
- ✅ Documentação Java Docs

### Organização
```
com.aluguel
├── controller    # Requisições HTTP
├── service       # Lógica de negócio
├── repository    # Acesso a dados
├── model         # Entidades
└── config        # Configurações
```

## Documentação

### Inclusos
- ✅ README.md com instruções de uso
- ✅ IMPLEMENTACAO_COMPLETA.md com detalhes
- ✅ TESTE_API.md com exemplos de requisições
- ✅ DECISOES_ARQUITETURA.md (este arquivo)
- ✅ JavaDocs nas classes principais

## Conclusão

O sistema foi desenvolvido com foco em:
1. **Clareza**: Código fácil de entender
2. **Manutenibilidade**: Fácil de modificar e estender
3. **Educação**: Demonstrar boas práticas
4. **Prototipagem**: Implementação rápida

A arquitetura escolhida facilita futuras migrações e melhorias sem necessidade de refatoração completa.

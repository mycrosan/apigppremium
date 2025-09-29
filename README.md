# 🏭 GP Premium API
Sistema Exclusivo para Gestão de Carcaças e Produção Industrial

## 📋 Descrição
API REST desenvolvida em Spring Boot para controle completo de produção, configuração de máquinas, gestão de usuários e monitoramento de processos industriais da GP Premium.

## 🚀 Status do Projeto
**Versão Atual**: v1.1 (Janeiro 2025)  
**Status**: ✅ ESTÁVEL - Pronto para Produção  
**Última Correção**: Hotfix matriz_id nulo

## 📚 Documentação Técnica Completa
Para informações detalhadas sobre arquitetura, evolução e estado atual do projeto:

### 📊 Documentos Principais
- **[📊 Resumo de Versão](./docs/VERSION_SUMMARY.md)** - Estado atual e métricas do projeto
- **[🏗️ Evolução Arquitetural](./docs/ARCHITECTURE_EVOLUTION.md)** - Arquitetura detalhada e roadmap
- **[🔧 Hotfix Matriz ID](./docs/HOTFIX_MATRIZ_ID.md)** - Correção crítica implementada
- **[📚 Índice de Documentação](./docs/README.md)** - Navegação completa da documentação

### 📁 Documentos de Desenvolvimento
- **[📝 Changelog](./CHANGELOG.md)** - Histórico de mudanças
- **[🛠️ Guia de Desenvolvimento](./DEVELOPMENT.md)** - Setup e desenvolvimento
- **[📏 Padrões de Código](./CODING_STANDARDS.md)** - Convenções e qualidade

## ⚡ Quick Start

### Pré-requisitos
```bash
Java 11+ (recomendado: 11.0.24-tem)
Maven 3.6+
MySQL 8.0+ (produção) ou H2 (desenvolvimento)
```

### Executar a Aplicação
```bash
# Instalar dependências
mvn install

# Executar em modo desenvolvimento
mvn spring-boot:run

# Acessar documentação da API
http://localhost:8080/swagger-ui/index.html
```

### Verificar Porta em Uso
```bash
# Verificar processos na porta 8080
lsof -ti:8080

# Matar processo se necessário
kill -9 <PID>
```

## 🎯 Funcionalidades Principais

### ✅ Implementadas e Funcionais
- **🔐 Autenticação JWT** - Login seguro com tokens
- **👥 Gestão de Usuários** - CRUD completo com soft delete
- **⚙️ Configuração de Máquinas** - Associação matriz/máquina [RECÉM CORRIGIDO]
- **📊 Controle de Produção** - Registro e monitoramento
- **🏭 Registro de Máquinas** - Cadastro e gestão de equipamentos
- **📖 Documentação API** - Swagger/OpenAPI 3 completo

### 🛠️ Tecnologias Utilizadas
```
Backend: Spring Boot 2.x + Spring Security
Database: MySQL (Prod) / H2 (Test)
ORM: JPA/Hibernate
Documentation: Swagger/OpenAPI 3
Testing: JUnit 5 + Mockito
Build: Maven
Security: JWT + BCrypt
```

## 🗄️ Configuração do Banco de Dados

### MySQL (Produção)
```sql
-- Criar usuário
CREATE USER 'monty'@'%' IDENTIFIED BY 'some_pass';

-- Criar o banco
CREATE DATABASE IF NOT EXISTS sislife CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Conceder privilégios
GRANT ALL PRIVILEGES ON sislife.* TO 'monty'@'%';
FLUSH PRIVILEGES;
```

### SDK Java
```bash
# Configurar versão do Java
sdk use java 11.0.24-tem
sdk default java 11.0.17-tem
```

---

## 📝 Convenções de Commit

### Commit type	Emoji

- Initial commit	🎉 :tada:
- Version tag	🔖 :bookmark:
- New feature	✨ :sparkles:
- Bugfix	🐛 :bug:
- Metadata	📇 :card_index:
- Documentation	📚 :books:
- Documenting source code	💡 :bulb:
- Performance	🐎 :racehorse:
- Cosmetic	💄 :lipstick:
- Tests	🚨 :rotating_light:
- Adding a test	✅ :white_check_mark:
- Make a test pass	✔️ :heavy_check_mark:
- General update	⚡ :zap:
- Improve format/structure	🎨 :art:
- Refactor code	🔨 :hammer:
- Removing code/files	🔥 :fire:
- Continuous Integration	💚 :green_heart:
- Security	🔒 :lock:
- Upgrading dependencies	⬆️ :arrow_up:
- Downgrading dependencies	⬇️ :arrow_down:
- Lint	👕 :shirt:
- Translation	👽 :alien:
- Text	📝 :pencil:
- Critical hotfix	🚑 :ambulance:
- Deploying stuff	🚀 :rocket:
- Fixing on MacOS	🍎 :apple:
- Fixing on Linux	🐧 :penguin:
- Fixing on Windows	🏁 :checkered_flag:
- Work in progress	🚧 :construction:
- Adding CI build system	👷 :construction_worker:
- Analytics or tracking code	📈 :chart_with_upwards_trend:
- Removing a dependency	➖ :heavy_minus_sign:
- Adding a dependency	➕ :heavy_plus_sign:
- Docker	🐳 :whale:
- Configuration files	🔧 :wrench:
- Package.json in JS	📦 :package:
- Merging branches	🔀 :twisted_rightwards_arrows:
- Bad code / need improv.	💩 :hankey:
- Reverting changes	⏪ :rewind:
- Breaking changes	💥 :boom:
- Code review changes	👌 :ok_hand:
- Accessibility	♿ :wheelchair:
- Move/rename repository	🚚 :truck:

## Matar sessao 
- lsof -i :8080
COMMAND   PID   USER   FD   TYPE DEVICE SIZE/OFF NODE NAME  
java     **12345**  you   ...  TCP  ...    LISTEN    ...
- kill -9 12345
## sdk default java 11.0.17-tem

mvn install

-- Criar usuário
CREATE USER 'monty'@'%' IDENTIFIED BY 'some_pass';

-- Criar o banco (se ainda não existir)
CREATE DATABASE IF NOT EXISTS sislife CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Conceder privilégios totais no banco para o usuário
GRANT ALL PRIVILEGES ON sislife.* TO 'monty'@'%';

-- Atualizar privilégios
FLUSH PRIVILEGES;

sdk use java 11.0.24-tem

http://localhost:8080/swagger-ui/index.html

mvn spring-boot:run 
lsof -ti:8080



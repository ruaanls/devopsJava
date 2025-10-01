# 🏍️ Sistema de Gestão de Estacionamento - Challenge Mottu

## 📋 Sobre o Projeto

Este é um sistema completo de gestão de estacionamento desenvolvido para o challenge da empresa **Mottu**. A aplicação permite o controle eficiente de vagas e motos em um estacionamento, oferecendo uma interface web intuitiva e uma API REST robusta.

### 🎯 Funcionalidades Principais

- **Gestão de Motos**: Cadastro, edição, exclusão e movimentação de motos
- **Gestão de Vagas**: Criação e controle de vagas de estacionamento
- **Sistema de Ocupação**: Controle automático de status das vagas (Livre/Ocupada)
- **Interface Web**: Interface amigável com Thymeleaf
- **API REST**: Endpoints para integração com outros sistemas
- **Autenticação OAuth2**: Suporte a GitHub e Google OAuth2
- **Banco de Dados**: Integração com SQL Server

### 🏗️ Arquitetura

O projeto segue a arquitetura **MVC (Model-View-Controller)** com Spring Boot:

```
📁 Estrutura do Projeto
├── 🎮 Controllers (Web + REST)
├── 🏢 Services (Lógica de Negócio)
├── 🗄️ Repositories (Acesso a Dados)
├── 📦 Entities (Modelos de Dados)
├── 🔄 DTOs (Transferência de Dados)
├── 🗺️ Mappers (Conversão de Objetos)
├── ⚙️ Configuration (Configurações)
└── 🎨 Templates (Interface Web)
```

### 🛠️ Tecnologias Utilizadas

- **Backend**: Java 21, Spring Boot 3.5.5
- **Banco de Dados**: Microsoft SQL Server
- **Frontend**: Thymeleaf, HTML5, CSS3
- **Segurança**: Spring Security + OAuth2
- **Build**: Gradle
- **Containerização**: Docker
- **Cloud**: Azure Container Registry + Container Instances

### 📊 Modelo de Dados

#### Entidade Motos
- **ID**: Identificador único
- **Placa**: Placa da moto (única)
- **Modelo**: Modelo da moto
- **Ano**: Ano de fabricação
- **Cor**: Cor da moto
- **Vaga**: Relacionamento com vaga (opcional)
- **Status**: Status da moto

#### Entidade Vagas
- **ID**: Identificador único
- **Linha**: Posição da linha no estacionamento
- **Coluna**: Posição da coluna no estacionamento
- **StatusVaga**: Enum (LIVRE/OCUPADA)
- **Moto**: Relacionamento com moto (opcional)

### 🔐 Variáveis de Ambiente

O projeto utiliza as seguintes variáveis de ambiente:

```bash
# Banco de Dados
DB_URL=jdbc:sqlserver://seu-servidor:1433;databaseName=estacionamento
DB_USERNAME=seu-usuario
DB_PASSWORD=sua-senha

# OAuth2 - GitHub
GITHUB_CLIENT_ID=seu-client-id
GITHUB_CLIENT_SECRET=seu-client-secret

# OAuth2 - Google
GOOGLE_CLIENT_ID=seu-client-id
GOOGLE_CLIENT_SECRET=seu-client-secret
```

### 🚀 Como Executar Localmente

1. **Clone o repositório**
```bash
git clone <url-do-repositorio>
cd devopsJava
```

2. **Configure as variáveis de ambiente**
```bash
# Crie um arquivo .env ou configure no seu IDE
export DB_URL=jdbc:sqlserver://localhost:1433;databaseName=estacionamento
export DB_USERNAME=sa
export DB_PASSWORD=YourPassword123
export GITHUB_CLIENT_ID=seu-client-id
export GITHUB_CLIENT_SECRET=seu-client-secret
export GOOGLE_CLIENT_ID=seu-client-id
export GOOGLE_CLIENT_SECRET=seu-client-secret
```

3. **Execute a aplicação**
```bash
./gradlew bootRun
```

4. **Acesse a aplicação**
- Interface Web: http://localhost:8080
- API REST: http://localhost:8080/api

### 🐳 Dockerização

O projeto inclui um `Dockerfile` otimizado com multi-stage build:

```dockerfile
# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS builder
# ... configurações de build

# Stage 2: Runtime
FROM amazoncorretto:21-alpine3.21
# ... configurações de execução
```

---

## 🚀 Tutorial: Deploy na Azure

### Pré-requisitos

- Conta Azure ativa
- Azure CLI instalado
- Docker instalado
- Acesso ao terminal/command prompt

### Passo 1: Configurar Azure CLI

```bash
# Login na Azure
az login

# Definir subscription (se necessário)
az account set --subscription "sua-subscription-id"
```

### Passo 2: Criar Resource Group

```bash
# Criar resource group
az group create --name rg-estacionamento --location eastus
```

### Passo 3: Criar Azure Container Registry (ACR)

```bash
# Criar ACR
az acr create --resource-group rg-estacionamento \
  --name acrestacionamento \
  --sku Basic \
  --admin-enabled true

# Obter credenciais do ACR
az acr credential show --name acrestacionamento
```

### Passo 4: Build e Push da Imagem Docker

```bash
# Login no ACR
az acr login --name acrestacionamento

# Build da imagem
docker build -t acrestacionamento.azurecr.io/estacionamento-app:v1 .

# Push da imagem
docker push acrestacionamento.azurecr.io/estacionamento-app:v1
```

### Passo 5: Criar Azure SQL Database

```bash
# Criar servidor SQL
az sql server create --resource-group rg-estacionamento \
  --name sql-estacionamento \
  --admin-user sqladmin \
  --admin-password "SuaSenhaSegura123!" \
  --location eastus

# Criar banco de dados
az sql db create --resource-group rg-estacionamento \
  --server sql-estacionamento \
  --name estacionamento \
  --service-objective Basic

# Configurar firewall (permitir Azure services)
az sql server firewall-rule create --resource-group rg-estacionamento \
  --server sql-estacionamento \
  --name AllowAzureServices \
  --start-ip-address 0.0.0.0 \
  --end-ip-address 0.0.0.0
```

### Passo 6: Criar Container Instance

```bash
# Criar container instance
az container create --resource-group rg-estacionamento \
  --name ci-estacionamento \
  --image acrestacionamento.azurecr.io/estacionamento-app:v1 \
  --cpu 1 \
  --memory 2 \
  --registry-login-server acrestacionamento.azurecr.io \
  --registry-username acrestacionamento \
  --registry-password "SUA_SENHA_DO_ACR" \
  --dns-name-label estacionamento-app \
  --ports 8080 \
  --environment-variables \
    DB_URL="jdbc:sqlserver://sql-estacionamento.database.windows.net:1433;databaseName=estacionamento;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;" \
    DB_USERNAME="sqladmin" \
    DB_PASSWORD="SUA_SENHA_DO_BANCO" \
    
```

### Passo 7: Verificar Deploy

```bash
# Verificar status do container
az container show --resource-group rg-estacionamento --name ci-estacionamento

# Obter IP público
az container show --resource-group rg-estacionamento --name ci-estacionamento --query ipAddress.ip
```

### Passo 8: Acessar a Aplicação

Após o deploy, a aplicação estará disponível em:
- **URL**: `IP_DA_APLICAÇÃO:8080`
- **API REST**: `IP_DA_APLICAÇÃO:8080/api`

### 🔄 Atualizações Automáticas

Para atualizar a aplicação com uma nova versão:

```bash
# 1. Build nova imagem
docker build -t acrestacionamento.azurecr.io/estacionamento-app:v2.0 .

# 2. Push nova imagem
docker push acrestacionamento.azurecr.io/estacionamento-app:v2.0

# 3. Atualizar container instance
az container create --resource-group rg-estacionamento \
  --name ci-estacionamento \
  --image acrestacionamento.azurecr.io/estacionamento-app:v2.0 \
  # ... resto das configurações
```

## 📚 API Endpoints

### 🌐 **URL de Teste**
**Aplicação rodando em produção**: `http://52.226.54.155:8080`

### 🏍️ **Endpoints - Motos**

#### `GET /api/motos` - Listar todas as motos
```bash
curl -X GET http://52.226.54.155:8080/api/motos
```

#### `POST /api/motos` - Criar nova moto
**Body (JSON):**
```json
{
    "placa": "ABC1234",
    "modelo": "Honda CB 600",
    "ano": 2023,
    "cor": "Vermelha",
    "status": "Ativa"
}
```

#### `GET /api/motos/{placa}` - Buscar moto por placa
```bash
curl -X GET http://52.226.54.155:8080/api/motos/ABC1234
```

#### `PUT /api/motos/{placa}` - Atualizar moto
**Body (JSON):**
```json
{
    "placa": "ABC1234",
    "modelo": "Honda CB 600F",
    "ano": 2024,
    "cor": "Azul",
    "status": "Ativa"
}
```

#### `DELETE /api/motos/{placa}` - Excluir moto
```bash
curl -X DELETE http://52.226.54.155:8080/api/motos/ABC1234
```

#### `POST /api/motos/{placa}/mover` - Mover moto para vaga
**Body (JSON):**
```json
{
    "placa": "ABC1234",
    "idVaga": 1
}
```

### 🅿️ **Endpoints - Vagas**

#### `GET /api/vagas` - Listar todas as vagas
```bash
curl -X GET http://52.226.54.155:8080/api/vagas
```

#### `POST /api/vagas` - Criar nova vaga
**Body (JSON):**
```json
{
    "linha": "A",
    "coluna": "1"
}
```

#### `GET /api/vagas/{id}` - Buscar vaga por ID
```bash
curl -X GET http://52.226.54.155:8080/api/vagas/1
```

#### `PUT /api/vagas/{id}` - Atualizar vaga
**Body (JSON):**
```json
{
    "linha": "B",
    "coluna": "2"
}
```

#### `DELETE /api/vagas/{id}` - Excluir vaga
```bash
curl -X DELETE http://52.226.54.155:8080/api/vagas/1
```

#### `GET /api/vagas/livres` - Listar vagas livres
```bash
curl -X GET http://52.226.54.155:8080/api/vagas/livres
```

### 📋 **Exemplos de Resposta**

#### Resposta - Lista de Motos
```json
[
    {
        "placa": "ABC1234",
        "modelo": "Honda CB 600",
        "ano": 2023,
        "cor": "Vermelha",
        "idVaga": 1,
        "status": "Ativa",
        "linha": "A",
        "coluna": "1"
    }
]
```

#### Resposta - Lista de Vagas
```json
[
    {
        "id": 1,
        "posicao": "A1",
        "status": "OCUPADA",
        "placa": "ABC1234",
        "modelo": "Honda CB 600",
        "ano": 2023,
        "cor": "Vermelha",
        "statusMoto": "Ativa"
    }
]
```

### 🧪 **Testando com Postman/Insomnia**

1. **Importe a collection** com os endpoints acima
2. **Configure a base URL**: `http://52.226.54.155:8080`
3. **Teste os endpoints** com os bodies fornecidos
4. **Verifique as respostas** para validar o funcionamento

### 🔧 **Headers Necessários**

Para todas as requisições, inclua:
```http
Content-Type: application/json
Accept: application/json
```

---

## 🎥 **Demonstração em Vídeo**

### 📺 **Vídeo Completo da Aplicação**

Assista à demonstração completa do sistema de gestão de estacionamento:

**[🎬 Vídeo de Demonstração - Sistema de Gestão de Estacionamento](https://www.youtube.com/watch?v=k_JVuyBvOq0)**

### ⏰ **Timestamps do Vídeo**

#### **🧪 Teste da Aplicação (0:00 - 9:00)**
- **0:00 - 3:00**: Apresentação do projeto e funcionalidades
- **3:00 - 6:00**: Demonstração da interface web
- **6:00 - 9:00**: Teste dos endpoints da API REST

#### **🚀 Deploy na Azure (9:00 - 16:00)**
- **9:00 - 12:00**: Build da imagem Docker e configuração do ACR
- **12:00 - 14:00**: Push das imagens para Azure Container Registry
- **14:00 - 16:00**: Criação do Container Instance com variáveis de ambiente

### 🎯 **O que você verá no vídeo:**

#### **Parte 1: Teste da Aplicação (0:00 - 9:00)**
- ✅ **Interface Web**: Navegação pelas páginas de motos e vagas
- ✅ **API REST**: Testes dos endpoints com Postman/Insomnia
- ✅ **Funcionalidades**: Cadastro, edição, movimentação de motos
- ✅ **Gestão de Vagas**: Criação e controle de vagas
- ✅ **Relacionamentos**: Como motos se relacionam com vagas

#### **Parte 2: Deploy na Azure (9:00 - 16:00)**
- 🐳 **Build Docker**: Criação da imagem Docker do projeto
- 🏗️ **Azure Container Registry**: Configuração e login no ACR
- 📦 **Push de Imagens**: Upload das versões v1, v2, v3, v4
- 🚀 **Container Instance**: Criação do ACI com variáveis de ambiente
- 🔐 **Variáveis de Ambiente**: Configuração do banco de dados SQL Server
- 🌐 **Networking**: Configuração de IP público e portas

### 🚀 **Como usar o vídeo:**

1. **Para Testes**: Use a primeira parte (0:00 - 9:00) para entender como testar a aplicação
2. **Para Deploy**: Use a segunda parte (9:00 - 16:00) para ver o deploy completo na Azure
3. **Para Scripts**: Consulte o arquivo `ScriptsACRACI` com todos os comandos utilizados

### 📚 **Recursos Adicionais**

- **Código Fonte**: Disponível no repositório GitHub
- **API em Produção**: `http://52.226.54.155:8080`
- **Documentação**: Este README completo
- **Tutorial Azure**: Seção de deploy na nuvem
- **Scripts de Deploy**: Arquivo `ScriptsACRACI` com comandos utilizados

### 🔧 **Scripts de Deploy (ScriptsACRACI)**

O arquivo `ScriptsACRACI` contém todos os comandos utilizados no vídeo para o deploy:

#### **🐳 Build e Tag da Imagem**
```bash
docker build -t challenge .
docker tag challenge acrchallenge.azurecr.io/challenge:v1
```

#### **🏗️ Configuração Azure**
```bash
az login
az account set --subscription SUA_SUBSCRIPTION_ID
az group create --name rgchallengemottu --location eastus
az provider register --namespace Microsoft.ContainerRegister
```

#### **📦 Azure Container Registry**
```bash
az acr create --resource-group rgchallengemottu --name acrchallenge --sku Standard --location eastus --public-network-enabled true --admin-enabled true
az acr login --name acrchallenge.azurecr.io
```

#### **🚀 Push das Versões**
```bash
docker push acrchallenge.azurecr.io/challenge:v1
docker push acrchallenge.azurecr.io/challenge:v2
docker push acrchallenge.azurecr.io/challenge:v3
docker push acrchallenge.azurecr.io/challenge:v4
```

#### **🌐 Container Instance**
```bash
az container create \
 --resource-group rgchallengemottu \
 --name challenge-v1 \
 --image acrchallenge.azurecr.io/challenge:v4 \
 --cpu 1 --memory 1.5 \
 --registry-login-server acrchallenge.azurecr.io \
 --registry-username acrchallenge \
 --registry-password "SUA_SENHA_DO_ACR" \
 --environment-variables \
   DB_URL="jdbc:sqlserver://SEU_SERVIDOR.database.windows.net:1433;database=SEU_BANCO;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;" \
   DB_USERNAME="SEU_USUARIO_SQL" \
   DB_PASSWORD="SUA_SENHA_DO_BANCO" \
 --os-type Linux \
 --ip-address Public \
 --ports 8080
```

---

## 👨‍💻 Autor

Desenvolvido por RUAN LIMA SILVA com ❤️ para o Challenge Mottu - FIAP Global Solution
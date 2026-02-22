# Infraestrutura de API de Gerenciamento de Bibliotecas

Este projeto consiste na implementação de uma infraestrutura robusta para uma API Spring Boot, utilizando o Nginx como Proxy Reverso, Balanceador de Carga e gateway de segurança. A solução foi conteinerizada com Docker para garantir portabilidade e escalabilidade.

## Arquitetura do Sistema

A infraestrutura foi desenhada para separar as responsabilidades de rede e segurança da lógica de negócio. O Nginx atua como a única porta de entrada, gerenciando criptografia, controle de tráfego e distribuição de carga entre as instâncias da aplicação.



## Requisitos de Infraestrutura Implementados

### 1. Proxy Reverso e Segurança de Protocolo (HTTPS)
* **Redirecionamento Automático**: O servidor escuta na porta 80 e realiza o redirecionamento (301) para a porta 443 (https), onde as requisições são encaminhadas para a porta 8080 da api.
* **Terminação SSL**: Implementação de comunicação criptografada utilizando protocolos TLS com certificados autoassinados.
* **Headers de Segurança**: Inclusão de cabeçalhos HTTP para mitigação de vulnerabilidades (X-Content-Type-Options, X-Frame-Options e X-XSS-Protection).

### 2. Controle de Fluxo e Tráfego
* **Rate Limiting**: Limitação estrita de 5 requisições por segundo por endereço IP, com burst de até 10 requisições, retornando status HTTP 429 em caso de violação.
* **Limite de Payload**: Restrição do corpo das requisições (client_max_body_size) em 1MB para proteção contra exaustão de recursos.
* **Compressão GZIP**: Otimização de banda através da compressão de payloads dos tipos `application/json` e `text/plain`.

### 3. Performance e Observabilidade
* **Balanceamento de Carga**: Distribuição de requisições em modo Round Robin entre duas instâncias da API (api-biblioteca1 e api-biblioteca2).
* **Persistência de Cache**: Armazenamento temporário de respostas para o endpoint `/libraries` por 10 segundos (método GET), configurado para ignorar cabeçalhos de controle do upstream.
* **Log Estruturado**: Formato de log customizado registrando IP do cliente, método, status HTTP, endereço da instância processadora e tempo de resposta do upstream.

### 4. Gestão de Acesso e Erros
* **Autenticação Básica**: Proteção de endpoints sensíveis como `/actuator` e `/swagger-ui` via autenticação Basic Auth (arquivo .htpasswd).
* **Tratamento de Erros**: Intercepção de códigos de erro 404 e 50x para exibição de páginas HTML customizadas.



---

## Configuração do Ambiente

### Pré-requisitos
* Docker e Docker Compose instalados.
* OpenSSL para geração de certificados.

## Como Subir o Projeto

### 1. Geração de Certificados SSL
Por motivos de segurança, as chaves privadas não são incluídas no repositório. Gere-as localmente na pasta raiz do projeto:

```bash
mkdir -p nginx/ssl
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout nginx/ssl/nginx-selfsigned.key \
  -out nginx/ssl/nginx-selfsigned.crt \
  -subj "/C=BR/ST=SP/L=SaoPaulo/O=Ada/OU=Library/CN=localhost"
```

### 2. Inicialização dos Containers
Execute o comando para compilar as imagens Java e subir a infraestrutura completa:
```bash
docker compose up
```
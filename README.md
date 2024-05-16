# Aplicação de Gerenciamento de Pessoas 

Esta aplicação é um serviço RESTful construído com Spring Boot que permite o gerenciamento de pessoas e seus respectivos endereços.

## Tecnologias Utilizadas

- Java 17: Linguagem de programação utilizada para o desenvolvimento da API.
- Spring Boot 3: Framework utilizado para simplificar o processo de configuração e publicação da aplicação.
- PostgreSQL: Sistema de gerenciamento de banco de dados relacional para armazenamento de dados.
- Maven: Ferramenta de automação de compilação e gerenciamento de dependências.

## Recursos

A aplicação fornece os seguintes recursos:

### Pessoas

- `POST /pessoas`: Cria uma nova pessoa.
- `GET /pessoas`: Consulta todas as pessoas.
- `GET /pessoas/{id}`: Consulta uma pessoa por ID.
- `GET /pessoas/nome/{nomeCompleto}`: Consulta pessoas pelo nome.
- `PUT /pessoas/{id}`: Edita uma pessoa.
- `GET /pessoas/ids`: Consulta mais de uma pessoa pelos seus IDs.

### Endereços

- `POST /enderecos`: Cria um novo endereço.
- `GET /enderecos`: Consulta todos os endereços.
- `GET /enderecos/{pessoaId}/principal`: Consulta o endereço principal de uma pessoa.
- `GET /enderecos/{id}`: Consulta um endereço por id.

## Instalação e Execução

Para executar esta aplicação, você precisará ter o Java e o Maven instalados em sua máquina. Depois de clonar o repositório, você pode construir e executar a aplicação.

### Verifique a instalação do Java e Maven
- `java -version`
- `mvn -version`

### Clone o repositório
git clone ```https://github.com/Dayanaferrer/gerenciamento-pessoas.git ```

### Navegue até o diretório do projeto
``` cd gerenciamento-pessoas ```

### Construa o projeto
```mvn clean install ```

### Execute a aplicação
```mvn spring-boot:run ```

## Documentação da API

A documentação da API é gerada automaticamente usando o Swagger. Você pode acessar a documentação da API em `http://localhost:8080/swagger-ui.html` quando a aplicação estiver em execução.

## Testes

Os testes podem ser executados com o seguinte comando:

```bash
mvn test 
```
## Contribuindo

Contribuições são bem-vindas. Por favor, faça um fork do repositório e crie um pull request com suas alterações. 

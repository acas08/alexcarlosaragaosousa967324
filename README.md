# PROCESSO SELETIVO CONJUNTO Nº 001/2026/SEPLAG e demais Órgãos - Engenheiro da Computação- Sênior

N° Inscrição : 16486

Data da inscrição 25/01/2026 11:26:21

PCD: Não

Nome: ALEX CARLOS ARAGAO SOUSA

Email: alexcarlos@gmail.com

CPF: 967.324.231-34

# Projeto Acervo Musical

Spring Boot iniciado com Docker.

## Passos para executar o projeto

Para executar o projeto localmente, execute o seguinte comando:
```bash
mvn spring-boot:run
```
Para executar o projeto com Docker, execute o seguinte comando:
```bash
docker build -t acervo-teste .
docker run --rm -p 8080:8080 acervo
```

Para executar o projeto com Docker Compose, execute o seguinte comando:
```bash
docker compose up --build
```

## Testes unitários

Testes unitário com JUnit 5 e Mockito.

Utilizamos jacoco para gerar relatórios de cobertura de código.

A cobertura mínima esperada é de 70%.

Para executar os testes unitários, execute o seguinte comando:
```bash
mvn clean test

mvn verify
```

## Segurança da Aplicação
Autenticação JWT com expiração a cada 5 minutos e possibilidade de renovação

Endpoint de login com usuário e senha. Esse endpoint retorna um token com expiração de 5 minutos
e retorna um refreshToken que pode ser usado para obter um novo token.

/api/auth/login
As credenciais para obter o login estão no arquivo ".env" na raiz do projeto, sendo elas:
LOGIN_USER e LOGIN_PASSWORD.

## Health Checks e Liveness/Readiness
Utilizamos o actuator para criar endpoints de health checks e liveness/readiness.
Liveness: GET /acervo/actuator/health/liveness
Readiness: GET /acervo/actuator/health/readiness
Vai ficar DOWN se qualquer um entre db, minio, argus falhar

## Migrations
Utilizamos o Flyway para gerenciar as migrations.
Os arquivos de migração estão na pasta "src/main/resources/db/migration".

## Tecnologias utilizadas
- Java 17
- Spring Boot 
- Maven
- Docker
- Containerization (API + MinIO + BD) via docker-compose
- Flyway Migrations

## Implementações realizads para o projeto
- Autenticação JWT com expiração a cada 5 minutos e possibilidade de renovação.
- Implementar POST, PUT, GET.
- Paginação na consulta dos álbuns.
- Expor quais álbuns são/tem cantores e/ou bandas (consultas parametrizadas).
- Consultas por nome do artista com ordenação alfabética (asc/desc).
- Upload de uma ou mais imagens de capa do álbum.
- Armazenamento das imagens no MinIO (API S3).
- Recuperação por links pré-assinados com expiração de 30 minutos.
- Versionar endpoints.
- Flyway Migrations para criar e popular tabelas.
- Documentar endpoints com OpenAPI/Swagger.
- Health Checks e Liveness/Readiness.
- Testes unitários.
- WebSocket para notificar o front a cada novo álbum cadastrado.
- Rate limit: até 10 requisições por minuto por usuário.
- Endpoint de regionais (https://integrador-argus-api.geia.vip/v1/regionais):
- Importar a lista para tabela interna;
- Adicionar atributo “ativo” (regional (id integer, nome varchar(200), ativo boolean));
- Sincronizar com menor complexidade:
1) Novo no endpoint → inserir;
2) Ausente no endpoint → inativar;
3) Atributo alterado → inativar antigo e criar novo registro




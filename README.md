## Cadastro de Clientes

### Gere o jar

```bash
mvn package 
```

### Copie o jar gerado para a pasta docker

```bash
cp ./target/builders-service.jar ./docker/
```

### Navegando até a pasta docker

```bash
cd docker
```

### Iniciando Swarm 

```bash
docker swarm init --advertise-addr <ENDEREÇO-DE-IP> 
```

### Baixando MYSQL

```bash
docker pull mysql
```

### Criando imagem do service - Executar esse comando dentro da pasta onde está o .jar e o Dockerfile

```bash
docker image build -t builders-service .
```

### Iniciando Stack Rodando docker-compose

```bash
- docker stack deploy -c docker-compose.yml app
```

### Listando os containers

```bash
docker container ls
```

### Acessando Container Mysql

```bash
docker container exec -it <ID-CONTAINER-DB> bash
```

### Acessando Mysql

```bash
mysql -uroot -proot
```

### Setando Banco Default

```bash
use builders;
```

### Criar Tabelas

```bash
Copie e cole no terminal os comandos de criação das tabelas - ./docker/create-tables.sql
```

### Visalizando tabelas criadas

```bash
- show tables;
```

### Saindo

```bash
exit
```

### Documentação da API

- http://localhost:8097/api/builders/swagger-ui/index.html?configUrl=/api/builders/v3/api-docs/swagger-config#/

### A Api e a documentação támbem estão na DigitalOcean no seguinte endereço.

- http://165.22.177.22:8097/api/builders/swagger-ui/index.html?configUrl=/api/builders/v3/api-docs/swagger-config#/

### Na pasta ./postman/ há um arquivo Postman para fácil utilização da API
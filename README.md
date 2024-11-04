# kalanblow:
In this project I'm demonstrating a simple architecture of microservices that perform distributed transactions. The example application applications are simple Spring Boot app that expose some HTTP endpoints for CRUD operations and connects to Postgres using Spring Data JPA.

Getting Started
All the examples are described in a separated articles on my blog. Here's a full list of available examples:

Rollback or confirmation of distributed transaction across gestiondeseleves service, gestiondescours and gestiondesnotes. A detailed guide may be find in the following article: Distributed Transaction in Microservices with Spring Boot
Usage
1) mvn compile package

2) Start discovery-server. It is available on port 8761.
Start RabbitMQ on Docker with command docker run -d --name rabbit -h rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3-management

Start Postgres on Docker with command docker run -d --name postgres -p 5432:5432 -e POSTGRES_USER= XXX -e POSTGRES_PASSWORD=XXX -e POSTGRES_DB=XXX postgres
Start microservices: gestiondeseleves service, gestiondescours and gestiondesnotes. The app gestiondeseleves is listening on port 8080.

Docker
You can test all the apps on Docker. To do that first build the whole project using the following command:

3) mvn clean package -DskipTests -Pbuild-image
Then you can just run all the apps including RabbitMQ and Postgres with docker-compose:

4) docker compose up --build

5) docker run -d -p [HOST_PORT]:[CONTAINER_PORT] kalanblow/gestiondeseleves:0.0.1-SNAPSHOT

6) docker run -d -p [HOST_PORT]:[CONTAINER_PORT] kalanblow/gestiondescours:0.0.1-SNAPSHOT

7. Démarrage avec Maven Spring Boot Plugin (sans Docker)
Vous pouvez aussi démarrer chaque application directement avec Maven. Cela est utile pour le développement et les tests en local, mais n'est pas recommandé pour la production.

Depuis le répertoire de chaque sous-module, exécutez :

mvn spring-boot:run

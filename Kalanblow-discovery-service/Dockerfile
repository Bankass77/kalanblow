# Utiliser l'image de base openjdk pour exécuter une application Java
FROM openjdk:17-jdk-alpine

# Définir le répertoire de travail dans le conteneur
WORKDIR /app/kalanblow-discovery-service

# Copier le fichier JAR généré par Maven/Gradle dans l'image Docker
COPY target/kalanblow-0.0.1-SNAPSHOT.jar kalanblow-discovery-service.jar

# Exposer le port sur lequel l'Eureka Server va écouter (par exemple 8761)
EXPOSE 8761

# Commande pour exécuter l'application Spring Boot
ENTRYPOINT ["java", "-jar", "kalanblow-discovery-service.jar"]

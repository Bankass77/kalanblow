# Étape 1 : Build de l'application avec Maven
FROM maven:3.8.4-openjdk-17 AS builder

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de configuration Maven (pom.xml)
COPY ./pom.xml .

# Télécharger les dépendances
RUN mvn dependency:go-offline

# Copier tout le code source du projet dans l'image
COPY . .

# Compiler le projet et créer le JAR
RUN mvn clean package -DskipTests

# Étape 2 : Image finale de l'application avec OpenJDK
FROM openjdk:17-jdk-slim-buster

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR de l'étape précédente
COPY --from=builder /app/target/gestiondesinscriptions-0.0.1-SNAPSHOT.jar /app/gestiondesinscriptions.jar

# Exposer le port 8080
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "/app/gestiondesinscriptions.jar"]

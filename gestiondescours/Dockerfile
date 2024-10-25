# Étape 1 : Construire avec Maven intégré
FROM maven:3.8.4-openjdk-17 AS builder

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de configuration du projet Maven
COPY ./pom.xml  .

# Télécharger les dépendances
RUN mvn dependency:go-offline

# Copier le code source et construire le projet
COPY . .
# Compiler le projet et créer le JAR
RUN mvn clean package -DskipTests

# Étape 2 : Image finale de l'application avec OpenJDK
FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY --from=builder app/target/gestiondescours-0.0.1-SNAPSHOT.jar /app/gestiondescours.jar

# Exposer le port 8080
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "/app/gestiondescours.jar"]

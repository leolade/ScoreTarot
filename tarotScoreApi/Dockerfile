# Étape unique : run d’un JAR Spring Boot précompilé
FROM eclipse-temurin:21-jre-alpine

# Dossier de travail dans le conteneur
WORKDIR /app

# Copie du fichier JAR généré
COPY ./build/libs/tarot.score-0.0.1-SNAPSHOT.jar app.jar

# Port exposé (modifie selon ton application)
EXPOSE 8080

# Commande de lancement
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
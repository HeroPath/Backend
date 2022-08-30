FROM openjdk:11
ADD target/aoweb-backend-0.0.1-SNAPSHOT.jar aoweb.jar
ENTRYPOINT ["java", "-jar","aoweb.jar"]
EXPOSE 8080
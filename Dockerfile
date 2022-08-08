FROM openjdk:18
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} aoweb-backend.jar
ENTRYPOINT ["java","-jar","aoweb-backend.jar"]

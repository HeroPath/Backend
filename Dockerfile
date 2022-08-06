FROM openjdk:18
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} aoweb-backend.jar
ENTRYPOINT ["java","-jar","/aoweb-backend.jar"]

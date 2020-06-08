FROM openjdk:8-jre
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/spring-docker-ibyte.jar
WORKDIR /app
ENTRYPOINT java -jar spring-docker-ibyte.jar
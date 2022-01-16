FROM adoptopenjdk:11-jre-hotspot as builder
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} creek.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","creek.jar"]

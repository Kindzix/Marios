FROM openjdk:11

WORKDIR /app

COPY target/Mario-1.0.jar /app/Mario-1.0.jar
COPY src/main/resources/application.properties /app/application.properties

EXPOSE 8083

CMD ["java", "-jar", "Mario-1.0.jar"]

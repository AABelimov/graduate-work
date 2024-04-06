FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV DB_PORT 5432
ENV DB_NAME dbname
ENV DB_USER_NAME username
ENV DB_PASS pass
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.datasource.url=jdbc:postgresql://host.docker.internal:${DB_PORT}/${DB_NAME}", "-Dspring.datasource.username=${DB_USER_NAME}", "-Dspring.datasource.password=${DB_PASS}", "-jar","/app.jar"]
FROM gradle:7.5.1-jdk17-alpine as build

VOLUME /tmp
COPY /build/libs/*.jar /app.jar
# Run the jar file
EXPOSE 8081 8081
ENTRYPOINT ["java","-jar","/app.jar"]
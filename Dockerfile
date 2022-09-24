FROM openjdk:11

COPY target/book-store-app-0.0.1-SNAPSHOT.jar bookstore.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/bookstore.jar"]
FROM openjdk:11-jdk-slim
EXPOSE 8080
RUN mkdir "images"
COPY build build
#ARG JAR_FILE=/build/libs/matdongsan-0.0.1-SNAPSHOT.jar
VOLUME ["/var/log"]
#COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/build/libs/matdongsan-0.0.1-SNAPSHOT.jar"]
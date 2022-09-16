FROM openjdk:11-jdk-slim
EXPOSE 8080
RUN mkdir "images"
ARG BUILD_DIR=/build
VOLUME ["/var/log"]
COPY ${BUILD_DIR} /build
COPY /chromedriver /driver/chromedriver
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/build/libs/matdongsan-0.0.1-SNAPSHOT.jar"]
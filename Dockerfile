FROM openjdk:11-jdk-slim
EXPOSE 443
RUN mkdir "images"

RUN apt-get -y update
RUN apt install wget -y
RUN apt install unzip -y
RUN apt install curl -y
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt -y install ./google-chrome-stable_current_amd64.deb
RUN wget -O /tmp/chromedriver.zip http://chromedriver.storage.googleapis.com/` curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE`/chromedriver_linux64.zip
RUN mkdir chrome
RUN unzip /tmp/chromedriver.zip chromedriver -d /driver

ARG BUILD_DIR=/build
VOLUME ["/var/log"]
COPY ${BUILD_DIR} /build
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/build/libs/matdongsan-0.0.1-SNAPSHOT.jar"]
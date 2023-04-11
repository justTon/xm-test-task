FROM openjdk:17
VOLUME /tmp
RUN mkdir -p /var/local/prices
COPY prices /var/local/prices
EXPOSE 8080
ARG JAR_FILE=target/crypto-recommend-docker.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
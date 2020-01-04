FROM openjdk:8
COPY ./target/hc-carlistingapp-0.0.1-SNAPSHOT.jar /usr/src/hc/
COPY ./data/ /usr/src/hc/data
WORKDIR /usr/src/hc
EXPOSE 8080
CMD ["java", "-jar", "hc-carlistingapp-0.0.1-SNAPSHOT.jar"]
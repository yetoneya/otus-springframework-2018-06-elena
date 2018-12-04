FROM java:8
VOLUME /tmp
EXPOSE 9090
ADD /target/spring-docker-1.0-SNAPSHOT.jar spring-docker-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://spring-docker-mongo:27017/mongo-docker","-Djava.security.egd=file:/dev/./urandom", "-jar","spring-docker-1.0-SNAPSHOT.jar"]
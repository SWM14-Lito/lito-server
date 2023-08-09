FROM openjdk:17

COPY ./api/build/libs/api-0.0.1-SNAPSHOT.jar api.jar

ENTRYPOINT ["java","-jar","api.jar"]
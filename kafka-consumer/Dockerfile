FROM gradle:latest AS build
COPY --chown=gradle:gradle ./kafka-consumer /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test

FROM openjdk:latest

EXPOSE 8080

RUN rm -rf /app && mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/kafka-consumer.jar

ENTRYPOINT ["java", "-jar", "/app/kafka-consumer.jar"]
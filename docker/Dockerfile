FROM gradle:7.2.0-jdk17 AS builder

ARG BUILD_PATH=/app

WORKDIR ${BUILD_PATH}
COPY --chown=gradle:gradle . .
RUN gradle clean \
  && gradle build


FROM openjdk:17-oracle

ARG ROOT_PATH=/opt/app
ARG BUILD_PATH=/app
ARG JAR_NAME=rest-0.0.1-SNAPSHOT.jar

WORKDIR ${ROOT_PATH}
COPY --from=builder /app/build/libs/${JAR_NAME} ./run.jar

ENTRYPOINT [ "java", "-jar", "run.jar" ]

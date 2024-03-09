FROM openjdk:17-jdk
ARG JAR_PATH=server/api/build/libs
ARG PROFILE=dev
COPY ${JAR_PATH}/api-0.0.1-SNAPSHOT.jar api.jar
ENTRYPOINT java -jar -Dspring.profiles.active=$PROFILE
CMD ["/api.jar"]

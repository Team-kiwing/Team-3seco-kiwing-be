FROM openjdk:17-jdk
ARG JAR_PATH=server/api/build/libs
COPY ${JAR_PATH}/api-0.0.1-SNAPSHOT.jar api.jar
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005", "-jar", "-Dspring.profiles.active=dev"]
CMD ["/api.jar"]

FROM bellsoft/liberica-openjdk-alpine:latest

WORKDIR /app

COPY . .

RUN ./gradlew clean test

RUN ./gradlew build

ENV JAVA_TOOL_OPTIONS "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

CMD ["java", "-jar", "build/libs/spring-boot-jwt-0.0.1-SNAPSHOT.jar"]
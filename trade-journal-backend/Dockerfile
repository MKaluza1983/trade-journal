FROM eclipse-temurin:21-alpine

RUN mkdir -p /home/service

WORKDIR /home/service

COPY . .

RUN ./gradlew bootJar --no-daemon

CMD ["java", "-jar", "build/libs/trades-backend-1.0.jar"]

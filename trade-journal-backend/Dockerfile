FROM amazoncorretto:21.0.3

RUN mkdir -p /home/service

WORKDIR /home/service

COPY . .

RUN ./gradlew bootJar --no-daemon

CMD ["java", "-jar", "build/libs/trade-journal-backend-1.0.jar"]

FROM amazoncorretto:11-al2-full

EXPOSE 8080

RUN mkdir /app

COPY build/libs/*.jar /app/
COPY build/libs/tondeuse-1.0.0-SNAPSHOT.jar /app/tondeuse-1.0.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/tondeuse-1.0.0-SNAPSHOT.jar"]
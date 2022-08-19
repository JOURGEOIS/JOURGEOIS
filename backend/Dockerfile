FROM openjdk:11-jdk

WORKDIR /app

COPY ../build/libs/*SNAPSHOT.jar application.jar

EXPOSE 8080

CMD ["java", "-jar", "-Duser.timezone=Asia/Seoul", "application.jar"]

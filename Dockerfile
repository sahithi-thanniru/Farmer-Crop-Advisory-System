FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY . .

RUN mkdir -p out && javac -cp "lib/mysql-connector-j-26.7.0.jar" -d out $(find src -name "*.java")

EXPOSE 8080

CMD ["java", "-cp", "out:lib/mysql-connector-j-26.7.0.jar", "web.WebServer"]

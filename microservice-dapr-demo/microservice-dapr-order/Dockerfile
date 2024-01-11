FROM eclipse-temurin:21.0.1_12-jre-alpine
COPY target/microservice-dapr-order-0.0.1-SNAPSHOT.jar .
CMD java -Xmx300m -Xms300m -XX:TieredStopAtLevel=1 -noverify -jar microservice-dapr-order-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:postgresql://postgres:5432/dbinvoicing
EXPOSE 8081

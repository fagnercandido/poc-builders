FROM maven:3.6.3-jdk-11-slim as build

WORKDIR /clientes

COPY pom.xml .

RUN mvn -f pom.xml dependency:go-offline -B

COPY src src

RUN mvn -f pom.xml package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11-jre-slim as production
ARG DEPENDENCY=/clientes/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java", "-cp", "app:app/lib/*","io.platformbuilders.clientes.Application"]
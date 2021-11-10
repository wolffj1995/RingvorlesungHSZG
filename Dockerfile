FROM adoptopenjdk/openjdk11-openj9:alpine-jre as builder
WORKDIR /build
ARG JAR_FILE=app/target/app.jar
COPY ${JAR_FILE} application.jar
RUN adduser -D -u 1000 app && \
    java -Djarmode=layertools -jar application.jar extract && \
    chown -R app:users .

FROM adoptopenjdk/openjdk11-openj9:alpine-jre
VOLUME /tmp
WORKDIR /app
COPY --from=builder /build/dependencies/ ./
COPY --from=builder /build/spring-boot-loader/ ./
COPY --from=builder /build/internal-dependencies/ ./
COPY --from=builder /build/snapshot-dependencies/ ./
COPY --from=builder /build/application/ ./
USER 1000
ENV JAVA_OPTS ''

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} org.springframework.boot.loader.JarLauncher"]



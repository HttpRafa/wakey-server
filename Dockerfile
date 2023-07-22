ARG JAVA_VERSION=17

FROM eclipse-temurin:${JAVA_VERSION} AS builder
WORKDIR /build

COPY ./ ./
RUN chmod +x gradlew
RUN ./gradlew clean build --no-daemon

FROM eclipse-temurin:${JAVA_VERSION} AS runtime
WORKDIR /app

RUN groupadd --system wakey \
    && useradd --system wakey --gid wakey \
    && chown -R wakey:wakey /app
USER wakey:wakey

VOLUME /data/wakey
EXPOSE 4567

COPY ./docker/wakey.json ./wakey.json
COPY --from=builder /build/app/build/libs/app-all.jar ./

CMD ["java", "-jar", "/app/app-all.jar"]

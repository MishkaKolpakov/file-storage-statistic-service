FROM java:8-jdk-alpine

ENV RABBIT_HOST=rabbitmq

WORKDIR /app

COPY target/StatisticsService-1.0-SNAPSHOT.jar .
COPY rootfs/ .


ENTRYPOINT ["/bin/sh"]
CMD ["/app/app-entrypoint.sh"]
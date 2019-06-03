# Dockerfile for ${artifactId}-distribution-spring-boot-app
# Build with:
# docker build -t ${artifactId}-distribution-spring-boot-app .
# Run with:
# docker run -p 9090:9090 -d -v /tmp/logs:/opt/logs --name ${artifactId}-distribution-spring-boot-app ${artifactId}-distribution-spring-boot-app

FROM openjdk:8-jre-alpine
MAINTAINER Elve.Xu <iskp.me@gmail.com>

ENV IN_VERSION ${version}

RUN echo "http://mirrors.aliyun.com/alpine/v3.6/main" > /etc/apk/repositories \
    && echo "http://mirrors.aliyun.com/alpine/v3.6/community" >> /etc/apk/repositories \
    && apk update upgrade \
    && apk add --no-cache procps unzip curl bash tzdata \
    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone

ADD ${artifactId}-distribution-spring-boot-app-${IN_VERSION}.zip /${artifactId}-distribution-spring-boot-app/${artifactId}-distribution-spring-boot-app-${IN_VERSION}.zip

RUN unzip /${artifactId}-distribution-spring-boot-app/${artifactId}-distribution-spring-boot-app-${IN_VERSION}.zip -d /${artifactId}-distribution-spring-boot-app \
    && rm -rf /${artifactId}-distribution-spring-boot-app/${artifactId}-distribution-spring-boot-app-${IN_VERSION}.zip \
    && sed -i '$d' /${artifactId}-distribution-spring-boot-app/bin/startup.sh \
    && echo "tail -f /dev/null" >> /${artifactId}-distribution-spring-boot-app/bin/startup.sh

EXPOSE 9090

CMD ["/${artifactId}-distribution-spring-boot-app/bin/startup.sh"]

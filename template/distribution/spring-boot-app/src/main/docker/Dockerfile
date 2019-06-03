# Dockerfile for ${__artifactId__}-distribution-spring-boot-app
# Build with:
# docker build -t ${__artifactId__}-distribution-spring-boot-app .
# Run with:
# docker run -p 9090:9090 -d -v /tmp/logs:/opt/logs --name ${__artifactId__}-distribution-spring-boot-app ${__artifactId__}-distribution-spring-boot-app

FROM openjdk:8-jre-alpine
MAINTAINER Elve.Xu <iskp.me@gmail.com>

ENV IN_VERSION ${__version__}

RUN echo "http://mirrors.aliyun.com/alpine/v3.6/main" > /etc/apk/repositories \
    && echo "http://mirrors.aliyun.com/alpine/v3.6/community" >> /etc/apk/repositories \
    && apk update upgrade \
    && apk add --no-cache procps unzip curl bash tzdata \
    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone

ADD ${__artifactId__}-distribution-spring-boot-app-${IN_VERSION}.zip /${__artifactId__}-distribution-spring-boot-app/${__artifactId__}-distribution-spring-boot-app-${IN_VERSION}.zip

RUN unzip /${__artifactId__}-distribution-spring-boot-app/${__artifactId__}-distribution-spring-boot-app-${IN_VERSION}.zip -d /${__artifactId__}-distribution-spring-boot-app \
    && rm -rf /${__artifactId__}-distribution-spring-boot-app/${__artifactId__}-distribution-spring-boot-app-${IN_VERSION}.zip \
    && sed -i '$d' /${__artifactId__}-distribution-spring-boot-app/bin/startup.sh \
    && echo "tail -f /dev/null" >> /${__artifactId__}-distribution-spring-boot-app/bin/startup.sh

EXPOSE 9090

CMD ["/${__artifactId__}-distribution-spring-boot-app/bin/startup.sh"]

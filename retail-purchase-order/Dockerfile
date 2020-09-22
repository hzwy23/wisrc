FROM swr.cn-north-1.myhuaweicloud.com/smartdo/matrix-java:1.0

MAINTAINER zhanwei_huang hzwy23@163.com

WORKDIR /opt

ADD . ./basic

WORKDIR basic


RUN mvn clean package -DskipTests=true

EXPOSE 8080

ENV TZ Asia/Shanghai

CMD java -Xms1638m -Xmx1638m  -jar target/basic-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

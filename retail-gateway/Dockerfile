FROM swr.cn-north-1.myhuaweicloud.com/smartdo/matrix-java:1.0

MAINTAINER zhanwei_huang hzwy23@163.com

WORKDIR /opt

ADD . ./zuul

WORKDIR zuul 

RUN mvn clean package -DskipTests=true

EXPOSE 8790

CMD java -Xms3272m -Xmx3272m  -jar target/zuul-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

FROM swr.cn-north-1.myhuaweicloud.com/smartdo/matrix-java:1.0

RUN localedef -c -f UTF-8 -i zh_CN zh_CN.utf8

ENV LC_ALL "zh_CN.UTF-8"

MAINTAINER zhanwei_huang hzwy23@163.com

WORKDIR /opt

ADD . erp-images-manager

WORKDIR erp-images-manager

# 复制pdf 打印所需中文字体
COPY ./bootstrap/src/main/resources/fonts/ /usr/share/fonts/

RUN mvn clean package -DskipTests=true

EXPOSE 8080

CMD java -Xms1280m -Xmx1280m -jar bootstrap/target/bootstrap-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

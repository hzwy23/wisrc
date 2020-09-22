# erp-server-template-java 基础框架介绍

## 主要集成功能
- **spring-boot 2.0.0**
- **mybatis orm组件**
- **swagger2 组件**
- **druid 连接池组件**
- **spring-boot actuator 应用状态监控组件**
- **mysql连接组件**
- **thymeleaf 模板组件**
- **eureka客户端**
- **Feign组件**
- **PageHelper 分页插件**

## 配置文件介绍
java基础框架的配置信息在resource目录中，目录中共有4个配置文件，分别是：
```shell
application.yaml         -- 应用启动后一定会读取的配置文件
application-dev.yaml     -- spring.profiles.active = dev时读取的配置文件
application-test.yaml    -- spring.profiles.active = test时读取的配置文件
application-prod.yaml    -- spring.profiles.active = prod时读取的配置文件
```
上述的4个配置文件，在不同的环境中作用不同。application.yaml作为基础配置信息，其余三个配置文件为3种不同环境时，不同的配置情况。

application.yaml配置文件介绍
```yaml
## 用来指定哪个配置文件有效，如dev表示application-dev.yaml配置文件有效
## 在程序运行中，也可以通过参数的方式修改这个配置变量的值
## java -jar basic-0.0.1.jar --spring.profiles.active=test 
## 上边的命令，表示使用application-test.yaml这个配置文件
spring:
  profiles:
    active: dev

## 设置日志级别
logging:
  level:
    root: info

## web服务开启端口
# context-path 表示所有api的前缀
server:
  port: 8080
  servlet:
    context-path: /demo
```

application-dev.yaml配置文件介绍（test,prod与dev配置项目中的值相同）
```yaml
## 使用druid配置数据源
## spring.application.name 这个值是应用的名称，当应用注册到服务与发现注册中心时，使用这个名字
spring:
  application:
    name: basic-test-demo-dev
## 数据库配置信息，在开发中，只需要修改url变量中数据库连接信息，username，password信息即可
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      driver-class-name: com.mysql.jdbc.Driver
      username: root
      password: Smart_888
      url: jdbc:mysql://117.78.44.209:8635/erp_basic?characterEncoding=UTF-8
      max-active: 20
      validation-query: SELECT 1 FROM DUAL
      initial-size: 10
      max-wait: 60000

## eureka server 地址， 根据实际情况设置eureka server地址
## 在实际的开发中，
eureka:
  client:
    service-url:
      defaultZone: http://49.4.2.78:8080/eureka
  instance:
    prefer-ip-address: true
## ip-address 和 instance-id 这两个值配置后，当应用被注册到服务与发现注册中心，这个服务对应的地址将会被想变的变量替换
## 如果不指定下边的值，默认情况下，应用被注册到服务与发现注册中心时，会默认获取第一张网卡的ip地址和应用端口号，将其作为这个微服务
## 的机器地址和端口号
    ip-address: 49.4.2.78
    instance-id: 49.4.2.78:8791
```

## 配置文件应用场景介绍
- **1**、  application-dev.yaml 用于本地开发时使用
- **2**、  application-test.yaml 用于发布到私有云时使用
- **3**、  application-prod.yaml 用于在生产环境时使用

**在开发过程中，目前已经根据现有的环境配置好了地址信息，在开发、测试的过程中咱是不需要修改**

## 编译运行方法
```shell
git clone https://gitee.com/sdit/erp-server-template-java.git
cd erp-server-template-java
mvn clean package -DskipTests=true
```
验证方法：
> 在浏览器中输入： http://localhost:8080/demo
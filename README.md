# 万马电商ERP
一万匹马奔腾

# 控制台
- 控制台地址：http://localhost:8080/ui
- 账号/密码：admin/123456

![example](./doc/index.png)

# 数据库
- 支持数据库： MySQL
- 数据库初始化脚本位置：db目录下 wisrc_db.sql

**数据库配置信息在 retail-gateway 模块中**

# 服务启动
```
## 1. 编译代码
mvn clean package -DskipTests=true

## 2. 启动服务
java -jar retail-gateway/target/retail-gateway-0.0.1-SNAPSHOT.jar 
```

# DEMO体验版本
- 地址： http://118.31.46.174:8080/ui
- 账号/密码：admin/123456

**请不要随意删除里边的内容**
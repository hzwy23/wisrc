# zuul
zuul 边缘服务，内部微服务API统一入口


## 权限验证接口
与权限校验相关的配置信息
```yaml
login:
  url: /login
  identifyUrl: http://49.4.2.78:8790/login
  identify:
    type: db
```

### 目前支持两种权限认证的接口，
> 分别是直接查询数据库

> 远程调用API

#### 1.直接查询数据库方法鉴权
1. 在zuul应用使用的数据库用户下边创建视图，视图中包含两个字段，分别是：account, password。account是用户id，password是用户密码

2. 在配置文件中，将login.identify.type 值设置成 db

#### 2.远程API鉴权
1. 在配置文件中设置login.identify.type值设置成api

2. 在配置文件中设置login.identifyUrl的值为远程api接口，这个远程api接口接收两个参数，分别是username，password。api在校验用户密码正确后，只需要设置http返回状态码为200即可，如果校验错误，设置http的返回状态码为403



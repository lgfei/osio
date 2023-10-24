# osio-admin
服务治理

## 同一个应用在 spring-admin-server 注册了两次
[相关的 github issue](https://github.com/codecentric/spring-boot-admin/issues/1392)
- 方案1: 将所有应用的 spring.application.name=全大写。建议只在验证问题的时候使用
- 方案2: 不要将 osio-admin 注册到 eureka, 即：eureka.client.enable=false，或者删除 eureka 的依赖和配置。osio-admin 本身会失去监控
- 方法3: 修改源码: 覆盖 de.codecentric.boot.admin.server.domain.values.Registration, 将构造方法中的 this.name = name; 改为 this.name = name.toLowerCase();

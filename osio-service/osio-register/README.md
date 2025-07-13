# osio-register
注册中心

## spring-cloud版本从2022.0.4降至2020.0.4之后eureka注册中心需要去掉eureka-client的依赖
```txt
Caused by: java.lang.IllegalStateException: java.lang.RuntimeException: Cannot Create new Replica Node :JerseyReplicationClient: http://127.0.0.1:7001/eureka/apps/:
```
去掉spring-cloud-starter-netflix-eureka-client依赖后正常启动
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

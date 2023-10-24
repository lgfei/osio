# osio-gateway
网关

## @EnableEurekaClient 没有了
启动类不再需要该注解，开启自动装配注解后会通过EurekaConfigServerBootstrapConfiguration自动配置Eureka Client

## optionalArgs
错误信息
<pre>
Field optionalArgs in org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration$RefreshableEurekaClientConfiguration required a bean of type 'com.netflix.discovery.AbstractDiscoveryClientOptionalArgs' that could not be found.

The injection point has the following annotations:
    - @org.springframework.beans.factory.annotation.Autowired(required=true)


Action:

Consider defining a bean of type 'com.netflix.discovery.AbstractDiscoveryClientOptionalArgs' in your configuration.


Process finished with exit code 1
</pre>
问题产生原因: 高版本的SpringBoot项目需要添加spring-boot-starter-web依赖<br>
解决方法：
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
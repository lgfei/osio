# OSIO-基于微服务架构开源免费的基础业务框架
一套基于Spring Cloud的微服务应用程序框架，封装了大量技术开发包、技术应用组件、技术场景实现能力，可帮助公司更快，更高效地进行微服务开发。

## OSIO源码说明
OSIO由多个微服务程序组成，如果您要查找单个组件，则可访问组件自己的Github地址。
- [osio-register](https://github.com/lgfei/osio-register) - 注册中心，基于Eureka的平台注册中心服务，包括服务注册发现，服务健康检查，服务监控，注册中心其他功能。
- [osio-gateway](https://github.com/lgfei/osio-gateway) - 服务网关，基于Spring Cloud Gateway进行二次封装，作为平台统一的对外出入口，主要有服务路由、鉴权、流量控制等管理功能。
- [osio-oauth](https://github.com/lgfei/osio-oauth) - 授权管理，基于 Spring Security、Spring OAuth2、JWT 实现的统一认证服务中心，登录基于 spring security 的标准登录流程。
- [osio-admin](https://github.com/lgfei/osio-admin) - 服务治理，基于 Spring Boot Admin实现的服务状态监控，基础配置。
- [osio-file](https://github.com/lgfei/osio-file) - 文件管理服务，提供简单易用的文件存储功能，具备对接多种云对象存储服务的能力且易于拓展，同时支持服务器ftp协议文件上传。

## 组件依赖
| 组件 | 描述 | 版本 |
| :--- | :--- | :--- |
| JDK | Java运行环境 | 17.0.7 |
| Maven | 项目构建 | 3.6.0 |

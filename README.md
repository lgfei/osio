# OSIO-基于微服务架构开源免费的基础业务框架
一套基于Spring Cloud的微服务应用程序框架，封装了大量技术开发包、技术应用组件、技术场景实现能力，可帮助公司更快，更高效地进行微服务开发。

## OSIO源码说明
- [osio-front](https://github.com/lgfei/osio/tree/main/osio-front) 基于React开发的前端页面代码
- [osio-parent](https://github.com/lgfei/osio/tree/main/osio-parent) 依赖项版本管理
- [osio-service](https://github.com/lgfei/osio/tree/main/osio-service) 后端微服务模块
  - [osio-register](https://github.com/lgfei/osio/tree/main/osio-service/osio-register) 注册中心，基于Eureka的平台注册中心服务，包括服务注册发现，服务健康检查，服务监控，注册中心其他功能。
  - [osio-admin](https://github.com/lgfei/osio/tree/main/osio-service/osio-admin) 服务治理，基于 Spring Boot Admin实现的服务状态监控，基础配置。
  - [osio-auth](https://github.com/lgfei/osio/tree/main/osio-service/osio-auth) 授权服务器，基于 Spring Security、Spring OAuth2、JWT 实现的统一认证服务中心，登录基于 spring security 的标准登录流程。
  - [osio-gateway](https://github.com/lgfei/osio/tree/main/osio-service/osio-gateway) 服务网关，基于Spring Cloud Gateway进行二次封装，作为平台统一的对外出入口，主要有服务路由、鉴权、流量控制等管理功能。
  - [osio-iam](https://github.com/lgfei/osio/tree/main/osio-service/osio-iam) 用户权限数据服务。
  - [osio-file](https://github.com/lgfei/osio/tree/main/osio-service/osio-file) 文件管理服务，提供简单易用的文件存储功能，具备对接多种云对象存储服务的能力且易于拓展，同时支持服务器ftp协议文件上传。
- [osio-starter-parent](https://github.com/lgfei/osio/tree/main/osio-starter-parent) 自定义Spring boot Starter。
  - [osio-starter-core](https://github.com/lgfei/osio/tree/main/osio-starter-parent/osio-starter-core) 核心依赖管理。

## 组件依赖
| 组件 | 描述 | 版本 |
| :--- | :--- | :--- |
| JDK | Java运行环境 | 17.0.7 |
| Maven | 项目构建 | 3.6.0 |

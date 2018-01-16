# Spring Boot Service
### Spring Boot 整合常用框架组成完整可用的快速开发模板。
更快的项目搭建、更高开发和运行效率、更灵活的应对需求变化、更好的可重构和维护性。  

## 集成的功能：
Spring Boot：Spring Boot和Spring基础框架，提供容器、定时任务、异步调用和其他常用功能支持。  
Spring Security：Spring安全框架，可满足复杂场景下的安全需求，已实现基于注解的基本权限体系。  
Spring MVC：Web访问接口与控制器，Restful，全局异常，自动转换时间，静态资源访问，动态国际化支持等。  
QueryDSL：通用高效的类型安全的查询框架，使用API覆盖标准SQL且可跨数据库，具有很高的开发和执行效率。。  
QueryDSL 案例：Maven生成插件配置、独立的参考案例（位于test目录），覆盖绝大多数应用场景，可快速上手。  
Spring Jdbc：Spring Jdbc事务及异常支持、JdbcTemplate作为特殊情况下的后备支持，确保无后顾之忧。  
GlobalIdWorker：全局ID生成器，支持多机多实例运行，趋势递增且尾数均匀，对分表分库非常友好，也可用于唯一命名。  
Spring Cache：基于注解的缓存，默认使用EhCache作为本地缓存，resources目录中提供了Redis参考。  
Protostuff 序列化：Protostuff 序列化可以大幅提高时间及空间性能，适合传输对象，比如存储到Redis等。  
Spring AOP：使用AOP对方法日志进行统一处理，也可用做收集信息、事务处理、权限校验等。  
RSA 和 AES：RSA 非对称可逆加密可用于登录加密等， AES 对称可逆加密可用于内部存储数据。  
Swagger2：扫描Controller及标记注解，生成接口文档，访问路径：/swagger-ui.html。  
Spring Test：Mock测试用例参考，测试驱动开发有助于编写简洁可用和高质量的代码，并加速开发过程。  
MySql数据库：默认使用MySql，驱动和配置参考 pom.xml 和 application.yml 中的数据库连接信息。  
其他支持：OkHttpUtils、RSA和AES加密、JWT、Spring Boot DevTools、Logback配置。

![image](https://github.com/ewingtsai/spring-boot-service/raw/master/screens/home.jpg)
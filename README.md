Spring Boot With Dubbo
===========================
主要介绍如何在Spring Boot中整合Dubbo的使用.

### 注意事项

由于Dubbo的版本并不是特别活跃,加上和Spring Boot的jar包适配等问题,所以请使用最新的Dubbo 3.0.0-SNAPSHOT版本, 目前还有很多工作调整
地址为: https://github.com/linux-china/dubbo3 

### 注册中心(Registry)
目前主要是支持Redis和ZooKeeper这两个注册中心，主要是这两个服务非常普遍，同时由于Docker的流行，启动这两个服务也非常简单。

##### Redis
使用Redis注册中心，需要将在pom.xml中添加对应的redis客户端，代码如下：

```xml
     <dependency>
         <groupId>redis.clients</groupId>
         <artifactId>jedis</artifactId>
     </dependency>
```

对应的配置项为： spring.dubbo.registry = redis://localhost:6379

#####  ZooKeeper
使用ZooKeeper注册中心，需要在pom.xml中添加zookeeper需要的jar包，代码如下：

```xml
     <dependency>
         <groupId>org.apache.curator</groupId>
         <artifactId>curator-framework</artifactId>
         <version>2.11.1</version>
     </dependency>
     <dependency>
         <groupId>com.101tec</groupId>
         <artifactId>zkclient</artifactId>
         <version>0.10</version>
     </dependency>
```

对应的配置项为： spring.dubbo.registry = zookeeper://127.0.0.1:2181

多个zookeeper的配置项为: spring.dubbo.registry = zookeeper://192.168.0.2:2181,192.168.0.3:2181

### 如何测试

* 首先使用IntelliJ IDEA导入项目
* 调用docker-compose启动对应的注册中心: docker-compose up -d
* 启动 SpringBootDubboServerApplication
* 启动 SpringBootDubboClientApplication
* 打开浏览器访问 http://localhost:2080

### 在 Docker 中运行

* 调用docker-compose启动对应的注册中心: docker-compose up -d
* 编译工程 `mvn clean package -Dmaven.test.skip`
* 将 server 打包成 docker image : `docker build -t dubbo-demo:latest spring-boot-dubbo-server`
* 运行 server (注意修改环境变量) : `docker run --rm --name=dubbo-demo -p 20890:20880 -e EXPORT_PORT=20890 -e EXPORT_HOST=YOUR_HOST_HERE -e ZK_HOST=YOUR_HOST_HERE dubbo-demo`
* 启动 SpringBootDubboClientApplication
* 打开浏览器访问 http://localhost:2080

### Spring DevTools注意事项
由于Spring DevTools采用不一样的classloader的机制，所以会导致Dubbo Consumer Bean无法赋值到指定的@Component上，请使用以下规则：

在 src/main/resources/META-INF/spring-devtools.properties 在添加以下代码进行DevTools的classloader屏蔽：
```properties
restart.exclude.target-classes=/target/classes/
```
关于hotspot的模式下，相关Java代码调整后理解生效，可以考虑： http://dcevm.github.io/

如果你的应用是纯Dubbo服务，没有涉及到Web页面，不建议你添加spring-devtools，如果添加了后，
可以通过以下配置项关闭livereload服务，这样可以保证不必要的live reload服务启动。
```properties
spring.devtools.livereload.enabled=false
```

### todo

* DubboConsumerBuilder: 快速构建Dubbo Consumer
* zipkin: https://github.com/jessyZu/dubbo-zipkin-spring-starter

### Spring Boot集成

请参看 [spring-boot-starter-dubbo](https://github.com/linux-china/spring-boot-dubbo/tree/master/spring-boot-starter-dubbo) 



Spring Boot With Dubbo
===========================
主要介绍如何在Spring Boot中整合Dubbo的使用.

### 注意事项

由于Dubbo的版本并不是特别活跃,加上和Spring Boot的jar包适配等问题,所以请使用最新的Dubbo 3.0.0-SNAPSHOT版本, 目前还有很多工作调整
地址为: https://github.com/linux-china/dubbo 

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
* 运行 server : `docker run --rm --name=dubbo-demo -p 20890:20880 -e EXPORT_PORT=20890 -e EXPORT_HOST=10.0.1.126 -e ZK_HOST=10.0.1.126 dubbo-demo`
* 启动 SpringBootDubboClientApplication
* 打开浏览器访问 http://localhost:2080

### todo

* DubboConsumerBuilder: 快速构建Dubbo Consumer

### Spring Boot集成

请参看 [spring-boot-starter-dubbo](https://github.com/linux-china/spring-boot-dubbo/tree/master/spring-boot-starter-dubbo) 



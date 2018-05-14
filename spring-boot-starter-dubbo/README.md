Spring Boot Start Dubbo
=================================
在Spring Boot中整合Dubbo服务发布. 


### 如何发布Dubbo服务?

* 首先在pom.xml中添加对spring-boot-starter-dubbo的引用

       
        <dependency>
            <groupId>org.mvnsearch.spring.boot</groupId>
            <artifactId>spring-boot-starter-dubbo</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
        
* 在application.properties文件中添加相关的配置,如下:


        spring.dubbo.app = dubbo-uic-provider
        spring.dubbo.registry = redis://192.168.99.100:6379
        spring.dubbo.protocol = dubbo
        spring.dubbo.port= 20880
        
* 然后在Spring Boot Application程序中添加 @EnableDubboConfiguration,样例代码如下:

     
     
        @SpringBootApplication
        @EnableDubboConfiguration()
        public class SpringBootDubboServerApplication 

其中org.mvnsearch.uic是Dubbo要扫描的package,根据Dubbo的Service annotation发布服务.

* 在Spring Bean上添加@DubboService Annotation进行Dubbo服务发布,代码如下:


    @Component
    @DubboService(interfaceClass = UicTemplate.class)
    public class UicTemplateImpl implements UicTemplate

### 客户端如何引用Dubbo服务

考虑到客户端的bean引用,目前还是采用spring boot配置和传统的Xml声明方式,也就是dubbo的配置是配置的,dubbo beans是xml引用的方式.

* 首先在pom.xml中添加对spring-boot-starter-dubbo的引用

       
        <dependency>
            <groupId>org.mvnsearch.spring.boot</groupId>
            <artifactId>spring-boot-starter-dubbo</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>

* 在application.properties文件中添加相关的配置,如下:


        spring.dubbo.app = dubbo-uic-consumer
        spring.dubbo.registry = redis://192.168.99.100:6379
        spring.dubbo.protocol = dubbo
     
* 接下来你只需要创建一个ReferenceBean即可,代码如下。 这个也是Spring Boot推荐的做法。

```
  @Bean
    public ReferenceBean<UicTemplate> uicTemplate() {
        return getConsumerBean(UicTemplate.class, properties.getVersion(), properties.getTimeout());
    }
```
* 如果你不想创建上述的ReferenceBean,你也可以在在要引用的Dubbo Service Interface上添加 @DubboConsumer即可,代码如下:
```
   @DubboConsumer
   private UicTemplate uicTemplate;
```
* 最后如果你还想用xml中声明 dubbo consumer beans的配置文件,样例如下:


       <?xml version="1.0" encoding="UTF-8"?>
       
       <beans xmlns="http://www.springframework.org/schema/beans"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
              xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
           http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd">
       
           <dubbo:reference id="uicTemplate" interface="org.mvnsearch.uic.UicTemplate" timeout="1000000" />
       
       </beans>

* 然后在Spring Boot Application中使用@ImportResource引入dubbo consumer xml文件,如下:


       @SpringBootApplication
       @ImportResource("/uic-dubbo-consumer.xml")
       public class SpringBootDubboClientApplication

### 优雅上下线
当我们要重新发布应用时候,我们需要新停掉服务,然后稍等一段时间,等客户端连接全部切换到其他服务器上,这个时候我们才能开始部署服务。这个就是我们称之为优雅下线,
目前可以通过 http://localhost:8080/dubbo/offline

### 其他

* Dubbo Endpint: spring-boot-starter-dubbo提供了dubbo的enpoint,通过该url可以快速了解Dubbo的运行信息
* health indicator: 对远程服务进行echo service调用进行health检查,通过 /health 进行查看

### 注意

* 对应Dubbo服务端应用来说,在重新发布应用时需要调用 /dubbo/offline 首先进行应用下线,然后稍后15秒钟后进行发布.

### 第三方客户端整合

如果你要在第三方客户端,如uic-client,直接整合Dubbo服务对外提供相关的接口,可以使用Spring Boot和Dubbo结合,自动完成auto configuration,
如spring-boot-starter-uic-client代码中,引入spring-boot-starter-dubbo,然后在UicAutoConfiguration中进行Dubbo服务关联,代码如下:

        @Bean
        public ReferenceBean uicTemplate() {
            ReferenceBean<UicTemplate> referenceBean = new ReferenceBean<UicTemplate>();
            String canonicalName = interfaceClazz.getCanonicalName();
            referenceBean.setInterface(canonicalName);
            referenceBean.setId(canonicalName);
            referenceBean.setTimeout(10000);
            return referenceBean;
        }
这里一定要使用类的canonicalName方式初始化,主要是解决Dubbo ClassLoader和Spring Boot的class loader问题.
当然,你可能需要通过自定义properties方式来设置对应的版本号. 这样他人在引入spring-boot-starter-uic-client后就可以直接使用UicTemplate对应的服务啦.



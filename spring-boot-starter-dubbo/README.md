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
        @EnableDubboConfiguration("org.mvnsearch.uic")
        public class SpringBootDubboServerApplication 

其中org.mvnsearch.uic是Dubbo要扫描的package,根据Dubbo的Service annotation发布服务.

* 在Spring Bean上添加@DubboService Annotation进行Dubbo服务发布,代码如下:


    @Component
    @DubboService
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
        
* 然后在xml中声明 dubbo consumer beans的配置文件,样例如下:


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

### 其他

* Dubbo Endpint: spring-boot-starter-dubbo提供了/tair的enpoint,通过该url可以快速了解Tair的运行信息
* health indicator: 对远程服务进行echo service调用进行health检查,通过 /health 进行查看

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


### Todo

* metrics: 客户端和服务端的统计信息,主要是方法调用的统计信息
* connection: 客户端和服务器端的连接明显信息
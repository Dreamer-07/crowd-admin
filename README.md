## 项目介绍

> 该项目为尚筹网项目的后台管理员系统

简介：尚筹网项目是一个众筹平台，主要作用是帮助创业者发布创业项目，向大众募集启动资金的融资平台，整个项目分为**前台会员**(微服务)和**后台管理**(SSM)两个模块

学习资料：https://www.bilibili.com/video/BV1bE411T7oZ

### 架构

![image-20220204213728517](README.assets/image-20220204213728517.png)

## 前端

## 后端

### 基于 Maven 的 MyBatis 的逆向工程

1. 引入依赖

   ```xml
   !-- 依赖 MyBatis 核心包 -->
   <dependencies>
       <dependency>
           <groupId>org.mybatis</groupId>
           <artifactId>mybatis</artifactId>
           <version>3.5.4</version>
       </dependency>
   </dependencies>
   <!-- 控制 Maven 在构建过程中相关配置 -->
   <build>
       <!-- 构建过程中用到的插件 -->
       <plugins>
           <!-- 具体插件，逆向工程的操作是以构建过程中插件形式出现的 -->
           <plugin>
               <groupId>org.mybatis.generator</groupId>
               <artifactId>mybatis-generator-maven-plugin</artifactId>
               <version>1.3.5</version>
               <configuration>
                   <configurationFile>
                       <!-- 这里是配置generatorConfig.xml的路径，这里空着不写表示默认在resources目录下找generatorConfig.xml文件 -->
                   </configurationFile>
                   <verbose>true</verbose>
                   <overwrite>true</overwrite>
               </configuration>
               <dependencies>
                   <dependency>
                       <groupId>mysql</groupId>
                       <artifactId>mysql-connector-java</artifactId>
                       <version>8.0.19</version>
                   </dependency>
               </dependencies>
           </plugin>
       </plugins>
   </build>
   ```

2. 在 `resources` 目录下创建 `generatorConfig.xml`

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE generatorConfiguration
           PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
           "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
   
   <generatorConfiguration>
       <!-- context 是逆向工程的主要配置信息 -->
       <!-- id：配置名称 -->
       <!-- targetRuntime：设置生成的文件的 mybatis 版本 -->
       <context id="default" targetRuntime="MyBatis3">
   
           <!-- optional, 旨在创建class时，对注释进行控制 -->
           <commentGenerator>
               <property name="suppressDate" value="true" />
               <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
               <property name="suppressAllComments" value="true" />
           </commentGenerator>
   
           <!--jdbc的数据库连接，以下为MySQL版本8以上的JDBC驱动-->
           <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                           connectionURL="jdbc:mysql://localhost:3306/crowd_admin?serverTimezone=UTC"
                           userId="root"
                           password="root">
           </jdbcConnection>
   
           <!--非必须，类型处理器，在数据库类型和java类型之间的转换控制-->
           <javaTypeResolver>
               <!-- 默认情况下数据库中的 decimal，bigInt 在 Java 对应是 sql 下的 BigDecimal 类 -->
               <!-- 不是 double 和 long 类型 -->
               <!-- 使用常用的基本类型代替 sql 包下的引用类型 -->
               <property name="forceBigDecimals" value="false" />
           </javaTypeResolver>
   
           <!-- targetPackage：生成的实体类所在的包 -->
           <!-- targetProject：生成的实体类所在的位置 -->
           <javaModelGenerator targetPackage="pers.prover07.crowd.entity" targetProject="src/main/java">
               <!-- 是否允许子包 -->
               <property name="enableSubPackages" value="false" />
               <!-- 是否清理从数据库中查询出的字符串左右两边的空白字符 -->
               <property name="trimStrings" value="true" />
           </javaModelGenerator>
   
           <!-- targetPackage 和 targetProject：生成的 mapper 文件的包和位置 -->
           <sqlMapGenerator targetPackage="mapper" targetProject="src/main/resources">
               <!-- 针对数据库的一个配置，是否把 schema 作为字包名 -->
               <property name="enableSubPackages" value="false" />
           </sqlMapGenerator>
   
           <!-- targetPackage 和 targetProject：生成的 interface 文件的包和位置 -->
           <javaClientGenerator type="XMLMAPPER" targetPackage="pers.prover07.crowd.dao" targetProject="src/main/java">
               <!-- 针对 oracle 数据库的一个配置，是否把 schema 作为字包名 -->
               <property name="enableSubPackages" value="false" />
           </javaClientGenerator>
           <!-- 需要映射的表，以及对应的实体类名 -->
           <table tableName="t_admin" domainObjectName="Admin"/>
       </context>
   </generatorConfiguration>
   ```

3. 通过 Maven 插件启动逆向工程

   ![image-20220205101309316](README.assets/image-20220205101309316.png)

4. 启动成功后刷新以下文件就可以看到生产的文件啦

   ![image-20220205101925342](README.assets/image-20220205101925342.png)

5. 然后将文件复制到对应工程下的目录中即可

### Spring 整合 MyBatis

> 目标

可以将 MyBatis 提供的 Mapper 层作为组件装配到 IOC 容器中，这样就可以直接调用它的方法，享受到框架提供的便利

> 思路

![image-20220205165512005](README.assets/image-20220205165512005.png)

- 通过注册 **SqlSessionFactoryBean** 组件实现 装配数据源(通过读取 `jdbc.properties`) & 指定 Mapper 配置文件位置 & 指定 mybatis-config.xml(mybatis 全局配置文件)
- Mybatis 根据 xml 文件动态生成对应的代理类
- 通过 **MapperScannerConfigurer** 将扫描 Mapper 接口所在包并根据接口类型找到对应的代理类后添加到 IOC 容器中
- 在 Spring 其他组件中通过 `@Autowired` 注入 Mapper 接口代理类

> 操作步骤

1. 在子工程中引入需要使用的依赖

   ```xml
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-orm</artifactId>
   </dependency>
   <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-webmvc</artifactId>
   </dependency>
   <!-- 数据库依赖 -->
   <!-- MySQL 驱动 -->
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
   </dependency>
   <!-- 数据源 -->
   <dependency>
       <groupId>com.alibaba</groupId>
       <artifactId>druid</artifactId>
   </dependency>
   <!-- MyBatis -->
   <dependency>
       <groupId>org.mybatis</groupId>
       <artifactId>mybatis</artifactId>
   </dependency>
   <!-- MyBatis 与 Spring 整合 -->
   <dependency>
       <groupId>org.mybatis</groupId>
       <artifactId>mybatis-spring</artifactId>
   </dependency>
   <!-- MyBatis 分页插件 -->
   <dependency>
       <groupId>com.github.pagehelper</groupId>
       <artifactId>pagehelper</artifactId>
   </dependency>
   ```

2. 准备 `jdbc.properties`

   ```properties
   jdbc.user=root
   jdbc.password=root
   jdbc.url=jdbc:mysql://localhost:3306/crowd_admin/useUnicode=true&characterEncoding=UTF-8
   jdbc.diver=com.mysql.cj.jdbc.Driver
   ```

3. 创建 Spring 配置文件专门配置 Spring 和 Mybatis 整合相关

   `mybatis-config.xml`

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0/EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <!--Spring与MyBatis整合后，MyBatis的配置文件可有可不有-->
   </configuration>
   ```

   `spring-persist-mybatis.xml`

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
   
   </beans>
   ```

4. 在 Spring 的配置文件中加载 `jdbc.properties` 并配置数据源

   ```xml
   <!-- 加载配置文件 -->
   <context:property-placeholder location="classpath:jdbc.properties" />
   
   <!-- 配置数据源 -->
   <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
       <property name="username" value="${jdbc.user}" />
       <property name="password" value="${jdbc.password}" />
       <property name="url" value="${jdbc.url}" />
       <property name="driverClassName" value="${jdbc.diver}" />
   </bean>
   ```

5. 配置 **SqlSessionFactoryBean**

   - 装配数据源

   - 指定 Mapper.xml 配置文件的位置

   - 指定 Mybatis 全局配置文件(可选)

     ```xml
     <!-- 配置 SqlSessionFactoryBean 整合 MyBatis -->
     <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
         <!-- Mybatis 全局配置文件位置 -->
         <property name="configLocation" value="classpath:mybatis/mybatis-config.xml"/>
     
         <!-- 指定 Mapper.xml 配置文件检查 -->
         <property name="mapperLocations" value="classpath:mybatis/mapper/*Mapper.xml" />
     
         <!-- 装配数据源 -->
         <property name="dataSource" ref="dataSource" />
     </bean>
     
     <!-- 配置 MapperScannerConfigurer -->
     <bean id="mapperScannerConfigurer" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
         <!-- 配置 Mapper 接口对应的包位置 -->
         <property name="basePackage" value="pers.prover07.crowd.dao" />
     </bean>
     ```

6. 配置 MapperScannerConfigurer

7. 测试是否可以自动装配 Mapper 接口并通过这个接口操作数据库

### 日志系统

> 介绍

约定/规范/接口:

![image-20220205194727183](README.assets/image-20220205194727183.png)

实现：

![image-20220205194745032](README.assets/image-20220205194745032.png)

> 不同日志系统的集合(以 **Slf4j** 为接口，不同日志系统的实现)

![image-20220205195114757](README.assets/image-20220205195114757.png)

> 使用 logback 替换 Spring 默认的 common-logging

1. 导入依赖

   ```xml
   <dependency>
       <groupId>org.slf4j</groupId>
       <artifactId>slf4j-api</artifactId>
   </dependency>
   <dependency>
       <groupId>ch.qos.logback</groupId>
       <artifactId>logback-classic</artifactId>
   </dependency>
   ```

2. 排除 Spring 依赖中的 `common-logging` 并导入 Sl4fj 转换依赖

   ```xml
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-orm</artifactId>
       <exclusions>
           <exclusion>
               <groupId>commons-logging</groupId>
               <artifactId>commons-logging</artifactId>
           </exclusion>
       </exclusions>
   </dependency>
   ```

   ```xml
   <dependency>
       <groupId>org.slf4j</groupId>
       <artifactId>jcl-over-slf4j</artifactId>
   </dependency>
   ```

3. 编写 logback 配置文件: 在 `src/main/resources` 下创建 **logback.xml** 文件

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <configuration debug="true">
       <!-- 指定日志输出的位置(控制台) -->
       <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
           <encoder>
               <!-- 日志输出的格式 -->
               <!-- 按照顺序分别是： 时间、 日志级别、 线程名称、 打印日志的类、 日志主体
               内容、 换行 -->
               <pattern>[%d{HH:mm:ss.SSS}] [%-5level] [%-8thread] [%logger] [%msg]%n</pattern>
           </encoder>
       </appender>
       <!-- 设置全局日志级别。 日志级别按顺序分别是： DEBUG、 INFO、 WARN、 ERROR -->
       <!-- 指定任何一个日志级别都只打印当前级别和后面级别的日志。 -->
       <root level="INFO">
           <!-- 指定打印日志的 appender， 这里通过"STDOUT"引用了前面配置的 appender -->
           <appender-ref ref="STDOUT" />
       </root>
       <!-- 根据特殊需求指定局部日志级别 -->
       <logger name="org.fall.mapper" level="DEBUG"/>
   </configuration>
   ```

   

## 其他
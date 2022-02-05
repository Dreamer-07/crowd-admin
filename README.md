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

## Spring 整合 MyBatis

> 目标

可以将 MyBatis 提供的 Mapper 层作为组件装配到 IOC 容器中，这样就可以直接调用它的方法，享受到框架提供的便利

> 思路

![image-20220205165512005](README.assets/image-20220205165512005.png)

- 通过注册 **SqlSessionFactoryBean** 组件实现 装配数据源(通过读取 `jdbc.properties`) & 指定 Mapper 配置文件位置 & 指定 mybatis-config.xml(mybatis 全局配置文件)
- Mybatis 根据 xml 文件动态生成对应的代理类
- 通过 **@MapperScannerConfigurer** 将扫描 Mapper 接口所在包并根据接口类型找到对应的代理类后添加到 IOC 容器中
- 在 Spring 其他组件中通过 `@Autowired` 注入 Mapper 接口代理类

> 操作步骤

1. 在子工程中引入需要使用的依赖

   ```xml
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-orm</artifactId>
       <version>${prover07.spring.version}</version>
   </dependency>
   <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-webmvc</artifactId>
       <version>${prover07.spring.version}</version>
   </dependency>
   <!-- 数据库依赖 -->
   <!-- MySQL 驱动 -->
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <version>8.0.20</version>
   </dependency>
   <!-- 数据源 -->
   <dependency>
       <groupId>com.alibaba</groupId>
       <artifactId>druid</artifactId>
       <version>1.0.31</version>
   </dependency>
   <!-- MyBatis -->
   <dependency>
       <groupId>org.mybatis</groupId>
       <artifactId>mybatis</artifactId>
       <version>3.2.8</version>
   </dependency>
   <!-- MyBatis 与 Spring 整合 -->
   <dependency>
       <groupId>org.mybatis</groupId>
       <artifactId>mybatis-spring</artifactId>
       <version>1.2.2</version>
   </dependency>
   <!-- MyBatis 分页插件 -->
   <dependency>
       <groupId>com.github.pagehelper</groupId>
       <artifactId>pagehelper</artifactId>
       <version>4.0.0</version>
   </dependency>
   ```

   

2. 准备 `jdbc.properties`

3. 创建 Spring 配置文件专门配置 Spring 和 Mybatis 整合相关

4. 在 Spring 的配置文件中加载 `jdbc.properties`

5. 配置数据源

6. 配置从数据源中获取数据库连接

7. 配置 **SqlSessionFactoryBean**

   - 装配数据源
   - 指定 Mapper.xml 配置文件的位置
   - 指定 Mybatis 全局配置文件(可选)

8. 配置 MapperScannerConfigurer

9. 测试是否可以自动装配 Mapper 接口并通过这个接口操作数据库

## 其他
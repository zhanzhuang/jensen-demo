# Spring

## Spring概述
### 什么是spring
### spring两大核心
### spring的发展历程和优势
### spring体系结构
## 程序的耦合以及解耦
**程序中的耦合**
```java
package spring.jdbc;

import java.sql.*;

/**
 * 程序的耦合
 * 耦合:
 *      程序之间的依赖关系,包括类之间的依赖关系/方法之间的依赖关系
 * 解耦:
 *      降低程序之间的依赖关系
 * 实际开发:
 *      应该做到编译器不依赖，程序运行时才依赖
 * 解耦思路：
 *      1.使用反射创建对象，避免使用new关键字
 *      2.通过读取配置文件来获取要创建的对象的全限定类名
 */
public class JdbcDemo1 {
    public static void main(String[] args) throws Exception {
        // 1.注册驱动
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver()); // 编译期依赖了
//        Class.forName("com.mysql.jdbc.Driver"); // 编译器没有依赖，只是字符串
        // 2.获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel?serverTimezone=UTC", "root", "123456");
        // 3.获取操作数据库的预处理对象
        PreparedStatement pstm = conn.prepareStatement("select * from account");
        // 4.执行SQL 得到结果集
        ResultSet rs = pstm.executeQuery();
        // 5.遍历结果集
        while (rs.next()) {
            System.out.println(rs.getString( "NAME"));
        }
        // 6.释放资源
        rs.close();
        pstm.close();
        conn.close();
    }
}
```
### 曾经案例中问题
### 工厂模式解耦
## IOC概念和spring中的IOC
### IOC概念
**控制反转(inversion of control)把创建对象的权力交给框架,是框架的主要特征**
**降低计算机程序之间的耦合(降低程序之间的依赖关系)**
```java
// 1.主动创建对象，是自己独立控制 2.程序有耦合
private IAccountDao accountDao = new AccountDaoImpl();
// 1.创建对象交给BeanFactory，由BeanFactory通过类的全限定类名控制 2.降低了耦合
private IAccountDao accountDao = (IAccountDao)BeanFactory.getBean("accountDao");
```
### spring中基于XML的IOC环境搭建
**读取配置文件，创建对象并放入map中是spring管理的**
```java
package spring.service;

public interface IAccountService {
    void saveAccount();
}
```
```java
package spring.service.impl;

import spring.dao.IAccountDao;
import spring.dao.impl.IAccountDaoImpl;
import spring.service.IAccountService;

public class AccountServiceImpl implements IAccountService {
    private IAccountDao accountDao = new IAccountDaoImpl();

    @Override
    public void saveAccount() {
        accountDao.saveAccount();
    }
}
```
```java
package spring.dao;

public interface IAccountDao {
    void saveAccount();
}
```
```java
package spring.dao.impl;

import spring.dao.IAccountDao;

public class IAccountDaoImpl implements IAccountDao {
    @Override
    public void saveAccount() {
        System.out.println("save...");
    }
}
```
```java
package spring.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.dao.IAccountDao;
import spring.service.IAccountService;

public class Client {
    /**
     * 获取spring的IOC核心容器，并根据id获取对象
     *
     * @param args
     */
    public static void main(String[] args) {
        // 1.获取核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        // 根据id获取bean对象
        IAccountService as = (IAccountService) ac.getBean("accountService");
        IAccountDao adao = ac.getBean("accountDao", IAccountDao.class);

        System.out.println(as);
        System.out.println(adao);
    }
}
```
#### ApplicationContext的三个实现类
+ **ClassPathXmlApplicationContext**
    + 它可以加载类路径下的配置文件,要求配置文件在类路径下
+ **FileSystemXmlApplicationContext**
    + 它可以加载磁盘任意路径下的配置文件(必须有访问权限)
+ **AnnotationConfigApplicationContext**
    + 它是用于读取注解创建的容器的
#### ApplicationContext和BeanFactory的区别
**ApplicationContext**(单例对象适用)
+ 它在构造核心容器时,创建对象采取的策略是采用立即加载的方式。也就是说，只要读取完配置文件马上就创建配置文件中的对象
    ```java
    public static void main(String[] args) {
       // 1.获取核心容器对象
       ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
       // 根据id获取bean对象
       IAccountService as = (IAccountService) ac.getBean("accountService");
       IAccountDao adao = ac.getBean("accountDao", IAccountDao.class);
       System.out.println(as);
       System.out.println(adao);
    }
    ```
**BeanFactory**(多例对象适用)
+ 它在构造核心容器时,创建对象采取的策略是采用延迟加载的方式。什么时候根据id获取对象了,什么时候才真正的创建对象    
    ```java
    public static void main(String[] args) {
       Resource resource = new ClassPathResource("bean.xml");
       BeanFactory factory = new XmlBeanFactory(resource);
       IAccountService as = (IAccountService) factory.getBean("accountService");
       System.out.println(as);
    }
    ```
## 依赖注入(Dependency Injection)

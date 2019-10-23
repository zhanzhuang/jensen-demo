# Spring

## Spring概述
### 什么是spring
### spring两大核心
### spring的发展历程和优势
### spring体系结构

## 程序的耦合以及解耦
###曾经案例中问题
### 工厂模式解耦
```java
/**
 * 一个创建Bean的工厂(创建service和dao对象)
 * Bean：在计算机语言中，由可重用组件的含义
 * JavaBean > 实体类
 * 1.需要一个配置文件来配置我们的service和dao对象
 * 配置内容：唯一标识(全限定类名) key = value
 * 2.读取配置文件中配置的内容，反射创建对象
 * <p>
 * 我们的配置文件可以是xml也可以是properties
 */
public class BeanFactory {
    // 定义一个Properties对象
    private static Properties props;
    // 定义一个Map,用于存放我们要创建的对象。我们把它称之为容器
    private static Map<String,Object> beans;

    static {
        try {
            props = new Properties();
            InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
            props.load(in);
            // 实例化容器
            beans = new HashMap<>();
            // 取出配置文件中所有的key
            Enumeration keys = props.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement().toString();
                String beanPath = props.getProperty(key);
                Object value = Class.forName(beanPath).newInstance();
                beans.put(key, value);
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError("init properties failed");
        }
    }

    /**
     * 根据Bean的名称获取bean对象(单例)
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        return beans.get(beanName);
    }

    /**
     * 根据Bean的名称获取bean对象(每次创建一个新的实例)
     *
     * @param beanName
     * @return
     */
/*    public static Object getBean(String beanName) {
        Object bean = null;
        try {
            String beanPath = props.getProperty(beanName);
            bean = Class.forName(beanPath).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }*/
}
```
resource/bean.properties
```properties
accountService=spring.service.impl.AccountServiceImpl
accountDao=spring.dao.AccountDaoImpl
```
## IOC概念和spring中的IOC
###IOC概念
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

# Spring目录
+ **一 程序的耦合以及解耦**
+ **二 IOC概念和spring中的IOC**
    + **IOC概念**
    + **spring中基于XML的IOC环境搭建**
    + **ApplicationContext的三个实现类**
    + **ApplicationContext和BeanFactory的区别**
    + **spring中bean细节之三种创建bean对象的方式**
        + **1.使用默认构造函数创建**
        + **2.使用普通工厂中的方法创建对象(使用某个类中的方法创建对象)**
        + **3.使用工厂中的静态方法创建对象(使用某个类中的静态方法创建对象并存入spring容器)**
    + **spring中bean细节之作用范围**
    + **spring中bean细节之生命周期**
    + **依赖注入(Dependency Injection)**
        + **构造器注入**
        + **SET方法注入**
        + **SET方法注入集合数据**
        experimental did merge
+ **一 ElasticSearch简介**
+ **一 ElasticSearch简介**
+ **一 ElasticSearch简介**
+ **一 ElasticSearch简介**
+ **一 ElasticSearch简介**
+ **一 ElasticSearch简介**
+ **一 ElasticSearch简介**
## 一 程序的耦合以及解耦
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
## 二 IOC概念和spring中的IOC
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
**读取配置文件,创建对象并放入map中是spring管理的,降低了耦合**
+ **依赖**
    ```xml
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.0.2.RELEASE</version>
    </dependency>
    ```
+ **bean.xml**
    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">
        <!--把对象的创建交给spring管理-->
        <bean id="accountService" class="spring.service.impl.AccountServiceImpl"></bean>
        <bean id="accountDao" class="spring.dao.impl.IAccountDaoImpl"></bean>
    </beans>
    ```
+ **代码**  
    ```java
    public class Client {
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
    ```java
    public interface IAccountService {
        void saveAccount();
    }
    ```
    ```java
    public class AccountServiceImpl implements IAccountService {
        private IAccountDao accountDao = new IAccountDaoImpl();
    
        @Override
        public void saveAccount() {
            accountDao.saveAccount();
        }
    }
    ```
    ```java
    public interface IAccountDao {
        void saveAccount();
    }
    ```
    ```java
    public class IAccountDaoImpl implements IAccountDao {
        @Override
        public void saveAccount() {
            System.out.println("save...");
        }
    }
    ```
### ApplicationContext的三个实现类
+ **ClassPathXmlApplicationContext**
    + 它可以加载类路径下的配置文件,要求配置文件在类路径下
+ **FileSystemXmlApplicationContext**
    + 它可以加载磁盘任意路径下的配置文件(必须有访问权限)
+ **AnnotationConfigApplicationContext**
    + 它是用于读取注解创建的容器的
### ApplicationContext和BeanFactory的区别
**ApplicationContext**(单例对象适用)
+ 它在构造核心容器时,创建对象采取的策略是采用立即加载的方式。也就是说，只要读取完配置文件马上就创建配置文件中的对象
    ```java
    public static void main(String[] args) {
       // 1.获取核心容器对象
       // 读取完配置文件创建对象
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
       // getBean时创建对象
       IAccountService as = (IAccountService) factory.getBean("accountService");
       System.out.println(as);
    }
    ```
### spring中bean细节之三种创建bean对象的方式
#### 1.使用默认构造函数创建
**在spring的配置文件中使用bean标签，配上id和class属性之后，且没有其他属性和标签**
**采用的是默认构造器创建bean对象，如果类中没有默认构造器，创建对象会报异常**
```java
public interface IAccountService {
    void saveAccount();
}
```
```java
public class AccountServiceImpl implements IAccountService {
    public AccountServiceImpl(String name) {
        System.out.println("accountServiceImpl initialized with param");
    }
//    public AccountServiceImpl() {
//        System.out.println("accountServiceImpl initialized");
//    }
    public void saveAccount() {
        System.out.println("saveAccount executed ");
    }
}
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"></bean>
</beans>
```
```java
public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = (IAccountService) ac.getBean("accountService");
        as.saveAccount();
    }
}
```
#### 2.使用普通工厂中的方法创建对象(使用某个类中的方法创建对象)
```java
public interface IAccountService {
    void saveAccount();
}
```
```java
public class AccountServiceImpl implements IAccountService {

    public AccountServiceImpl() {
        System.out.println("accountServiceImpl initialized");
    }
    
    public void saveAccount() {
        System.out.println("saveAccount executed ");
    }
}
```
```java
/**
 * 模拟一个工厂类(该类可能是存在于jar包中的,我们无法通过修改源码的方式提供默认构造器)
 */
public class InstanceFactory {
    public IAccountService getAccountService() {
        return new AccountServiceImpl();
    }
}
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--第二种方式:使用普通工厂中的方法创建对象(使用某个类中的方法创建对象)-->
    <bean id="instanceFactory" class="com.itheima.factory.InstanceFactory"></bean>
    <bean id="accountService" factory-bean="instanceFactory" factory-method="getAccountService"></bean>
</beans>
```
```java
public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = (IAccountService) ac.getBean("accountService");
        as.saveAccount();
    }
}
```
#### 3.使用工厂中的静态方法创建对象(使用某个类中的静态方法创建对象并存入spring容器)
```java
public interface IAccountService {
    void saveAccount();
}
```
```java
public class AccountServiceImpl implements IAccountService {

    public AccountServiceImpl() {
        System.out.println("accountServiceImpl initialized");
    }
    
    public void saveAccount() {
        System.out.println("saveAccount executed ");
    }
}
```
```java
/**
 * 模拟一个工厂类(该类可能是存在于jar包中的,我们无法通过修改源码的方式提供默认构造器)
 */
public class StaticFactory {
    public static IAccountService getAccountService() {
        return new AccountServiceImpl();
    }
}
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="accountService" class="com.itheima.factory.StaticFactory" factory-method="getAccountService"></bean>
</beans>
```
```java
public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = (IAccountService) ac.getBean("accountService");
        as.saveAccount();
    }
}
```
### spring中bean细节之作用范围
+ **singleton:单例(常用),是默认值**
+ **prototype:多例(常用)**
+ **request:作用于web应用的请求范围**
+ **session:作用于web应用的会话范围**
+ **global-session:作用于集群会话范围,如果不是集群环境,它就是session**
```java
public interface IAccountService {
    void saveAccount();
}
```
```java
public class AccountServiceImpl implements IAccountService {

    public AccountServiceImpl() {
        System.out.println("accountServiceImpl initialized");
    }
    
    public void saveAccount() {
        System.out.println("saveAccount executed ");
    }
}
```
```java
/**
 * 模拟一个工厂类(该类可能是存在于jar包中的,我们无法通过修改源码的方式提供默认构造器)
 */
public class StaticFactory {
    public static IAccountService getAccountService() {
        return new AccountServiceImpl();
    }
}
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="accountService" class="com.itheima.factory.StaticFactory" factory-method="getAccountService" scope="prototype"></bean>
</beans>
```
```java
public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as1 = (IAccountService) ac.getBean("accountService");
        IAccountService as2 = (IAccountService) ac.getBean("accountService");
//        as.saveAccount();
        System.out.println(as1 == as2); // this is false,because scope is prototype
    }
}
```
### spring中bean细节之生命周期
+ **单例对象**
    + 出生：容器创建时对象出生
    + 活着：容器还在对象就在
    + 死亡：容器销毁对象死亡
    + 总结：单例对象生命周期和容器象通
+ **多例对象**
    + 出生：使用对象时spring自动创建
    + 活着：对象在使用
    + 死亡：当对象长时间不用且没有被其他对象引用，由java的垃圾回收器回收      
```java
public interface IAccountService {
    void saveAccount();
}
```
```java
public class AccountServiceImpl implements IAccountService {
    public AccountServiceImpl() {
        System.out.println("accountServiceImpl initialized");
    }
    public void saveAccount() {
        System.out.println("saveAccount executed");
    }
    public void init() {
        System.out.println("init method");
    }
    public void destory() {
        System.out.println("destory method");
    }
}
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl" scope="prototype"
            init-method="init" destroy-method="destory"></bean></beans>
```
```java
public class Client {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = (IAccountService) ac.getBean("accountService");
        as.saveAccount();
        // main方法结束后当前应用占用的内存全部释放,容器内存也释放,所以没有执destory方法
        // 手动关闭容器方法
        ac.close();        
    }
}
```

## 依赖注入(Dependency Injection)
+ **IOC作用**
    + **降低程序之间的耦合(依赖关系)**
+ **依赖注入的数据类型**
    + 1.基本类型和String
    + 2.其他bean类型(在配置文件中或者注解配置过的bean)    
    + 3.复杂类型/集合类型
+ **注入方式**
    + 1.构造器注入
    + 2.set方法注入
    + 3.注解注入
### 构造器注入
+ **在bean标签内部的constructor-arg**
+ **标签中的属性**
    + type:用于指定要注入的数据类型，该数据类型也是构造器中某个或某些参数的类型
    + index:用于指定要注入的数据给构造器中指定索引位置的参数复制。索引的位置是从0开始
    + name:用于指定给构造器中指定名称的参数赋值(常用)
    + value:用于提供基本类型和string类型的数据
    + ref:用于指定其他的bean类型数据。它指的就是在spring的IOC核心容器中出现过的bean对象
+ **优势**
    + 获取bean对象时，诸如数据时必须的操作，否则对象无法创建
+ **弊端**
    + 改变了bean对象的实例化方式，使我们在创建对象时，如果用不到这些数据，也必须提供
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.0.2.RELEASE</version>
</dependency>
```    
```java
public interface IAccountService {
    void saveAccount();
}
```
```java
public class AccountServiceImpl implements IAccountService {
    // 如果是经常变化的数据并不适用这种注入
    private String name;
    private Integer age;
    private Date birthday;

    public AccountServiceImpl(String name, Integer age, Date birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public void saveAccount() {
        System.out.println("saveAccount executed..." + name + age + birthday);
    }
}
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
        <constructor-arg name="name" value="jack"/>
        <constructor-arg name="age" value="18"/>
        <constructor-arg name="birthday" ref="now"/>
    </bean>

    <bean id="now" class="java.util.Date"></bean>

</beans>
```
```JAVA
public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = (IAccountService) ac.getBean("accountService");
        as.saveAccount();
    }
}
```
### SET方法注入
+ **在bean标签内部的property**
+ **标签中的属性**
    + name:用于指定给构造器中指定名称的参数赋值(常用)
    + value:用于提供基本类型和string类型的数据
    + ref:用于指定其他的bean类型数据。它指的就是在spring的IOC核心容器中出现过的bean对象
+ **优势**
    + 创建对象时没有明确的限制，可以直接使用默认构造函数
+ **弊端**
    + 如果有某个成员必须有值，则获取对象时set方法没有执行
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.0.2.RELEASE</version>
</dependency>
```    
```java
public interface IAccountService {
    void saveAccount();
}
```
```java
public class AccountServiceImpl2 implements IAccountService {
    private String name;
    private Integer age;
    private Date birthday;
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public void saveAccount() {
        System.out.println("saveAccount executed..." + name + age + birthday);
    }
}
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="accountService2" class="com.itheima.service.impl.AccountServiceImpl2">
        <property name="name" value="Test"></property>
        <property name="age" value="21"></property>
        <property name="birthday" ref="now"></property>
    </bean>

    <bean id="now" class="java.util.Date"></bean>

</beans>
```
```JAVA
public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = (IAccountService) ac.getBean("accountService2");
        as.saveAccount();
    }
}
```
### SET方法注入集合数据
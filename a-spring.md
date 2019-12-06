# Spring目录
+ **一 程序的耦合以及解耦**
+ **二 IOC概念和spring中的IOC**
    + **IOC概念**
    + **Spring中基于XML的IOC环境搭建**
    + **ApplicationContext的三个实现类**
    + **ApplicationContext和BeanFactory的区别**
    + **Spring中bean细节之三种创建bean对象的方式**
        + **1.使用默认构造函数创建**
        + **2.使用普通工厂中的方法创建对象(使用某个类中的方法创建对象)**
        + **3.使用工厂中的静态方法创建对象(使用某个类中的静态方法创建对象并存入spring容器)**
    + **Spring中bean细节之作用范围**
    + **Spring中bean细节之生命周期**
    + **依赖注入(Dependency Injection)**
        + **构造器注入**
        + **SET方法注入**
        + **SET方法注入集合数据**
+ **三 Spring基于注解的IOC以及IOC的案例**
    + **Spring中IOC的常用注解按照作用分类**
        + **用于创建对象的**
            + **@Component**
            + **由@Component衍生出来的注解 @Controller @Service @Repository**
        + **用于注入数据的**
            + **@Autowired @Qualifier @Resource @Value**
        + **用于改变作用范围的**
            + **@Scope**
        + **和生命周期相关的(了解)**
            + **@PostConstruct @PreDestroy**
    + **使用xml方法和注解方式实现单表的CRUD操作案例**
        + **基于XML方式的IOC案例**
        + **基于注解的方式并整合Junit案例**
            + **Spring中的新注解**
                + **@Configuration @ComponentScan @Bean @Import @PropertySource**
+ **四 动态代理**   
    + **基于接口的动态代理** 
    + **基于子类的动态代理(cglib动态代理)** 
+ **五 AOP**
    + **什么是AOP**
    + **AOP的作用和优势**
    + **AOP的相关术语**
        + **Joinpoint Pointcut Advice Introduction Target Weaving Proxy Aspect**
    + **基于XML的AOP配置**
    + **基于注解的AOP配置**
+ **六 Spring中的事务**
    + **隔离级别**
    + **传播行为**
    
+ **一 ElasticSearch简介**
+ **一 ElasticSearch简介**
+ **一 ElasticSearch简介**
+ **一 ElasticSearch简介**
+ **一 ElasticSearch简介**
## 一 程序的耦合以及解耦
```java
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
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver()); // 编译期依赖了(mysql8更新了驱动,pom中驱动版本必须为8以上)
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
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.0.2.RELEASE</version>
</dependency>
```
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
#### ApplicationContext(单例对象适用)
**它在构造核心容器时,创建对象采取的策略是采用立即加载的方式。也就是说，只要读取完配置文件马上就创建配置文件中的对象**
```java
public static void main(String[] args) {
   // 读取完配置文件创建对象
   ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
   // 根据id获取bean对象
   IAccountService as = (IAccountService) ac.getBean("accountService");
   IAccountDao adao = ac.getBean("accountDao", IAccountDao.class);
   System.out.println(as);
   System.out.println(adao);
}
```
#### BeanFactory(多例对象适用)
**它在构造核心容器时,创建对象采取的策略是采用延迟加载的方式。什么时候根据id获取对象了,什么时候才真正的创建对象**    
```java
public static void main(String[] args) {
   Resource resource = new ClassPathResource("bean.xml");
   BeanFactory factory = new XmlBeanFactory(resource);
   // 根据id获取bean对象时创建对象
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
        // main方法结束后当前应用占用的内存全部释放,容器内存也释放,所以没有执行destory方法
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
+ **集合类型的数据有两种**
    + List类型
        + array list set
    + Map类型
        + map props
```java
public interface IAccountService {
    void saveAccount();
}
```
```java
public class AccountServiceImpl3 implements IAccountService {
    private String[] myStrs;
    private List<String> myList;
    private Set<String> mySet;
    private Map<String,String> myMap;
    private Properties myProps;

    public void setMyStrs(String[] myStrs) {
        this.myStrs = myStrs;
    }

    public void setMyList(List<String> myList) {
        this.myList = myList;
    }

    public void setMySet(Set<String> mySet) {
        this.mySet = mySet;
    }

    public void setMyMap(Map<String, String> myMap) {
        this.myMap = myMap;
    }

    public void setMyProps(Properties myProps) {
        this.myProps = myProps;
    }

    public void saveAccount() {
        System.out.println(Arrays.toString(myStrs));
        System.out.println(myList);
        System.out.println(mySet);
        System.out.println(myMap);
        System.out.println(myProps);
    }
}
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="accountService3" class="com.itheima.service.impl.AccountServiceImpl3">
        <property name="myStrs">
            <array>
                <value>AAA</value>
                <value>BBB</value>
                <value>CCC</value>
            </array>
        </property>
        <property name="myList">
            <list>
                <value>AAA</value>
                <value>BBB</value>
                <value>CCC</value>
            </list>
        </property>
        <property name="mySet">
            <set>
                <value>AAA</value>
                <value>BBB</value>
                <value>CCC</value>
            </set>
        </property>
        <property name="myMap">
            <map>
                <entry key="testA" value="aaa"></entry>
                <entry key="testB">
                    <value>bbb</value>
                </entry>
            </map>
        </property>
        <property name="myProps">
            <props>
                <prop key="testC">ccc</prop>
                <prop key="testD">ddd</prop>
            </props>
        </property>
    </bean>

</beans>
```
```java
public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = (IAccountService) ac.getBean("accountService3");
        as.saveAccount(); // 会打印五种类型的数据
    }
}
```
## 三 Spring基于注解的IOC以及IOC的案例
### Spring中IOC的常用注解按照作用分类
#### 用于创建对象的 @Component @Controller @Service @Repository
##### @Component
+ **作用**
    + 用于创建对象,并存入Spring容器中(相当于在XML配置文件中编写一个bean标签)
+ **属性**
    + `value`:用于指定bean的id。当不写时,默认是当前类名且首字母小写
```java
public interface IAccountService {
    void saveAccount();
}
```
```java
@Component
public class AccountDaoImpl implements IAccountDao {
    public AccountDaoImpl() {
        System.out.println("AccountDaoImpl对象创建了");
    }
    public void saveAccount() {

    }
}
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 告诉Spring在创建容器时要扫描的包，配置所需要的标签不是在beans约束中，而是一个名称
    为context名称空间和约束中-->
    <context:component-scan base-package="com.itheima"></context:component-scan>
    
</beans>
```       
```java
public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = (IAccountService) ac.getBean("accountServiceImpl");
        System.out.println(as); // 打印对象地址,证明对象已经被创建
    }
}
``` 
##### 由@Component衍生出来的注解 @Controller @Service @Repository
+ @Controller @Service @Repository
+ 他们三个注解的作用和属性与Component是一模一样的
+ 是Spring框架为我们明确三层结构而产生的注解
+ 上面的例子将Component换成他们三个会得到一样的结果!!!
#### 用于注入数据的 @Autowired @Qualifier @Resource @Value
##### @Autowired
+ **作用**
    + 自动按照类型注入。只要IOC容器中有唯一的一个bean对象和要注入的变量类型匹配,即可注入成功
+ **出现位置**
    + 可以是变量上,也可以是方法上
+ **细节**
    + 在使用注解注入时,set方法就不是必须的了
+ **下面的例子是有两个同一类型的bean对象**
    + IOC容器中有两个IAccountDao类型的对象,分别是accountDao1/accountDao2    
    + IAccountDao accountDao2则表示使用accountDao2这个变量名去匹配IOC容器中的bean
```XML
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 告诉Spring在创建容器时要扫描的包，配置所需要的标签不是在beans约束中，而是一个名称
    为context名称空间和约束中-->
    <context:component-scan base-package="com.itheima"></context:component-scan>

</beans>
```    
```java
public interface IAccountService {
    void saveAccount();
}
```
```java
@Component
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private IAccountDao accountDao2; // 此处根据accountDao2这个对象名进行匹配

    public void saveAccount() {
        accountDao2.saveAccount();
    }
}
```
```java
public interface IAccountDao {
    void saveAccount();
}
```    
```java
@Repository("accountDao1")
public class AccountDaoImpl1 implements IAccountDao {
    public void saveAccount() {
        System.out.println("dao保存了账户111");
    }
}
```
```java
@Repository("accountDao2")
public class AccountDaoImpl2 implements IAccountDao {
    public void saveAccount() {
        System.out.println("dao保存了账户222");
    }
}
```
```java
public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = (IAccountService) ac.getBean("accountServiceImpl");
        as.saveAccount(); // 输出dao保存了账户222
    }
}
```
##### @Qualifier
+ **作用**
    + 给类成员注入需要联合@Autowired使用.当@Autowired修饰的变量名无法匹配IOC容器中的多个bean类型时,通过此注解指定bean
    + 给方法参数注入时可以
+ **属性**    
    + `value`:用于指定注入bean的ID
```java
@Autowired // 1.IOC容器中IAccountDao类型有两个bean分别是 accountDao1 accountDao2
@Qualifier("accountDao2") // 3.指定注入的bean是 accountDao2
private IAccountDao accountDao; // 2.accountDao在IOC容器中没有找到匹配的bean
```
##### @Resource
+ **作用**
    + 直接按照bean的id注入,可以独立使用.相当于 @Autowired 与 @Qualifier 组合使用
+ **属性**
    + `name`:用于指定bean的ID
```java
//    @Autowired
//    @Qualifier("accountDao2")
@Resource(name = "accountDao2") // 2.name属性直接匹配到accountDao2代表的bean并注入
private IAccountDao accountDao; // 1.IOC容器中有两个IAccountDao类型的bean,分别是accountDao1 accountDao2。使用@Autowired 与accountDao 无法匹配到具体的某个bean
```
##### @Autowired @Qualifier @Resource 注意事项
+ **都只能注入其他bean类型的数据，而基本类型和String类型无法使用上述注解实现**
+ **集合类型的注入只能通过XML来实现**
##### @Value
+ **作用**
    + 用于注入基本类型和String类型的数据
+ **属性**
    + `value`:用于指定数据的值
#### 用于改变作用范围的
##### @Scope
+ **作用**
    + 用于指定bean的作用范围
+ **属性**
    + `value`:用指定范围的取值。常用取值:singleton(单例),prototype(多例)
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 告诉Spring在创建容器时要扫描的包，配置所需要的标签不是在beans约束中，而是一个名称
    为context名称空间和约束中-->
    <context:component-scan base-package="com.itheima"></context:component-scan>
</beans>
```    
```java
public interface IAccountService {
    void saveAccount();
}
```
```java
@Component
@Scope("prototype") // 默认是singleton
public class AccountServiceImpl implements IAccountService {
    public void saveAccount() {
        System.out.println("保存账户1");
    }
}
```
```java
public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as1 = (IAccountService) ac.getBean("accountServiceImpl");
        IAccountService as2 = (IAccountService) ac.getBean("accountServiceImpl");
        System.out.println(as1 == as2); // false
    }
}
```
#### 和生命周期相关的(了解)
##### @PostConstruct
+ **作用**
    + 用于指定初始化方法
##### @PreDestroy
+ **作用**
    + 用于指定销毁方法
```java
@Component
public class AccountServiceImpl implements IAccountService {
    public void saveAccount() {
        System.out.println("保存账户1");
    }
    @PostConstruct
    public void myInit() {
        System.out.println("myInit");
    }
    @PreDestroy
    public void myDestroy() {
        System.out.println("myDestroy");
    }
}
```    
```java
public class Client {
    public static void main(String[] args) {
//        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        // 不能子类对象指向父类实例,要用自己的对象调用close()方法
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        IAccountService as = (IAccountService) ac.getBean("accountServiceImpl");
        as.saveAccount();
        ac.close();
    }
}
```
### 使用xml方法和注解方式实现单表的CRUD操作案例
#### 基于XML方式的IOC案例
```sql
create table account(
	id int primary key auto_increment,
	name varchar(40),
	money float
)character set utf8 collate utf8_general_ci;

insert into account(name,money) values('aaa',1000);
insert into account(name,money) values('bbb',1000);
insert into account(name,money) values('ccc',1000);
```
```java
public class Account {
    private Integer id;
    private String name;
    private Float money;
    // get and set ...
    // toString ...
}
```
```java
public interface IAccountService {
    List<Account> findAllAccount();
    Account findAccountById(Integer id);
    void saveAccount(Account account);
    void updateAccount(Account account);
    void deleteAccount(Integer accountId);
}
```
```java
public class AccountServiceImpl implements IAccountService {
    private IAccountDao accountdao;
    public void setAccountdao(IAccountDao accountdao) {
        this.accountdao = accountdao;
    }
    public List<Account> findAllAccount() {
        return accountdao.findAllAccount();
    }
    public Account findAccountById(Integer id) {
        return accountdao.findAccountById(id);
    }
    public void saveAccount(Account account) {
        accountdao.saveAccount(account);
    }
    public void updateAccount(Account account) {
        accountdao.updateAccount(account);
    }
    public void deleteAccount(Integer accountId) {
        accountdao.deleteAccount(accountId);
    }
}
```
```java
public interface IAccountDao {
    List<Account> findAllAccount();
    Account findAccountById(Integer id);
    void saveAccount(Account account);
    void updateAccount(Account account);
    void deleteAccount(Integer accountId);
}
```
```java
public class AccountDaoImpl implements IAccountDao {

    private QueryRunner runner;

    public void setRunner(QueryRunner runner) {
        this.runner = runner;
    }
    public List<Account> findAllAccount() {
        try {
            return runner.query("select * from account", new BeanListHandler<Account>(Account.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Account findAccountById(Integer id) {
        try {
            return runner.query("select * from account where id = ? ", new BeanHandler<Account>(Account.class), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void saveAccount(Account account) {
        try {
            runner.update("insert into account(name,money) values(? , ?)", account.getName(), account.getMoney());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void updateAccount(Account account) {
        try {
            runner.update("update account set name = ?, money = ? where id = ?", account.getName(), account.getMoney(), account.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteAccount(Integer accountId) {
        try {
            runner.update("delete from account where id = ?", accountId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 配置service -->
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
        <!-- 注入dao对象 -->
        <property name="accountdao" ref="accountDao"/>
    </bean>
    <!-- 配置dao -->
    <bean id="accountDao" class="com.itheima.dao.impl.AccountDaoImpl">
        <!-- 注入QueryRunner -->
        <property name="runner" ref="runner"/>
    </bean>
    <!--配置QueryRunner-->
    <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype">
        <!-- 注入数据源 -->
        <constructor-arg name="ds" ref="dataSource"/>
    </bean>
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!-- 连接数据库的必备信息 -->
        <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/travel?serverTimezone=UTC"/>
        <property name="user" value="root"/>
        <property name="password" value="123456"/>
    </bean>

</beans>
```
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.0.2.RELEASE</version>
</dependency>
<dependency>
    <groupId>commons-dbutils</groupId>
    <artifactId>commons-dbutils</artifactId>
    <version>1.4</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.17</version>
</dependency>
<dependency>
    <groupId>c3p0</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.1.2</version>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.10</version>
</dependency>
```
```java
/**
 * 使用Junit单元测试,测试我们的配置
 */
public class AccountServiceTest {
    @Test
    public void testFindAll() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = ac.getBean("accountService", IAccountService.class);
        List<Account> allAccount = as.findAllAccount();
        for (Account account : allAccount) {
            System.out.println(account);
        }
    }
    @Test
    public void testFindOne() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = ac.getBean("accountService", IAccountService.class);
        Account a = as.findAccountById(1);
        System.out.println(a);
    }
    @Test
    public void testSave() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = ac.getBean("accountService", IAccountService.class);
        Account account = new Account();
        account.setName("jack");
        account.setMoney(2000f);
        as.saveAccount(account);
    }
    @Test
    public void testUpdate() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = ac.getBean("accountService", IAccountService.class);
        Account account = as.findAccountById(4);
        account.setMoney(123f);
        as.updateAccount(account);
    }
    @Test
    public void testDelete() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = ac.getBean("accountService", IAccountService.class);
        as.deleteAccount(4);
    }
}
```
#### 基于注解的方式并整合Junit案例
##### Spring中的新注解
###### @Configuration
+ **作用**
    + 用于指定当前类是一个配置类
###### @ComponentScan
+ **作用**
    + 指定Spring在创建容器时需要扫描的包
###### @Bean
+ **作用**        
    + 把当前方法的返回值作为bean对象存入IOC容器中
+ **属性**
    + `name`:用于指定bean的ID。当不写时,默认值是当前方法名
+ **细节**        
    + 当我们使用注解配置方法时,如果方法有参数。Spring框架会去容器中查找有没有可用的bean对象
    + 查找的方式和AutoWired注解的方式一样
###### @Import
+ **作用**        
    + 用于导入其他的配置类
+ **属性**    
    + `value`:用于指定其他配置类的字节码
    + 在SpringConiguration总配置中加上Import注解代表父配置类,将JdbcConfig.class作为参数传进去,就会扫描JdbcCOnfig.java
###### @PropertySource
+ **作用**    
    + 用于指定properties文件的位置
+ **属性**    
    + `value`:指定文件的名称和路径
    + 关键字：classpath->表示类路径下
```sql
create table account(
	id int primary key auto_increment,
	name varchar(40),
	money float
)character set utf8 collate utf8_general_ci;
insert into account(name,money) values('aaa',1000);
insert into account(name,money) values('bbb',1000);
insert into account(name,money) values('ccc',1000);
```   
```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>commons-dbutils</groupId>
            <artifactId>commons-dbutils</artifactId>
            <version>1.4</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.17</version>
        </dependency>
        <dependency>
            <groupId>c3p0</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.1.2</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>
```
jdbcConfig.properties
```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/travel?serverTimezone=UTC
jdbc.username=root
jdbc.password=123456
```
```java
public interface IAccountService {
    List<Account> findAllAccount();
    Account findAccountById(Integer id);
    void saveAccount(Account account);
    void updateAccount(Account account);
    void deleteAccount(Integer accountId);
}
```
```java
@Service("accountService")
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private IAccountDao accountdao;
    public List<Account> findAllAccount() {
        return accountdao.findAllAccount();
    }
    public Account findAccountById(Integer id) {
        return accountdao.findAccountById(id);
    }
    public void saveAccount(Account account) {
        accountdao.saveAccount(account);
    }
    public void updateAccount(Account account) {
        accountdao.updateAccount(account);
    }
    public void deleteAccount(Integer accountId) {
        accountdao.deleteAccount(accountId);
    }
}
```
```java
public interface IAccountDao {
    List<Account> findAllAccount();
    Account findAccountById(Integer id);
    void saveAccount(Account account);
    void updateAccount(Account account);
    void deleteAccount(Integer accountId);
}
```
```java
@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {
    @Autowired
    private QueryRunner runner;

    public List<Account> findAllAccount() {
        try {
            return runner.query("select * from account", new BeanListHandler<Account>(Account.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Account findAccountById(Integer id) {
        try {
            return runner.query("select * from account where id = ? ", new BeanHandler<Account>(Account.class), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAccount(Account account) {
        try {
            runner.update("insert into account(name,money) values(? , ?)", account.getName(), account.getMoney());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAccount(Account account) {
        try {
            runner.update("update account set name = ?, money = ? where id = ?", account.getName(), account.getMoney(), account.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(Integer accountId) {
        try {
            runner.update("delete from account where id = ?", accountId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```
```java
public class Account {
    private Integer id;
    private String name;
    private Float money;
    // get and set ...
    // toString ...
    
```
```java
@ComponentScan("com.itheima")
@Import(JdbcConfig.class)
@PropertySource("classpath:jdbcConfig.properties")
public class SpringConfiguration {

}
```
```java
/**
 * 和Spring连接数据库相关的配置类
 */
public class JdbcConfig {
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    /**
     * 创建一个QueryRunner对象
     *
     * @param dataSource
     * @return
     */
    @Scope("prototype")
    @Bean(name = "runner")
    public QueryRunner createQueryRunner(@Qualifier("dataSource") DataSource source) {// 配置多数据源,使用@Qualifier指定数据源
        return new QueryRunner(source);
    }

    /**
     * 创建数据源对象
     *
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource createDataSource() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setDriverClass(driver);
            ds.setJdbcUrl(url);
            ds.setUser(username);
            ds.setPassword(password);
            return ds;
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean(name = "dataSourceOther")
    public DataSource createDataSourceOther() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setDriverClass(driver);
            ds.setJdbcUrl("另一个数据库的url");
            ds.setUser(username);
            ds.setPassword(password);
            return ds;
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }
}
```
**Spring整合Junit的配置**
+ 0.当我们使用Spring5.x版本的时候，要求Junit的jar必须是4.1.2及以上
+ 1.导入Spring整合Junit的jar
+ 2.使用Junit提供的@Runwith把原有的main方法替换了，替换成Spring提供的
+ 3.告知Spring的运行期，Spring和IOC创建是基于XML还是注解的，并且说明位置
    + @ContextConfiguration
        + locations:指定XML文件的位置，加上classpath关键字，表示在类路径下
        + classes：指定注解类所在的位置
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AccountTest {

    @Autowired
    private IAccountService as;

    @Test
    public void testFindAll() {
        List<Account> allAccount = as.findAllAccount();
        for (Account account : allAccount) {
            System.out.println(account);
        }
    }
    @Test
    public void testFindOne() {
        Account a = as.findAccountById(1);
        System.out.println(a);
    }
    @Test
    public void testSave() {
        Account account = new Account();
        account.setName("jack");
        account.setMoney(2000f);
        as.saveAccount(account);
    }
    @Test
    public void testUpdate() {
        Account account = as.findAccountById(4);
        account.setMoney(123f);
        as.updateAccount(account);
    }
    @Test
    public void testDelete() {
        as.deleteAccount(4);
    }
}
```    
## 四 动态代理
### 特点
+ 字节码随用随创建,随用随加载
### 作用
+ 不修改源码的基础上对方法增强
### 分类
+ 基于接口的动态代理
+ 基于子类的动态代理(cglib动态代理)
#### 基于接口的动态代理
```java
public interface IProducer {
    public void saleProduct(float money);

    public void afterService(float money);
}
```
```java
/**
 * 生产者
 */
public class Producer implements IProducer {
    /**
     * 销售
     * @param money
     */
    public void saleProduct(float money) {
        System.out.println("销售产品,拿到钱" + money);
    }
    /**
     * 售后
     * @param money
     */
    public void afterService(float money) {
        System.out.println("提供售后服务,拿到钱" + money);
    }
}
```

```java
/**
 * 模拟一个消费者
 */
public class Client {
    public static void main(String[] args) {
        final Producer producer = new Producer();
        /**
         *  基于接口的动态代理：
         *      涉及的类：Proxy
         *      提供者：JDK官方
         * 如何创建代理对象：
         *      使用Proxy类中的newProxyInstance
         * 创建代理对象的要求：
         *      被代理类最少实现一个接口,如果没有则不能使用
         * newProxyInstance方法的参数：
         *      ClassLoader：类加载器
         *          用于加载代理对象字节码的。和被代理对象使用相同的类加载器
         *      Class[]：字节码数组
         *          用于让代理对象和被代理对象有相同的方法
         *      InvocationHandler：用于提供增强的代码
         *          它是让我们写如何代理。我们一般都是写一个该接口的实现类。通常情况下都是匿名内部类。但不是必须的
         *          此接口的实现类都是谁用谁写
         */
        IProducer proxyProducer = (IProducer) Proxy.newProxyInstance(producer.getClass().getClassLoader(),
                producer.getClass().getInterfaces(),
                new InvocationHandler() {
                    // 作用：执行被代理对象的任何接口方法都会经过该方法
                    // proxy：代理对象的引用
                    // method：当前执行的方法
                    // args：当前执行方法所需的参数
                    // return：和被代理对象有相同的返回值
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 提供增强的代码
                        Object returnValue = null;
                        // 1.获取方法执行的参数
                        Float money = (Float) args[0];
                        // 2.判断当前方法是不是销售
                        if ("saleProduct".equals(method.getName())) {
                            returnValue = method.invoke(producer, money * 0.8f);
                        }
                        return returnValue;
                    }
                });

        proxyProducer.saleProduct(10000f);
        // 打印结果 8000.0
    }
}

```
#### 基于子类的动态代理(cglib动态代理)
```xml
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>2.1_3</version>
</dependency>
```
```java
public class Producer {
    // 销售
    public void saleProduct(float money) {
        System.out.println("销售产品，并拿到钱" + money);

    }

    // 售后
    public void afterService(float money) {
        System.out.println("提供售后服务，并拿到钱" + money);
    }
}
```
```java
public class Client {
    public static void main(String[] args) {
        final Producer producer = new Producer();

        /**
         * 基于子类的动态代理
         *      涉及的类：Enhancer
         *      提供者：第三方cglib库
         * 如何创建代理对象
         *      使用Enhancer类中的create方法
         * 创建代理对象的要求
         *      被代理类不能是最终类
         * newProxyInstance方法的参数
         *      Class：字节码
         *          指定被代理对象的字节码
         *      Callback：用于提供增强的代码
         *          它是让我们写如何代理。我们一般都是写一个该接口的实现类，通常境况下都是匿名内部类，但不是必须的。
         *          此接口的实现类都是谁用谁写
         *          我们没一般写的都是该接口的实现类,MethodInterceptor
         */
        Producer cglibProducer = (Producer) Enhancer.create(producer.getClass(), new MethodInterceptor() {
            /**
             * 只从被代理对象的任何方法都会经过该方法
             *
             * @param proxy
             * @param method
             * @param args        以上三个参数和基于接口的动态代理invoke方法的参数是一样的
             * @param methodProxy
             * @return
             * @throws Throwable
             */
            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                // 增强的代码
                Object returnValue = null;
                Float money = (Float) args[0];
                if ("saleProduct".equals(method.getName())) {
                    returnValue = method.invoke(producer, money * 0.8f);
                }
                return returnValue;
            }
        });
        cglibProducer.saleProduct(12000f);

    }
}
```
## 五 AOP
### 什么是AOP
AOP为Aspect Oriented programming的缩写,意为面向切面编程,通过预编译方法和运行期动态代理
实现程序功能统一维护的一种技术。AOP是OOP的延续,是Spring框架中的重要内容是函数式变成的一种衍生范型。
利用AOP可以对业务逻辑的各个部分进行隔离,熊二使得业务逻辑各部分之间的耦合度降低,提高了程序的
可重用性,同时提高了开发的效率
### AOP的作用和优势
+ 作用
    + 在程序运行期间,不修改源码对已有方法进行增强
+ 优势
    + 减少重复代码
    + 提高开发效率 
    + 维护方便
### AOP的相关术语
+ Joinpoint(连接点)
    + 所谓连接点是指那些被拦截到的点。在Spring中,这些点指的是方法,因为Spring只支持方法类型的连接点。
+ Pointcut(切入点)
    + 所谓切入点是指我们要对哪些Joinpoint进行拦截的定义(被增强的方法)
+ Advice(通知/增强)
    + 拦截到Joinpoint要做的事情就是通知。
        + 通知的类型：前置通知，后置通知，异常通知，最终通知，环绕通知
        + invoke方法里面的method.invoke(param, param);前面的是前置通知,后面的是后置通知,catch里面的是异常通知,finally里面的是最终通知,整个invoke方法在执行就是环绕通知
+ Introduction(引介)
    + 引介是一种特殊的通知在不修改类代码的前提下,Introduction可以在运行期为类动态地添加一些方法或Field
+ Target(目标对象)
    + 被代理对象
+ Weaving(织入)
    + 把增强应用到目标对象来创建新的代理对象的过程
    + 例：原有的Service无法实现事务的支持,使用动态代理创建代理对象,返回代理对象中加入了事务支持,加入事务支持的过程就叫织入
    + Spring采用动态代理织入,而AspectJ采用编译期织入和类装载期织入
+ Proxy(代理)
    + 新对象(代理对象)
+ Aspect(切面)
    + 切入点和通知(引介)的结合
### 基于XML的AOP配置
+ 前置通知
+ 后置通知
+ 异常通知
+ 最终通知
+ 环绕通知
    + 它是Spring框架为我们提供的一种可以在代码中手动控制增强方法何时执行的方式
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.0.2.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.8.7</version>
</dependency>
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd">

    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"></bean>
    <bean id="logger" class="com.itheima.utils.Logger"></bean>

    <aop:config>
            <!--配置切入点表达式,id属性唯一标识，expression属性用于指定表达式内容
                此标签写在aop:aspect标签的内部只能当前切面使用
                它还可以写在aop:aspect外面，此时就变成了所有切面可用
            -->
        <aop:pointcut id="pt1" expression="execution(* com.itheima.service.impl.*.*(..))"/>
        <aop:aspect id="logAdvice" ref="logger">
            <aop:before method="beforePrintLog" pointcut-ref="pt1"/>
            <aop:after-returning method="afterReturningPrintLog" pointcut-ref="pt1"/>
            <aop:after-throwing method="afterThrowingPrintLog" pointcut-ref="pt1"/>
            <aop:after method="afterPrintLog" pointcut-ref="pt1"/>
            <!--测试环绕通知时将上面四个注释掉-->
            <aop:around method="aroundLog" pointcut-ref="pt1"/>
        </aop:aspect>
    </aop:config>
</beans>
```
```java
public class Logger {
    public void beforePrintLog() {
        System.out.println("前置通知Logger类中的beforePrintLog方法开始记录日志了");
    }
    public void afterReturningPrintLog() {
        System.out.println("后置通知Logger类中的afterReturningPrintLog方法开始记录日志了");
    }
    public void afterThrowingPrintLog() {
        System.out.println("异常通知Logger类中的afterThrowingPrintLog方法开始记录日志了");
    }
    public void afterPrintLog() {
        System.out.println("最终通知Logger类中的afterPrintLog方法开始记录日志了");
    }
    /**
     * 环绕通知
     * 问题：
     *      当我们配置了环绕通知之后，切入点方法没有执行，而通知方法执行了
     * 分析：
     *      通过对比动态代理中的环绕通知代码，发现动态代理的环绕通知有明确的切入点方法调用，而我们的代码中没有
     * 解决：
     *      spring框架为我们提供了一个接口：ProceedingJoinPoint。该接口有一个方法proceed()，此方法就相当于明确调用切入点方法
     *      该接口可以作为环绕通知的方法参数，在程序执行时，spring框架会为我们提供该接口的实现类供我们使用
     * Spring中的环绕通知：
     *      它是Spring框架为我们提供的一种可以在代码中手动控制增强方法何时执行的方式
     */
    public Object aroundLog(ProceedingJoinPoint pjp){
        Object rtValue = null;
        try {
            // 得到方法执行的所需参数
            Object[] args = pjp.getArgs();
            System.out.println("前置环绕通知Logger类中的aroundLog方法开始记录日志了");
            // 明确调用业务层方法(切入点方法
            pjp.proceed(args);
            System.out.println("后置环绕通知Logger类中的aroundLog方法开始记录日志了");
            return rtValue;
        } catch (Throwable throwable) {
            System.out.println("异常环绕通知Logger类中的aroundLog方法开始记录日志了");
            throwable.printStackTrace();
        }finally {
            System.out.println("最终环绕通知Logger类中的aroundLog方法开始记录日志了");
        }
        return rtValue;
    }
}
```
```java
public interface IAccountService {
    void saveAccount();

    void updateAccount(int i);

    int deleteAccount();
}
```
```java
public class AccountServiceImpl implements IAccountService {
    public void saveAccount() {
//        int i = 1 / 0;
        System.out.println("执行了保存");
    }
    public void updateAccount(int i) {
        System.out.println("执行了更新" + i);
    }
    public int deleteAccount() {
        System.out.println("执行了删除");
        return 0;
    }
}
```
```java
public class AOPTest {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = (IAccountService) ac.getBean("accountService");
        as.saveAccount();
    }
}
```
### 基于注解的AOP配置
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.0.2.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.8.7</version>
</dependency>
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
    <!--配置spring创建容器时要扫描的包-->
    <context:component-scan base-package="com.itheima"></context:component-scan>

    <!--配置spring开启注解AOP的支持-->
    <aop:aspectj-autoproxy/>
</beans>
```
```java
@Component("logger")
@Aspect// 当前类是一个切面类
public class Logger {
    @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
    private void pt1(){}

//    @Before("pt1()")
    public void beforePrintLog() {
        System.out.println("前置通知Logger类中的beforePrintLog方法开始记录日志了");
    }
//    @AfterReturning("pt1()")
    public void afterReturningPrintLog() {
        System.out.println("后置通知Logger类中的afterReturningPrintLog方法开始记录日志了");
    }
//    @AfterThrowing("pt1()")
    public void afterThrowingPrintLog() {
        System.out.println("异常通知Logger类中的afterThrowingPrintLog方法开始记录日志了");
    }
//    @After("pt1()")
    public void afterPrintLog() {
        System.out.println("最终通知Logger类中的afterPrintLog方法开始记录日志了");
    }

    /**
     * 环绕通知
     * 问题：
     *      当我们配置了环绕通知之后，切入点方法没有执行，而通知方法执行了
     * 分析：
     *      通过对比动态代理中的环绕通知代码，发现动态代理的环绕通知有明确的切入点方法调用，而我们的代码中没有
     * 解决：
     *      spring框架为我们提供了一个接口：ProceedingJoinPoint。该接口有一个方法proceed()，此方法就相当于明确调用切入点方法
     *      该接口可以作为环绕通知的方法参数，在程序执行时，spring框架会为我们提供该接口的实现类供我们使用
     * Spring中的环绕通知：
     *      它是Spring框架为我们提供的一种可以在代码中手动控制增强方法何时执行的方式
     */
    @Around("pt1()")
    public Object aroundLog(ProceedingJoinPoint pjp){
        Object rtValue = null;
        try {
            // 得到方法执行的所需参数
            Object[] args = pjp.getArgs();
            System.out.println("前置环绕通知Logger类中的aroundLog方法开始记录日志了");
            // 明确调用业务层方法(切入点方法
            pjp.proceed(args);
            System.out.println("后置环绕通知Logger类中的aroundLog方法开始记录日志了");
            return rtValue;
        } catch (Throwable throwable) {
            System.out.println("异常环绕通知Logger类中的aroundLog方法开始记录日志了");
            throwable.printStackTrace();
        }finally {
            System.out.println("最终环绕通知Logger类中的aroundLog方法开始记录日志了");
        }
        return rtValue;
    }
}
```
```java
public interface IAccountService {
    void saveAccount();
    void updateAccount(int i);
    int deleteAccount();
}
```
```java
public class AccountServiceImpl implements IAccountService {
    public void saveAccount() {
//        int i = 1 / 0;
        System.out.println("执行了保存");
    }
    public void updateAccount(int i) {
        System.out.println("执行了更新" + i);
    }
    public int deleteAccount() {
        System.out.println("执行了删除");
        return 0;
    }
}
```
```java
public class AOPTest {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService as = (IAccountService) ac.getBean("accountService");
        as.saveAccount();
    }
}
```
```
前置环绕通知Logger类中的aroundLog方法开始记录日志了
执行了保存
后置环绕通知Logger类中的aroundLog方法开始记录日志了
最终环绕通知Logger类中的aroundLog方法开始记录日志了

Process finished with exit code 0
```
## 六 Spring中的事务
### 隔离级别
事务隔离级别反应事务提交并发访问时的处理态度
+ ISOLATION_DEFAULT(默认的级别,归属下列某一种)
+ ISOLATION_READ_UNCOMMITTED(只能读取提交数据,解决脏读问题,Oracle默认级别)
+ ISOLATION_REPEATABLE_READ(是否读取其他事务提交修改后的数据,解决不可重复读问题,Mysql默认级别)
+ ISOLATION_SERIALIZABLE(是否读取其他食物提交添加后的数据,解决幻影读问题)
### 传播行为
+ REQUIRED(如果当前没有事务,就新建一个事务,如果已经存在一个事务中,加入到这个事务中,默认值)
+ SUPPORTS(支持当前事务,如果当前没有事务,就以非事务方式执行)
+ MANDATORY(使用当前的事务,如果当前没有事务,就抛出异常)
+ REQUERS_NEW(新建事务,如果当前在事务中,把当前事务挂起)
+ NOT_SUPPORTED(以非事务方式执行操作,如果当前存在事务,就把当前事务挂起)
+ NEVER(以非事务方式运行,如果当前存在事务,抛出异常)
+ NESTED(如果当前存在事务,则在嵌套事务内执行,如果当前没有事务,则执行REQUIRED类似的操作)

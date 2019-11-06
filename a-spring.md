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
    + **Spring中IOC的常用注解**
        + **用于创建对象的**
            + **@Component**
            + **由@Component衍生出来的注解 @Controller @Service @Repository**
        + **用于注入数据的**
            + **@Autowired @Qualifier @Resource @Value**
        + **用于改变作用范围的**
            + **@Scope**
        + **和生命周期相关的(了解)**
            + **@PostConstruct @PreDestroy**
    + **案例使用xml方法和注解方式实现单表的CRUD操作**
    + **改造基于注解的IOC案例 使用纯注解的方式实现**
    + **Spring和Junit整合**
    
+ **一 ElasticSearch简介**
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
#### BeanFactory(多例对象适用)
**它在构造核心容器时,创建对象采取的策略是采用延迟加载的方式。什么时候根据id获取对象了,什么时候才真正的创建对象**    
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
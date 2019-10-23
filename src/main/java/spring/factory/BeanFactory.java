package spring.factory;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

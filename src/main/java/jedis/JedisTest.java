package jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * jedis的测试类
 */
public class JedisTest {
    @Test
    public void simpleTest() {
        // 1.获取连接
        // 如果使用空构造器，默认值是localhost和6379
        Jedis jedis = new Jedis("localhost", 6379);
        // 2.操作
        jedis.set("username", "zhangsan");
        // 关闭连接
        jedis.close();
    }

    /**
     * string
     */
    @Test
    public void simpleTest2() {
        Jedis jedis = new Jedis();
        jedis.set("username", "zhangsan");
        String username = jedis.get("username");
        System.out.println(username);
        // 可以使用setex()方法存储可以指定过期时间的key value
        jedis.setex("activecode", 20, "hehe");// 将activecode:hehe键值对存入redis，并且20秒后自动删除该键值对
        jedis.close();
    }

    /**
     * hash
     */
    @Test
    public void simpleTest3() {
        Jedis jedis = new Jedis();
        jedis.hset("user", "name", "lisi");
        jedis.hset("user", "age", "23");
        jedis.hset("user", "gender", "male");
        String name = jedis.hget("user", "name");
        System.out.println("取到的名字" + name);
        Map<String, String> user = jedis.hgetAll("user");
        Set<String> keySet = user.keySet();
        for (String key : keySet) {
            String value = user.get(key);
            System.out.println(key + ":" + value);
        }

        jedis.close();
    }

    /**
     * list
     */
    @Test
    public void simpleTest4() {
        Jedis jedis = new Jedis();
        jedis.lpush("mylist", "a", "b", "c ");
        jedis.rpush("mylist", "a", "b", "c");
        List<String> mylist = jedis.lrange("mylist", 0, -1);
        System.out.println(mylist);
        String element = jedis.lpop("mylist");
        System.out.println(element);

        jedis.close();
    }

    /**
     * set
     */
    @Test
    public void simpleTest5() {
        Jedis jedis = new Jedis();
        jedis.sadd("myset", "java", "php", "C++");
        Set<String> myset = jedis.smembers("myset");
        System.out.println(myset);

        jedis.close();
    }

    /**
     * sortedset
     */
    @Test
    public void simpleTest6() {
        Jedis jedis = new Jedis();
        jedis.zadd("mysortedset", 3, "亚瑟");
        jedis.zadd("mysortedset", 30, "后羿");
        jedis.zadd("mysortedset", 25, "孙悟空");
        Set<String> mysortedset = jedis.zrange("mysortedset", 0, -1);
        System.out.println(mysortedset);
    }

    /**
     * Jedis连接池
     */
    @Test
    public void test7() {
        JedisPoolConfig config = new JedisPoolConfig();// config可以进行相关配置
        JedisPool jedisPool = new JedisPool(config, "localhost", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("hehe", "heihei");
        jedis.close();
    }
}

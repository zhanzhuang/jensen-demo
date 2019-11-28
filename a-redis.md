# Redis目录
+ **一 下载安装**
    + **下载**
    + **安装**
+ **二 数据结构与命令操作**
    + **数据结构**
+ **三 通用命令**
+ **四 持久化**
+ **五 Jedis**
+ **六 JedisPool**
# 一 下载安装
+ 官网:https://redis.io
+ 中文网:http://www.redis.net.cn/
+ 下载
    + 貌似redis官网不提供windows版本,在github上面下载
    + https://github.com/microsoftarchive/redis/releases/
+ 安装
    + 解压后直接可以使用
        + redis.windows.conf = 配置文件
        + redis-cli.exe = redis的客户端
        + redis-server.exe = redis服务器
# 二 数据结构与命令操作
+ 数据结构
    + `字符串类型 string`
    + `哈希类型 hash` : 
        + map格式
    + `列表类型 list` : 
        + linkedlist格式，可以重复
        + 是简单的字符串列表，按照插入顺序排序。可以添加一个元素到列表的头部或者尾部
    + `集合类型 set` : 
        + 不允许重复元素
    + `有序集合类型 sortedset` : 
        + 不允许重复，且元素有序
+ 字符串类型 string
    + 存储 : 
        + `set key value`
    + 获取 : 
        + `get key`
    + 删除 : 
        + `del key`
+ 哈希类型 hash
    + 存储 : 
        + `hset field key value`
    + 获取 : 
        + `hget field key(获取指定字段的值)`
        + `hgetall field(获取所有)`
    + 删除 : 
        + `hdel field key`
    
+ 列表类型 list
    + 存储 :
        + `lpush key value(将元素添加到列表左边)`
        + `rpush key value(将元素添加到列表右边)`
    + 获取 :
        + `lrange key start end(范围获取)`
        + `lrange key 0 -1(获取所有元素)`
    + 删除 :
        + `lpop key(删除列表左边的元素，并将元素返回)`
        + `rpop key(删除列表右边的元素，并将元素返回)`
+ 集合类型 set
    + 存储 :
        + `sadd key value`
        + `smembers key(获取set集合中所有元素)`
        + `srem key value(删除set集合中的某个元素)`
+ 有序集合类型 sortedset
    + 存储 : 
        + `zadd key score value`
    + 获取 :
        + `zrange key start end`
    + 删除 : 
        + `zrem key value`
# 三 通用命令
+ 查询所有的键
    + `keys *`
+ 查询键的类型
    + `type key`
+ 删除指定的key value
    + `del key`
# 四 持久化
+ redis是一个内存数据库,当redis服务器重启或者电脑重启数据就会丢失,我们可以将redis内存中的数据持久化保存到硬盘中
+ 持久化机制
    + RDB
        + 默认使用这种方式，不需要配置
        + 在一定的间隔时间中，检测key的变化情况，然后持久化数据
        + 1.编辑`redis.windows.conf`文件
            + save 900 1(after 15 min if at least 1 key changed)
            + save 300 10(after 5 min if at least 10 keys changed)
            + save 60 10000(after 1min if at least 10000 keys changed)
        + 2.重启redis服务器，并在redis文件夹下打开命令行：redis-server.exe redis.windows.conf
    + AOF
        + 日志记录的方式，可以记录每一条命令的操作。可以每一次命令操作后来持久化数据
        + 1.编辑`redis.windows.conf`文件
        + 2.`appendonly no` 改成 `appendonly yes`(开启AOF持久化)
            + `\#appendfsync always`(每一次操作都会持久化)
            + `appendfsync everysec`(每隔一秒持久化一次)
            + `\# appendfsync no`(不进行持久化)
# 五 Jedis
+ jedis是一款java操作redis数据库的工具
+ 使用步骤
    + 1.下载jedis的jar包
    ```xml
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>3.1.0</version>
    </dependency>
    ```
    + 2.使用
    ```java
    // 1.获取连接
    Jedis jedis = new Jedis("localhost", 6379);
    // 2.操作
    jedis.set("username", "zhangsan");
    // 关闭连接
    jedis.close();
    ```
    + 代码
    ```java
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
    ```
# 六 JedisPool
+ 使用
    + 简单的Jedis连接池
    ```java
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
    ```
    + 改良的Jedis连接池
    ```java
    /**
     * JedisPool工具类
     * 加载配置文件，配置连接池的参数
     */
    public class JedisPoolUtils {
        private static JedisPool jedisPool;
        // 当类加载的时候读取配置文件
        static {
            InputStream is = JedisPoolUtils.class.getClassLoader().getResourceAsStream("jedis.properties");
            Properties pro = new Properties();
            try {
                pro.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(Integer.parseInt(pro.getProperty("maxTotal")));
            config.setMaxIdle(Integer.parseInt(pro.getProperty("maxIdle")));
            jedisPool = new JedisPool(config, pro.getProperty("host"), Integer.parseInt(pro.getProperty("port")));
    
        }
        /**
         * 获取连接的方法
         */
        public static Jedis getJedis() {
            return jedisPool.getResource();
        }
    }
    ```
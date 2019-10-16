package elasticSearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

/**
 * 增删改
 */
public class ElasticSearchTest {

    private TransportClient client;

    /**
     * 创建客户端
     * @throws Exception
     */
    @Before
    public void init() throws Exception{
        Settings settings = Settings.builder()
                .put("cluster.name", "my-elasticsearch")
                .build();

        client = new PreBuiltTransportClient(settings)
                // 一次性弄好Client对象
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9301))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9302))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9303));

    }

    /**
     * 创建索引
     * @throws Exception
     */
    @Test
    public void createIndex() throws Exception {
        // 1.创建一个Settings对象，相当于是一个配置信息。主要配置集群的名称。
        Settings settings = Settings.builder()
                .put("cluster.name", "my-elasticsearch")
                .build();
        // 2.创建一个客户端Client对象
        TransportClient client = new PreBuiltTransportClient(settings);
        // ip地址=InetAddress.getByName("127.0.0.1")      9201是http提供对外服务的端口号 这里要用tcp连接es服务器所以是9301
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9301));
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9302));
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9303));
        // 3.使用client创建索引库
        // admin()=管理员  indices()=对索引库进行管理  prepareCreate()=创建索引库   get()=执行
        client.admin().indices().prepareCreate("index_hello").get();
        // 4.管理client对象
        client.close();

    }

    /**
     * 设置mapping
     * @throws Exception
     */
    @Test
    public void setMappins() throws Exception {
        // 1.创建一个Settings对象
        Settings settings = Settings.builder()
                .put("cluster.name", "my-elasticsearch")
                .build();
        // 2.创建一个客户端Client对象
        TransportClient client = new PreBuiltTransportClient(settings)
                // 一次性弄好Client对象
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9301))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9302))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9303));
        // 3.创建一个mappings信息
        /**
         * "mappings" : {
         *              "article" : {
         *                 "dynamic" : "false",
         *                  "properties" : {
         *                     "id" : { "type" : "string" },
         *                      "content" : { "type" : "string" },
         *                     "author" : { "type" : "string" }
         *                   }
         *              }
         * }
         */
        XContentBuilder builder = new XContentFactory().jsonBuilder()
                .startObject() // 相当于 {
                    .startObject("article")
                        .startObject("properties")
                            .startObject("id")
                                .field("type","long")
                                .field("store",true)
                            .endObject()
                            .startObject("title")
                                .field("type","text")
                                .field("store",true)
                                .field("analyzer","ik_smart")
                            .endObject()
                            .startObject("content")
                                .field("type","text")
                                .field("store",true)
                                .field("analyzer","ik_smart")
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();
        // 4.使用client把mapping信息设置到索引库中
        client.admin().indices()
                // 索引
                .preparePutMapping("index_hello")
                // type
                .setType("article")
                // mapping信息，可以是XXontentBuilder对象 也可以是json格式的字符串
                .setSource(builder)
                // 执行
                .get();
        // 5.关闭连接
        client.close();

    }

    /**
     * 添加文档
     * @throws Exception
     */
    @Test
    public void testAddDocument() throws Exception {
        // client对象

        // 文档对象
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                    .field("id", 2L)
                    .field("title", "222北方入秋速度明显加快 多地降温幅度最多可达10度")
                    .field("content", "222阿联酋一架客机在纽约机场被隔离 10名乘客病倒")
                .endObject();
        // 把文档对象添加到索引库
        client.prepareIndex()
                .setIndex("index_hello")//索引名称
                .setType("article")// type
                .setId("2")// 文档id，如果不设置会自动生成
                .setSource(builder)// 设置文档信息
                .get();// 执行
        // close
        client.close();
    }

    /**
     * 添加文档(创建pojo的方式,较为方便)
     * @throws Exception
     */
    @Test
    public void testAddDocument2() throws Exception {
        // 设置一个Article对象
        Article article = new Article();
        // 设置对象的属性
        article.setId(3L);
        article.setTitle("搜索工作其实很快乐");
        article.setContent("我们希望我们的搜索解决方案要快，我们希望有一个零配置和一个完全免费的搜索模式，我们希望能够简单地使用JSON通过HTTP的索引数据，我们希望我们的搜索服务器始终可用，我们希望能够一台开始并扩展到数百，我们要实时搜索，我们要简单的多租户，我们希望建立一个云的解决方案。Elasticsearch旨在解决所有这些问题和更多的");
        // 把article对象转换成json格式的字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonDocument = objectMapper.writeValueAsString(article);
        System.out.println(jsonDocument);
        // 使用client把文档写入索引库
        client.prepareIndex("index_hello","article","3")
                .setSource(jsonDocument, XContentType.JSON)
                .get();
        // 关闭客户端
        client.close();
    }


}

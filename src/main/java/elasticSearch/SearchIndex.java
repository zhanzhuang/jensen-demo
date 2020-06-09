package elasticSearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Iterator;
import java.util.Map;

public class SearchIndex {
    private TransportClient client;

    /**
     * 创建客户端
     *
     * @throws Exception
     */
    @Before
    public void init() throws Exception {
        Settings settings = Settings.builder()
                .put("cluster.name", "my-elasticsearch")
                .build();

        client = new PreBuiltTransportClient(settings)
                // 一次性弄好Client对象
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9301))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9302))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9303));

    }

    private void search(QueryBuilder queryBuilder) {
        SearchResponse searchResponse = client.prepareSearch("index_hello")
                .setTypes("article")
                .setQuery(queryBuilder)
                .setFrom(0) // 开始索引
                .setSize(5) // 设置大小
                .get();
        // 取查询结果
        SearchHits searchHits = searchResponse.getHits();
        // 取查询结果的总记录数
        System.out.println("查询结果总记录数" + searchHits.getTotalHits());
        // 查询结果列表
        Iterator<SearchHit> iterator = searchHits.iterator();
        while (iterator.hasNext()) {
            SearchHit searchHit = iterator.next();
            // 打印文档对象,JSON格式输出
            System.out.println(searchHit.getSourceAsString());
            // 取文档的属性
            System.out.println("-----文档的属性");
            Map<String, Object> document = searchHit.getSource();
            System.out.println(document.get("id"));
            System.out.println(document.get("title"));
            System.out.println(document.get("content"));
        }
        // 关闭
        client.close();
    }

    /**
     * 根据ID进行查询
     *
     * @throws Exception
     */
    @Test
    public void testSearchById() throws Exception {
        QueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds("1", "2");
        search(queryBuilder);
    }

    /**
     * 根据关键词查询
     *
     * @throws Exception
     */
    @Test
    public void testSearchByTerm() throws Exception {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("title", "北方");
        search(queryBuilder);
    }

    /**
     * 带分析的查询
     */
    @Test
    public void testSearchByQueryString() {
        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("快乐")
                .defaultField("title");// 不指定title会在所有域进行查询
        search(queryBuilder, "title");
    }


    private void search(QueryBuilder queryBuilder, String highlightField) {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(highlightField); // 高亮显示的字段
        highlightBuilder.preTags("<em>");
        highlightBuilder.postTags("</em>");
        SearchResponse searchResponse = client.prepareSearch("index_hello")
                .setTypes("article")
                .setQuery(queryBuilder)
                .setFrom(0) // 开始索引
                .setSize(5) // 设置大小
                .highlighter(highlightBuilder) // 设置高亮信息
                .get();
        // 取查询结果
        SearchHits searchHits = searchResponse.getHits();
        // 取查询结果的总记录数
        System.out.println("查询结果总记录数" + searchHits.getTotalHits());
        // 查询结果列表
        Iterator<SearchHit> iterator = searchHits.iterator();
        while (iterator.hasNext()) {
            SearchHit searchHit = iterator.next();
            // 打印文档对象,JSON格式输出
            System.out.println(searchHit.getSourceAsString());
            // 取文档的属性
            System.out.println("-----文档的属性");
            Map<String, Object> document = searchHit.getSource();
            System.out.println(document.get("id"));
            System.out.println(document.get("title"));
            System.out.println(document.get("content"));
            System.out.println("***********高亮结果");
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            System.out.println(highlightFields);
            // 取title高亮显示的结果
            HighlightField field = highlightFields.get(highlightField);
            Text[] fragments = field.getFragments();
            if (fragments != null) {
                String title = fragments[0].toString();
                System.out.println(title);
            }

        }
        // 关闭
        client.close();
    }
}

package elasticSearch.springdata.test;

import elasticSearch.springdata.entity.Article;
import elasticSearch.springdata.repositories.ArticleRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringDataElasticSearchTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void createIndex() {
        // 创建索引，并配置映射关系
        template.createIndex(Article.class);
        // 只有索引库没有配置映射的时候执行(单独配置映射)
//        template.putMapping(Article.class);
    }
    @Test
    public void addDocument() {
        for (int i = 10; i <= 20; i++) {

            Article article = new Article();
            article.setId(i);
            article.setTitle(i + "斋藤飞鸟");
            article.setContent(i + "斋藤飞鸟是日缅混血儿，1998年8月10日出生于日本东京都 [9]  ，因为两个哥哥的名字里都带有“鸟”字，父亲给她取名为“飞鸟”；她在私立学校上小学时看到舞蹈社以AKB48的曲目为伴奏表演，此后产生了对成为偶像（歌手）的向往 [10]  ；斋藤飞鸟小学时的社团是铜管乐队，初中则参加了吹奏乐部，不过获选为乃木坂46成员后便退出了社团");
            articleRepository.save(article);
        }
    }
    @Test
    public void delDocumentById() {
        articleRepository.deleteById(2L);
//        articleRepository.deleteAll();
    }

    @Test
    public void findAll() {
        Iterable<Article> articles = articleRepository.findAll();
        articles.forEach(System.out::println);
    }
    @Test
    public void testFindById() {
        Optional<Article> optional = articleRepository.findById(1L);
        Article article = optional.get();
        System.out.println(article);
    }

    /**
     * 对内容进行分词再查询
     */
    @Test
    public void testFindByTitle() {
        List<Article> list = articleRepository.findByTitle("飞鸟");
        list.forEach(System.out::println);
    }
    /**
     * 对内容进行分词再查询
     */
    @Test
    public void testFindByTitleOrContent() {
        // 默认从0开始每页显示10条数据
        List<Article> list = articleRepository.findByTitleOrContent("14", "content中没有的内容");
        list.forEach(System.out::println);
    }
    /**
     * 对内容进行分词再查询
     */
    @Test
    public void testFindByTitleOrContentPage() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Article> list = articleRepository.findByTitleOrContent("飞鸟", "content中没有的内容", pageable);
        list.forEach(System.out::println);
    }

    /**
     * queryString查询
     */
    @Test
    public void testNativeSearchQuery() {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery("飞鸟是什么").defaultField("title"))
                .withPageable(PageRequest.of(0, 3))
                .build();
        List<Article> articleList = template.queryForList(query, Article.class);
        articleList.forEach(System.out::println);
    }

}

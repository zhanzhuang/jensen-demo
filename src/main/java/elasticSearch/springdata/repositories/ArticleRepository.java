package elasticSearch.springdata.repositories;

import elasticSearch.springdata.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {
    // 按照spring-data-elasticsearch 命名规则定义方法，无需实现，会自动实现
    List<Article> findByTitle(String title);
    // OR查询
    List<Article> findByTitleOrContent(String title, String content);
    List<Article> findByTitleOrContent(String title, String content, Pageable pageable);
}

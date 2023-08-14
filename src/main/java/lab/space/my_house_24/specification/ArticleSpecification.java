package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.Predicate;
import lab.space.my_house_24.entity.Article;
import lab.space.my_house_24.model.article.ArticleRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ArticleSpecification {
    public Specification<Article> getArticleByRequest(ArticleRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

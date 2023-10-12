package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.Predicate;
import lab.space.my_house_24.entity.Article;
import lab.space.my_house_24.enums.ArticleType;
import lab.space.my_house_24.model.article.ArticleRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Component
public class ArticleSpecification {
    public Specification<Article> getArticleByRequest(ArticleRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nonNull(request.typeQuery())) {
                if (request.typeQuery()) {
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("type"), ArticleType.INCOME)
                    ));
                } else {
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("type"), ArticleType.EXPENSE)
                    ));
                }
            }
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Article> getArticleForSelect(String name, Boolean type) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nonNull(type)) {
                if (type) {
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("type"), ArticleType.INCOME)
                    ));
                } else {
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("type"), ArticleType.EXPENSE)
                    ));
                }
            }
            if (nonNull(name) && !Objects.equals(name, "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("name"), "%" + name + "%")
                ));
            }
            query.orderBy(criteriaBuilder.asc(root.get("name")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}

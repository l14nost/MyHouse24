package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.Article;
import lab.space.my_house_24.enums.ArticleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    boolean existsByNameAndType(String name, ArticleType type);
}

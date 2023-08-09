package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Article;
import lab.space.my_house_24.model.article.ArticleResponse;
import lab.space.my_house_24.model.article.ArticleSaveRequest;
import lab.space.my_house_24.model.article.ArticleUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ArticleService {

    Article getArticleById(Long id);
    ResponseEntity<?> getArticleDtoById(Long id);

    List<ArticleResponse> getAllArticleDto();

    void saveArticle(Article article);

    ResponseEntity<?> updateArticleByRequest(ArticleUpdateRequest article);

    void saveArticleByRequest(ArticleSaveRequest article);

    ResponseEntity<?> deleteArticleById(Long id);

}

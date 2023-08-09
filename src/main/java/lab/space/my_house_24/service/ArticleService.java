package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Article;
import lab.space.my_house_24.model.article.ArticleResponse;

import java.util.List;

public interface ArticleService {
    Article getArticleById(Long id);
    List<ArticleResponse> getAllArticleDto();

    void saveArticle(Article article);
    void updateArticle(Article article);
}

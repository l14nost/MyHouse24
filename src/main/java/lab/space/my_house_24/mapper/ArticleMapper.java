package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Article;
import lab.space.my_house_24.model.article.ArticleResponse;
import lab.space.my_house_24.model.article.ArticleSaveRequest;
import lab.space.my_house_24.model.article.ArticleUpdateRequest;
import org.springframework.context.i18n.LocaleContextHolder;

public interface ArticleMapper {

    static ArticleResponse toSimpleDto(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .name(article.getName())
                .build();
    }

    static ArticleResponse toArticleResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .name(article.getName())
                .type(EnumMapper.toSimpleDto(
                        article.getType().name(),
                        article.getType().getArticleType(LocaleContextHolder.getLocale()))
                )
                .build();
    }

    static Article toArticleAfterSaveRequest(ArticleSaveRequest articleSaveRequest) {
        return Article.builder()
                .name(articleSaveRequest.name())
                .type(articleSaveRequest.type())
                .build();
    }

    static Article toArticleAfterUpdateRequest(ArticleUpdateRequest articleUpdateRequest, Article article) {
        return article
                .setName(articleUpdateRequest.name())
                .setType(articleUpdateRequest.type());
    }

}

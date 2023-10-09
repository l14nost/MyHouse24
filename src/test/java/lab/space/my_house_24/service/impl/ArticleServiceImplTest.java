package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.Article;
import lab.space.my_house_24.enums.ArticleType;
import lab.space.my_house_24.model.article.ArticleRequest;
import lab.space.my_house_24.model.article.ArticleResponse;
import lab.space.my_house_24.model.article.ArticleSaveRequest;
import lab.space.my_house_24.model.article.ArticleUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.repository.ArticleRepository;
import lab.space.my_house_24.specification.ArticleSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleSpecification articleSpecification;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    void getArticleById() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(Article.builder().build()));
        assertEquals(Article.builder().build(), articleService.getArticleById(1L));
    }

    @Test
    void getArticleDtoById() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(Article.builder()
                .id(1L)
                .name("Test")
                .type(ArticleType.INCOME)
                .build()));
        assertEquals(ArticleResponse.builder().id(1L).build().id(), articleService.getArticleDtoById(1L).id());
        verify(articleRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllArticleDto() {
        Page<Article> articles = new PageImpl<>(
                List.of(
                        Article.builder()
                                .id(1L)
                                .name("Test")
                                .type(ArticleType.INCOME)
                                .build(),
                        Article.builder()
                                .id(2L)
                                .name("Test")
                                .type(ArticleType.INCOME)
                                .build(),
                        Article.builder()
                                .id(3L)
                                .name("Test")
                                .type(ArticleType.INCOME)
                                .build(),
                        Article.builder()
                                .id(4L)
                                .name("Test")
                                .type(ArticleType.INCOME)
                                .build()
                )
        );
        when(articleRepository.findAll((Specification<Article>) any(), any(PageRequest.class))).thenReturn(articles);
        Page<ArticleResponse> articleResponses = articleService.getAllArticleDto(ArticleRequest.builder().pageIndex(1).build());
        assertEquals(4, articleResponses.getTotalElements());
        assertEquals(ArticleResponse.class, articleResponses.iterator().next().getClass());
    }

    @Test
    void getAllArticleResponseByType() {
        List<Article> articles = List.of(
                Article.builder()
                        .id(1L)
                        .name("Test")
                        .type(ArticleType.INCOME)
                        .build(),
                Article.builder()
                        .id(2L)
                        .name("Test")
                        .type(ArticleType.INCOME)
                        .build(),
                Article.builder()
                        .id(3L)
                        .name("Test")
                        .type(ArticleType.INCOME)
                        .build(),
                Article.builder()
                        .id(4L)
                        .name("Test")
                        .type(ArticleType.INCOME)
                        .build()
        );
        when(articleRepository.findAll((Specification<Article>) any())).thenReturn(articles);
        List<ArticleResponse> articleResponses = articleService.getAllArticleResponseByType(true);
        assertEquals(4, articleResponses.size());
        assertEquals(ArticleResponse.class, articleResponses.iterator().next().getClass());
    }

    @Test
    void getAllType() {
        assertEquals(2, articleService.getAllType().size());
        assertEquals(EnumResponse.class, articleService.getAllType().iterator().next().getClass());
    }

    @Test
    void saveArticle() {
        articleService.saveArticle(Article.builder().build());
        verify(articleRepository, times(1)).save(any());
    }

    @Test
    void updateArticleByRequest() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(Article.builder()
                .id(1L)
                .cashBoxList(List.of())
                .build()));
        articleService.updateArticleByRequest(ArticleUpdateRequest.builder().id(1L).name("Test").type(ArticleType.INCOME).build());
        verify(articleRepository, times(1)).save(any());
        verify(articleRepository, times(1)).findById(anyLong());
    }

    @Test
    void saveArticleByRequest() {
        articleService.saveArticleByRequest(ArticleSaveRequest.builder().name("Test").type(ArticleType.INCOME).build());
        verify(articleRepository, times(1)).save(any());
        verify(articleRepository, times(0)).findById(anyLong());
    }

    @Test
    void deleteArticleById() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(Article.builder()
                .id(1L)
                .cashBoxList(List.of())
                .build()));
        articleService.deleteArticleById(1L);
        verify(articleRepository, times(1)).findById(anyLong());
        verify(articleRepository, times(1)).delete((Article) any());
    }
}

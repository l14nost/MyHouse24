package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Article;
import lab.space.my_house_24.mapper.ArticleMapper;
import lab.space.my_house_24.model.article.ArticleResponse;
import lab.space.my_house_24.model.article.ArticleSaveRequest;
import lab.space.my_house_24.model.article.ArticleUpdateRequest;
import lab.space.my_house_24.repository.ArticleRepository;
import lab.space.my_house_24.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public Article getArticleById(Long id) throws EntityNotFoundException {
        log.info("Try to search by id" + id);
        return articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found by id " + id));
    }

    @Override
    public ResponseEntity<?> getArticleDtoById(Long id) {
        try {
            log.info("Try to convert Article in dto by id" + id);
            return ResponseEntity.ok(ArticleMapper.toSimpleDto(getArticleById(id)));
        } catch (EntityNotFoundException e) {
            log.error("Error update Article after request");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public List<ArticleResponse> getAllArticleDto() {
        return null;
    }

    @Override
    public void saveArticle(Article article) {
        log.info("Try to save Article");
        articleRepository.save(article);
        log.info("Success save Article");
    }

    @Override
    public ResponseEntity<?> updateArticleByRequest(ArticleUpdateRequest article) {
        try {
            log.info("Try to update Article after request");
            saveArticle(
                    ArticleMapper.toArticleAfterUpdateRequest(
                            article,
                            getArticleById(article.id())
                    )
            );
            log.info("Success update Article after request");
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Error update Article after request");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public void saveArticleByRequest(ArticleSaveRequest article) {
        log.info("Try to save Article after request");
        saveArticle(
                ArticleMapper.toArticleAfterSaveRequest(
                        article
                )
        );
        log.info("Success save Article after request");
    }

    @Override
    public ResponseEntity<?> deleteArticleById(Long id) {
        try {
            log.info("Try to delete Article");
            articleRepository.delete(getArticleById(id));
            log.info("Success delete Article");
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }

    }
}

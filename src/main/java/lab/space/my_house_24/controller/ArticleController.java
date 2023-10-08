package lab.space.my_house_24.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lab.space.my_house_24.model.article.ArticleRequest;
import lab.space.my_house_24.model.article.ArticleResponse;
import lab.space.my_house_24.model.article.ArticleSaveRequest;
import lab.space.my_house_24.model.article.ArticleUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.service.ArticleService;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.ArticleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("payment-items")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleValidator articleValidator;

    @GetMapping({"/", ""})
    public ModelAndView showArticlePage() {
        return new ModelAndView("admin/pages/article/article");
    }

    @GetMapping("/add")
    public ModelAndView showArticleSavePage() {
        return new ModelAndView("admin/pages/article/article-save");
    }

    @GetMapping("/update-{id}")
    public ModelAndView showArticleUpdatePage(@PathVariable Long id) {
        return new ModelAndView("admin/pages/article/article-save");
    }

    @GetMapping("/all-article-type")
    public ResponseEntity<List<EnumResponse>> getAllArticleType() {
        return ResponseEntity.ok(articleService.getAllType());
    }

    @PostMapping("/get-all-article")
    public ResponseEntity<Page<ArticleResponse>> getAllArticle(@RequestBody ArticleRequest articleRequest) {
        return ResponseEntity.ok(articleService.getAllArticleDto(articleRequest));
    }

    @GetMapping("/get-article-dto/{id}")
    public ResponseEntity<?> getArticleDtoById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be > 0");
        }
        try {
            return ResponseEntity.ok(articleService.getArticleDtoById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping("/save-article")
    public ResponseEntity<?> saveArticle(@Valid @RequestBody ArticleSaveRequest request,
                                         BindingResult bindingResult) {
        articleValidator.isNameUniqueValidation(request,
                bindingResult, "ArticleSaveRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        articleService.saveArticleByRequest(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-article")
    public ResponseEntity<?> updateArticleById(@Valid @RequestBody ArticleUpdateRequest request,
                                               BindingResult bindingResult) {
        articleValidator.isValidationByUpdateRequest(request, bindingResult,
                "ArticleUpdateRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            articleService.updateArticleByRequest(request);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            if (e instanceof EntityNotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-article/{id}")
    public ResponseEntity<?> deleteArticleById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be > 0");
        }
        try {
            articleService.deleteArticleById(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            if (e instanceof EntityNotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

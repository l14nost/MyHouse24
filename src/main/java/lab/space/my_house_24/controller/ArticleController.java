package lab.space.my_house_24.controller;

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

import java.util.List;

@Controller
@RequestMapping("payment-items")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleValidator articleValidator;

    @GetMapping({"/",""})
    public String showArticlePage(){
        return "admin/pages/article/article";
    }

    @GetMapping("/add")
    public String showArticleSavePage(){
        return "admin/pages/article/article-save";
    }
    @GetMapping("/update-{id}")
    public String showArticleUpdatePage(@PathVariable Long id){
        return "admin/pages/article/article-save";
    }

    @GetMapping("/all-article-type")
    public ResponseEntity<List<EnumResponse>> getAllArticleType(){
        return ResponseEntity.ok(articleService.getAllType());
    }

    @PostMapping ("/get-all-article")
    public ResponseEntity<Page<ArticleResponse>> getAllArticle(@RequestBody ArticleRequest articleRequest){
        return ResponseEntity.ok(articleService.getAllArticleDto(articleRequest));
    }

    @GetMapping("/get-article-dto/{id}")
    public ResponseEntity<?> getStaffEditDtoById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be > 0");
        }
        return articleService.getArticleDtoById(id);
    }

    @PostMapping("/save-article")
    public ResponseEntity<?> saveStaff(@Valid @RequestBody ArticleSaveRequest articleSaveRequest,
                                       BindingResult bindingResult) {
        articleValidator.isNameUniqueValidation(articleSaveRequest.name(),
                bindingResult,"ArticleSaveRequest",LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        articleService.saveArticleByRequest(articleSaveRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-article")
    public ResponseEntity<?> updateStaffById(@Valid @RequestBody ArticleUpdateRequest articleUpdateRequest,
                                             BindingResult bindingResult) {
        articleValidator.isNameUniqueValidationWithId( articleUpdateRequest.id(),
                articleUpdateRequest.name(), bindingResult,
                "ArticleUpdateRequest",LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        return articleService.updateArticleByRequest(articleUpdateRequest);
    }

    @DeleteMapping("/delete-article/{id}")
    public ResponseEntity<?> deleteStaffById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be > 0");
        }
        return articleService.deleteArticleById(id);
    }
}

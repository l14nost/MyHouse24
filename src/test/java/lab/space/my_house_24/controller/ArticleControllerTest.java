package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.enums.ArticleType;
import lab.space.my_house_24.model.article.ArticleRequest;
import lab.space.my_house_24.model.article.ArticleResponse;
import lab.space.my_house_24.model.article.ArticleSaveRequest;
import lab.space.my_house_24.model.article.ArticleUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.service.ArticleService;
import lab.space.my_house_24.validator.ArticleValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {

    @MockBean
    private ArticleService articleService;

    @MockBean
    private ArticleValidator articleValidator;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void showArticlePage() throws Exception {
        mockMvc.perform(get("/payment-items"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/article/article"));
    }

    @Test
    void showArticleSavePage() throws Exception {
        mockMvc.perform(get("/payment-items/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/article/article-save"));
    }

    @Test
    void showArticleUpdatePage() throws Exception {
        mockMvc.perform(get("/payment-items/update-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/article/article-save"));
    }

    @Test
    void getAllArticleType() throws Exception {
        List<EnumResponse> enumResponses = List.of(
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build()
        );
        when(articleService.getAllType()).thenReturn(enumResponses);
        mockMvc.perform(get("/payment-items/all-article-type"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(enumResponses)));
    }

    @Test
    void getAllArticle() throws Exception {
        Page<ArticleResponse> articleResponses = new PageImpl<>(List.of(
                ArticleResponse.builder().build(),
                ArticleResponse.builder().build(),
                ArticleResponse.builder().build(),
                ArticleResponse.builder().build()
        ));
        when(articleService.getAllArticleDto(ArticleRequest.builder().pageIndex(1).build())).thenReturn(articleResponses);
        mockMvc.perform(post("/payment-items/get-all-article")
                        .content(objectMapper.writeValueAsString(ArticleRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(articleResponses)));
    }

    @Test
    void getArticleDtoById() throws Exception {
        ArticleResponse articleResponse = ArticleResponse.builder().build();
        when(articleService.getArticleDtoById(anyLong())).thenReturn(articleResponse);
        mockMvc.perform(get("/payment-items/get-article-dto/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getArticleDtoByInvalidId() throws Exception {
        mockMvc.perform(get("/payment-items/get-article-dto/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getArticleDtoByIdNotFound() throws Exception {
        when(articleService.getArticleDtoById(anyLong())).thenThrow(new EntityNotFoundException("Not found"));
        mockMvc.perform(get("/payment-items/get-article-dto/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveArticle() throws Exception {
        ArticleSaveRequest articleRequest = ArticleSaveRequest.builder()
                .name("Test")
                .type(ArticleType.EXPENSE)
                .build();
        mockMvc.perform(post("/payment-items/save-article")
                        .content(objectMapper.writeValueAsString(articleRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(articleService, times(1)).saveArticleByRequest(any());
    }

    @Test
    void saveArticleBadRequest() throws Exception {
        ArticleSaveRequest articleRequest = ArticleSaveRequest.builder()
                .name("test")
                .type(ArticleType.EXPENSE)
                .build();
        mockMvc.perform(post("/payment-items/save-article")
                        .content(objectMapper.writeValueAsString(articleRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(articleService, times(1)).saveArticleByRequest(any());
    }

    @Test
    void updateArticleById() throws Exception {
        ArticleUpdateRequest articleRequest = ArticleUpdateRequest.builder()
                .id(1L)
                .name("Test")
                .type(ArticleType.EXPENSE)
                .build();
        mockMvc.perform(put("/payment-items/update-article")
                        .content(objectMapper.writeValueAsString(articleRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(articleService, times(1)).updateArticleByRequest(any());
    }

    @Test
    void updateArticleByIdBadRequest() throws Exception {
        ArticleUpdateRequest articleRequest = ArticleUpdateRequest.builder()
                .id(1L)
                .name("test")
                .type(ArticleType.EXPENSE)
                .build();
        mockMvc.perform(put("/payment-items/update-article")
                        .content(objectMapper.writeValueAsString(articleRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(articleService, times(1)).updateArticleByRequest(any());
    }

    @Test
    void updateArticleByIdEntityNotFound() throws Exception {
        ArticleUpdateRequest articleRequest = ArticleUpdateRequest.builder()
                .id(1L)
                .name("Test")
                .type(ArticleType.EXPENSE)
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(articleService)
                .updateArticleByRequest(articleRequest);
        mockMvc.perform(put("/payment-items/update-article")
                        .content(objectMapper.writeValueAsString(articleRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(articleService, times(1)).updateArticleByRequest(any());
    }

    @Test
    void deleteArticleById() throws Exception {
        mockMvc.perform(delete("/payment-items/delete-article/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteArticleByIdBadRequest() throws Exception {
        mockMvc.perform(delete("/payment-items/delete-article/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteArticleByIdEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(articleService)
                .deleteArticleById(anyLong());
        mockMvc.perform(delete("/payment-items/delete-article/1"))
                .andExpect(status().isNotFound());
    }
}

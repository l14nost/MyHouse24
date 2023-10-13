package lab.space.my_house_24.model.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24.enums.ArticleType;
import lombok.Builder;

@Builder
public record ArticleSaveRequest(
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}" + " {max}")
        String name,
        @NotNull(message = "{not.blank.message}")
        ArticleType type
) {
}

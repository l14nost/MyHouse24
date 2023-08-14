package lab.space.my_house_24.model.article;

import jakarta.validation.constraints.*;
import lab.space.my_house_24.enums.ArticleType;
import lombok.Builder;

@Builder
public record ArticleUpdateRequest(
        @NotNull(message = "{not.blank.message}")
        @Min(1)
        Long id,
        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[А-ЯЄІЇҐЁA-Z][а-яєіїґёa-z]*$", message = "{pattern.name.message}")
        @Size(max = 100, message = "{size.less.message}" + " {max}")
        String name,
        @NotNull(message = "{not.blank.message}")
        ArticleType type
) {
}

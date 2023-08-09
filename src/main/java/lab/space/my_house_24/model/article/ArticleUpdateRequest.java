package lab.space.my_house_24.model.article;

import lab.space.my_house_24.enums.ArticleType;
import lombok.Builder;

@Builder
public record ArticleUpdateRequest(
        Long id,
        String name,
        ArticleType type
) {
}

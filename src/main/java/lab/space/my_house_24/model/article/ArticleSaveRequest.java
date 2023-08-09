package lab.space.my_house_24.model.article;

import lab.space.my_house_24.enums.ArticleType;
import lombok.Builder;

@Builder
public record ArticleSaveRequest(
        String name,
        ArticleType type
) {
}

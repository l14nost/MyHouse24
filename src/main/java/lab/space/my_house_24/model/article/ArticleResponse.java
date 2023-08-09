package lab.space.my_house_24.model.article;

import lab.space.my_house_24.model.enums_response.EnumResponse;
import lombok.Builder;

@Builder
public record ArticleResponse(
        Long id,
        String name,
        EnumResponse type
) {
}

package lab.space.my_house_24.model.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ArticleResponse(
        Long id,
        String name,
        EnumResponse type
) {
}

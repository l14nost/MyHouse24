package lab.space.my_house_24.model.cash_box;

import jakarta.validation.constraints.Size;
import lab.space.my_house_24.enums.ArticleType;
import lombok.Builder;

@Builder
public record CashBoxRequest(
        Integer pageIndex,

        @Size(max = 15)
        String numberQuery,

        @Size(max = 30)
        String dateQuery,

        Boolean draftQuery,

        Long articleIdQuery,

        @Size(max = 15)
        String bankBookNumberQuery,

        Long ownerIdQuery,

        ArticleType typeQuery
) {
}

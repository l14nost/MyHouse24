package lab.space.my_house_24.model.cash_box;

import lab.space.my_house_24.enums.ArticleType;
import lombok.Builder;

@Builder
public record CashBoxRequest(
        Integer pageIndex,

        String numberQuery,

        String dateQuery,

        Boolean draftQuery,

        Long articleIdQuery,

        String bankBookNumberQuery,

        Long ownerIdQuery,

        ArticleType typeQuery
) {
}

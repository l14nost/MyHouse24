package lab.space.my_house_24.model.cash_box;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.article.ArticleResponse;
import lab.space.my_house_24.model.bankBook.BankBookResponse;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.staff.StaffResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CashBoxResponse(
        Long id,

        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate createAt,

        String number,

        EnumResponse draft,

        BigDecimal price,

        String comment,

        Boolean type,

        BankBookResponse bankBook,

        StaffResponse staff,

        ArticleResponse article
) {
}

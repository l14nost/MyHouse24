package lab.space.my_house_24.model.cash_box;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CashBoxUpdateRequest(
        @NotNull(message = "{not.blank.message}")
        @Min(value = 1, message = "{price.min}" + " {value}")
        Long id,

        @NotBlank(message = "{not.blank.message}")
        @Size(max = 10, message = "{size.less.message}" + " {max}")
        String number,

        @NotNull(message = "{not.blank.message}")
        LocalDate createAt,

        @NotNull(message = "{not.blank.message}")
        Boolean draft,

        @NotNull(message = "{not.blank.message}")
        @Min(value = 1, message = "{price.min}" + " {value}")
        BigDecimal price,

        @NotNull(message = "{not.blank.message}")
        Boolean type,

        @Size(max = 1000, message = "{size.less.message}" + " {max}")
        String comment,

        @Min(value = 1, message = "{price.min}" + " {value}")
        Long bankBookId,

        @NotNull(message = "{not.blank.message}")
        @Min(value = 1, message = "{price.min}" + " {value}")
        Long articleId,

        @NotNull(message = "{not.blank.message}")
        @Min(value = 1, message = "{price.min}" + " {value}")
        Long staffId
) {
}

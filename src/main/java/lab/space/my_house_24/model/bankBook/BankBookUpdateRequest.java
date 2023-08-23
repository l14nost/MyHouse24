package lab.space.my_house_24.model.bankBook;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24.enums.BankBookStatus;
import lombok.Builder;

@Builder
public record BankBookUpdateRequest (

        @NotNull(message = "{not.blank.message}")
        @Min(1)
        Long id,
        @NotNull(message = "{not.blank.message}")
        BankBookStatus status,

        @Min(1)
        Long apartmentId,

        @Pattern(regexp = "^\\d{5}-\\d{5}$")
        @Size(max = 20, message = "{size.less.message}" + " {max}")
        String number

){
}

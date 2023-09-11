package lab.space.my_house_24.model.bill;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lab.space.my_house_24.enums.BillStatus;
import lab.space.my_house_24.model.service_bill.ServiceBillRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BillUpdateRequest(
        @NotNull(message = "{not.blank.message}")
        @Min(value = 1, message = "{price.min}" + " {value}")
        Long id,

        @NotBlank(message = "{not.blank.message}")
        @Size(max = 10, message = "{size.less.message}" + " {max}")
        String number,

        @NotNull(message = "{not.blank.message}")
        LocalDate createAt,

        @NotNull(message = "{not.blank.message}")
        @Min(value = 1, message = "{price.min}" + " {value}")
        Long bankBookId,

        @NotNull(message = "{not.blank.message}")
        Boolean draft,

        @NotNull(message = "{not.blank.message}")
        BillStatus status,

        @Min(value = 1, message = "{price.min}" + " {value}")
        Long rateId,

        @NotNull(message = "{not.blank.message}")
        BigDecimal totalPrice,

        @Valid
        @NotEmpty
        List<ServiceBillRequest> serviceBillList,

        @NotNull(message = "{not.blank.message}")
        LocalDate periodOf,

        @NotNull(message = "{not.blank.message}")
        LocalDate periodTo

) {
}

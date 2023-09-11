package lab.space.my_house_24.model.bill;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.bankBook.BankBookResponse;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.rate.RateResponse;
import lab.space.my_house_24.model.service_bill.ServiceBillResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BillResponse(
        Long id,

        String number,

        EnumResponse status,

        BigDecimal totalPrice,

        Boolean draft,

        BankBookResponse bankBook,

        RateResponse rate,

        List<ServiceBillResponse> services,

        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate createAt,

        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate periodOf,

        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate periodTo,

        String month

) {
}

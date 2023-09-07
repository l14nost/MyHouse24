package lab.space.my_house_24.model.service_bill;

import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.service.ServiceResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServiceBillResponse(
        Long id,

        ServiceResponse service,

        BigDecimal price,

        BigDecimal totalPrice,

        Double count
) {
}

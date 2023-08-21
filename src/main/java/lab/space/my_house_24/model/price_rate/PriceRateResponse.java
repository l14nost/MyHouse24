package lab.space.my_house_24.model.price_rate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.service.ServiceResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PriceRateResponse(

        Long id,

        BigDecimal price,

        ServiceResponse service
) {
}

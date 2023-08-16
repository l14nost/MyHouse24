package lab.space.my_house_24.model.rate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.price_rate.PriceRateResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RateResponse(
        Long id,

        String name,

        String description,

        @JsonFormat(pattern = "dd.MM.yyyy - HH:mm")
        LocalDateTime date,

        List<PriceRateResponse> priceRate
) {
}

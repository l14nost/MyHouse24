package lab.space.my_house_24.model.rate;

import lombok.Builder;

@Builder
public record RateRequest(
        Integer pageIndex
) {
}

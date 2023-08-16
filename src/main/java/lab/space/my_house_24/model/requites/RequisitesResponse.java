package lab.space.my_house_24.model.requites;

import lombok.Builder;

@Builder
public record RequisitesResponse(
        String name,
        String info
) {
}

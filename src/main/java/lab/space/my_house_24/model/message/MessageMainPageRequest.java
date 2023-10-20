package lab.space.my_house_24.model.message;

import jakarta.validation.constraints.Size;

public record MessageMainPageRequest(
        Integer page,
        @Size(max = 250)
        String keyWord
) {
}

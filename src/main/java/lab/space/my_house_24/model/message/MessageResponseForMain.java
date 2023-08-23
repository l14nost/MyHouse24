package lab.space.my_house_24.model.message;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record MessageResponseForMain(
        Long id,
        String recipient,
        String text,
        LocalDateTime sendDate
) {
}

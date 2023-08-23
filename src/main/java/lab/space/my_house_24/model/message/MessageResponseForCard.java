package lab.space.my_house_24.model.message;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record MessageResponseForCard(
        Long id,
        String title,
        String message,
        String staffFullName,
        LocalDateTime sendDate


) {
}

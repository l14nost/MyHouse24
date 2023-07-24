package lab.space.my_house_24.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeterReadingStatus {
    ZERO("Zero"),
    NEW("New"),
    CONSIDERED("Considered"),
    CONSIDERED_PAID("Considered and paid");
    private final String value;
}

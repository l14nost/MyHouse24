package lab.space.my_house_24.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Master {
    ANY_MASTER("Any master"),
    PLUMBER("Plumber"),
    ELECTRIC("Electric");
    private final String value;
}

package lab.space.my_house_24.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    DIRECTOR("Director"),
    MANAGER("Manager"),
    ACCOUNTANT("Accountant"),
    ELECTRIC("Electric"),
    PLUMBER("Plumber");
    private final String value;
}

package lab.space.my_house_24.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MastersApplicationStatus {
    NEW("New"),
    IN_PROCESS("In process"),
    COMPLETED("Completed");
    private final String value;
}

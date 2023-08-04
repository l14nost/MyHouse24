package lab.space.my_house_24.model.role;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PageResponse(
        @NotNull(message = "{not.blank.message}")
        Boolean statistics,
        @NotNull(message = "{not.blank.message}")
        Boolean cashBox,
        @NotNull(message = "{not.blank.message}")
        Boolean bills,
        @NotNull(message = "{not.blank.message}")
        Boolean bankBooks,
        @NotNull(message = "{not.blank.message}")
        Boolean apartments,
        @NotNull(message = "{not.blank.message}")
        Boolean apartmentsOwner,
        @NotNull(message = "{not.blank.message}")
        Boolean houses,
        @NotNull(message = "{not.blank.message}")
        Boolean messages,
        @NotNull(message = "{not.blank.message}")
        Boolean mastersApplications,
        @NotNull(message = "{not.blank.message}")
        Boolean meterReading,
        @NotNull(message = "{not.blank.message}")
        Boolean settingsPage,
        @NotNull(message = "{not.blank.message}")
        Boolean services,
        @NotNull(message = "{not.blank.message}")
        Boolean rates,
        @NotNull(message = "{not.blank.message}")
        Boolean staff,
        @NotNull(message = "{not.blank.message}")
        Boolean roles,
        @NotNull(message = "{not.blank.message}")
        Boolean requisites
) {
}

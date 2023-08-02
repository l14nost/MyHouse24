package lab.space.my_house_24.model.role;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PageResponse(
        @NotNull
        Boolean statistics,
        @NotNull
        Boolean cashBox,
        @NotNull
        Boolean bills,
        @NotNull
        Boolean bankBooks,
        @NotNull
        Boolean apartments,
        @NotNull
        Boolean apartmentsOwner,
        @NotNull
        Boolean houses,
        @NotNull
        Boolean messages,
        @NotNull
        Boolean mastersApplications,
        @NotNull
        Boolean meterReading,
        @NotNull
        Boolean settingsPage,
        @NotNull
        Boolean services,
        @NotNull
        Boolean rates,
        @NotNull
        Boolean staff,
        @NotNull
        Boolean roles,
        @NotNull
        Boolean requisites
) {
}

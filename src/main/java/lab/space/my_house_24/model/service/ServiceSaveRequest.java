package lab.space.my_house_24.model.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record ServiceSaveRequest(
        @Valid
        @NotEmpty
        List<ServiceRequest> serviceRequestList
) {
}

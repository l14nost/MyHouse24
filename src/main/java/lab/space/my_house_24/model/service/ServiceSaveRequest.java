package lab.space.my_house_24.model.service;

import lombok.Builder;

import java.util.List;

@Builder
public record ServiceSaveRequest(
        List<ServiceRequest> serviceRequestList
) {
}

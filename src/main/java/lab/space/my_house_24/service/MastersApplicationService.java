package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.MastersApplication;
import lab.space.my_house_24.enums.MastersApplicationStatus;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.masters_application.MastersApplicationRequest;
import lab.space.my_house_24.model.masters_application.MastersApplicationResponse;
import lab.space.my_house_24.model.masters_application.MastersApplicationSaveRequest;
import lab.space.my_house_24.model.masters_application.MastersApplicationUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MastersApplicationService {
    MastersApplication getMastersApplicationById(Long id);

    MastersApplicationResponse getMastersApplicationResponseById(Long id);

    void updateMastersApplicationByRequest(MastersApplicationUpdateRequest request);

    void saveMastersApplicationByRequest(MastersApplicationSaveRequest request);

    void saveMastersApplication(MastersApplication mastersApplication);

    void deleteMastersApplicationById(Long id);

    Page<MastersApplicationResponse> getAllMastersApplication(MastersApplicationRequest request);

    List<EnumResponse> getAllStatus();

    List<EnumResponse> getAllTypeMaster();

    Long countByStatus(MastersApplicationStatus mastersApplicationStatus);
}

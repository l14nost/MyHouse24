package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.MastersApplication;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.masters_application.MastersApplicationRequest;
import lab.space.my_house_24.model.masters_application.MastersApplicationResponse;
import lab.space.my_house_24.model.masters_application.MastersApplicationSaveRequest;
import lab.space.my_house_24.model.masters_application.MastersApplicationUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MastersApplicationService {
    MastersApplication getMastersApplicationById(Long id);

    ResponseEntity<?> getMastersApplicationResponseById(Long id);

    ResponseEntity<?> updateMastersApplicationByRequest(MastersApplicationUpdateRequest request);

    ResponseEntity<?> saveMastersApplicationByRequest(MastersApplicationSaveRequest request);

    void saveMastersApplication(MastersApplication mastersApplication);

    ResponseEntity<?> deleteMastersApplicationById(Long id);

    Page<MastersApplicationResponse> getAllMastersApplication(MastersApplicationRequest request);

    List<EnumResponse> getAllStatus();

    List<EnumResponse> getAllTypeMaster();
}

package lab.space.my_house_24.service;


import lab.space.my_house_24.entity.Unit;
import lab.space.my_house_24.model.unit.UnitResponse;
import lab.space.my_house_24.model.unit.UnitSaveRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UnitService {

    Unit getUnitById(Long id);

    List<UnitResponse> getAllUnitDto();

    void saveUnit(Unit unit);

    ResponseEntity<?> saveUnitByRequest(UnitSaveRequest request);

    ResponseEntity<?> deleteUnitById(Long id);

}

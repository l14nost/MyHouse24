package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Unit;
import lab.space.my_house_24.mapper.UnitMapper;
import lab.space.my_house_24.model.unit.UnitRequest;
import lab.space.my_house_24.model.unit.UnitResponse;
import lab.space.my_house_24.model.unit.UnitSaveRequest;
import lab.space.my_house_24.repository.UnitRepository;
import lab.space.my_house_24.service.UnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    @Override
    public Unit getUnitById(Long id) throws EntityNotFoundException {
        log.info("Try to search Unit by id " + id);
        return unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found by id " + id));
    }

    @Override
    public List<UnitResponse> getAllUnitDto() {
        log.info("Try to get All Unit and convert in Dto");
        return unitRepository.findAll(Sort.by(Sort.Direction.ASC,"id"))
                .stream()
                .map(UnitMapper::toSimpleDto).collect(Collectors.toList());
    }

    @Override
    public void saveUnit(Unit unit) {
        log.info("Try to save Unit");
        unitRepository.save(unit);
        log.info("Success save Unit");
    }

    @Override
    public ResponseEntity<?> saveUnitByRequest(UnitSaveRequest request) {
        try {
            log.info("Try to save Unit by Request");
            for (UnitRequest unitRequest : request.unitRequestList()) {
                if (nonNull(unitRequest.id())) {
                    saveUnit(
                            UnitMapper.toUnitUpdate(
                                    unitRequest,
                                    getUnitById(unitRequest.id())
                            )

                    );
                } else {
                    saveUnit(UnitMapper.toUnitSave(unitRequest));
                }
            }
            log.info("Success save Unit by Request");
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e){
            log.error("Error save Unit by Request");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteUnitById(Long id) {
        try {
            log.info("Try to delete Unit");
            unitRepository.delete(getUnitById(id));
            log.info("Success delete Unit");
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Error delete Unit");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}

package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Unit;
import lab.space.my_house_24.mapper.UnitMapper;
import lab.space.my_house_24.model.unit.UnitRequest;
import lab.space.my_house_24.model.unit.UnitResponse;
import lab.space.my_house_24.model.unit.UnitSaveRequest;
import lab.space.my_house_24.repository.UnitRepository;
import lab.space.my_house_24.service.UnitService;
import lab.space.my_house_24.specification.UnitSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final UnitSpecification unitSpecification;
    private final MessageSource message;

    @Override
    public Unit getUnitById(Long id) throws EntityNotFoundException {
        log.info("Try to search Unit by id " + id);
        return unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found by id " + id));
    }

    @Override
    public Page<UnitResponse> getAllUnitDtoForSelect(Integer pageIndex, String search) {
        log.info("Try to get All Unit and convert in Dto");
        return unitRepository.findAll(unitSpecification.getUnit(search), PageRequest.of(pageIndex, 10))
                .map(UnitMapper::toSimpleDto);
    }

    @Override
    public List<UnitResponse> getAllUnitDto() {
        return unitRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
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
    public void saveUnitByRequest(UnitSaveRequest request) throws EntityNotFoundException {
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
    }

    @Override
    public void deleteUnitById(Long id) throws EntityNotFoundException, IllegalArgumentException {
        log.info("Try to delete Unit");
        Unit unit = getUnitById(id);
        if (unit.getServiceList().isEmpty()) {
            unitRepository.delete(unit);
        } else {
            log.warn("Warning delete Unit");
            throw new IllegalArgumentException(message.getMessage("unit.delete.error", null, LocaleContextHolder.getLocale()));
        }
        log.info("Success delete Unit");
    }
}

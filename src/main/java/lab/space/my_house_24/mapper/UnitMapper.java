package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Unit;
import lab.space.my_house_24.model.unit.UnitRequest;
import lab.space.my_house_24.model.unit.UnitResponse;

public interface UnitMapper {

    static UnitResponse toSimpleDto(Unit unit) {
        return UnitResponse.builder()
                .id(unit.getId())
                .name(unit.getName())
                .build();
    }

    static UnitResponse toDto(Unit unit) {
        return UnitResponse.builder()
                .name(unit.getName())
                .build();
    }

    static Unit toUnitUpdate(UnitRequest unitRequest, Unit unit) {
        return unit.setName(unitRequest.name());
    }

    static Unit toUnitSave(UnitRequest unitRequest) {
        return Unit.builder()
                .name(unitRequest.name())
                .build();
    }

}

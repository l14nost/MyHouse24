package lab.space.my_house_24.mapper;

import lab.space.my_house_24.model.enums_response.EnumResponse;

public interface EnumMapper {
    static EnumResponse toSimpleDto(String name, String value){
        return EnumResponse.builder()
                .name(name)
                .value(value)
                .build();
    }
}

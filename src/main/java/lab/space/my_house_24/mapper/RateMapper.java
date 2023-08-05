package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Floor;
import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.model.floor.FloorResponseForTable;
import lab.space.my_house_24.model.rate.RateResponseForTable;


public class RateMapper {
    public  static RateResponseForTable entityToDtoForTable(Rate rate){
        return RateResponseForTable.builder().name(rate.getName()).id(rate.getId()).build();
    }
}

package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.model.house.HouseResponseForTable;


public class HouseMapper{
    public  static HouseResponseForTable entityToDtoForTable(House house){
        return HouseResponseForTable.builder().name(house.getName()).id(house.getId()).build();
    }
}

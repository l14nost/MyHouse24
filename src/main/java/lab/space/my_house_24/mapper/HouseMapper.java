package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.model.house.HouseResponseForCard;
import lab.space.my_house_24.model.house.HouseResponseForMain;
import lab.space.my_house_24.model.house.HouseResponseForTable;


public class HouseMapper{
    public  static HouseResponseForTable entityToDtoForTable(House house){
        return HouseResponseForTable.builder().name(house.getName()).id(house.getId()).build();
    }

    public  static HouseResponseForMain entityToDtoForMain(House house){
        return HouseResponseForMain.builder().name(house.getName()).id(house.getId()).address(house.getAddress()).build();
    }

    public static HouseResponseForCard entityToDtoForCard(House house){
        return HouseResponseForCard.builder()
                .section(house.getSectionList().size())
                .users(house.getStaffList().stream().map(StaffMapper::entityToDtoForHouseCard).toList())
                .floor(house.getFloorList().size())
                .name(house.getName())
                .image1(house.getImage1())
                .image2(house.getImage2())
                .image3(house.getImage3())
                .image4(house.getImage4())
                .image5(house.getImage5())
                .address(house.getAddress())
                .build();
    }
}

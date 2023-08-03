package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.model.house.HouseResponseForUserPage;
import lab.space.my_house_24.model.user.UserResponse;

import java.util.ArrayList;
import java.util.List;


public class HouseMapper {

    public  static HouseResponseForUserPage entityToDtoForUserPage(House house){
        return HouseResponseForUserPage.builder().name(house.getName()).build();
    }
}

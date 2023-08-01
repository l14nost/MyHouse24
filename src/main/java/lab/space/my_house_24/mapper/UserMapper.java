package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.model.apartment.ApartmentResponseForUserTable;
import lab.space.my_house_24.model.house.HouseResponseForUserTable;
import lab.space.my_house_24.model.user.UserCardResponse;
import lab.space.my_house_24.model.user.UserResponse;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


public class UserMapper {

    public  static UserResponse entityToMainPageDto(User user){
        List<ApartmentResponseForUserTable> apartments = new ArrayList<>();
        List<HouseResponseForUserTable> houses = new ArrayList<>();
        if (user.getApartmentList()!=null){
            for (Apartment apartment : user.getApartmentList() ){
                apartments.add(ApartmentResponseForUserTable.builder().id(apartment.getId()).number(apartment.getNumber()).build());
                houses.add(HouseResponseForUserTable.builder().name(apartment.getHouse().getName()).id(apartment.getHouse().getId()).build());
            }
        }
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .duty(user.getDuty())
                .fullName(user.getLastname() + " " + user.getFirstname() + " " + user.getSurname())
                .status(user.getUserStatus())
                .number(user.getNumber())
                .filename(user.getFilename())
                .apartments(apartments)
                .houses(houses)
                .addDate(user.getAddDate().atZone(ZoneId.systemDefault()).toLocalDate())
                .build();

        return userResponse;
    }


    public  static UserCardResponse entityToCardDto(User user){
        UserCardResponse userResponse = UserCardResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .surname(user.getSurname())
                .status(user.getUserStatus())
                .number(user.getNumber())
                .filename(user.getFilename())
                .date(user.getDate().atZone(ZoneId.systemDefault()).toLocalDate())
                .viber(user.getViber())
                .telegram(user.getTelegram())
                .build();

        return userResponse;
    }
}

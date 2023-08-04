package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.Section;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.model.apartment.ApartmentResponseForTable;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.user.UserCardResponse;
import lab.space.my_house_24.model.user.UserEditResponse;
import lab.space.my_house_24.model.user.UserResponse;
import lab.space.my_house_24.model.user.UserResponseForTable;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


public class UserMapper {

    public  static UserResponse entityToMainPageDto(User user){
        List<ApartmentResponseForTable> apartments = new ArrayList<>();
        List<HouseResponseForTable> houses = new ArrayList<>();
        if (user.getApartmentList()!=null){
            for (Apartment apartment : user.getApartmentList() ){
                apartments.add(ApartmentResponseForTable.builder().id(apartment.getId()).number(apartment.getNumber()).build());
                houses.add(HouseResponseForTable.builder().name(apartment.getHouse().getName()).id(apartment.getHouse().getId()).build());
            }
        }
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .duty(user.getDuty())
                .fullName(user.getLastname() + " " + user.getFirstname() + " " + user.getSurname())
                .status(user.getUserStatus().getUserStatus(LocaleContextHolder.getLocale()))
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
                .status(user.getUserStatus().getUserStatus(LocaleContextHolder.getLocale()))
                .number(user.getNumber())
                .filename(user.getFilename())
                .date(user.getDate().atZone(ZoneId.systemDefault()).toLocalDate())
                .viber(user.getViber())
                .telegram(user.getTelegram())
                .build();

        return userResponse;
    }

    public  static UserEditResponse entityToEditDto(User user){
        UserEditResponse userResponse = UserEditResponse.builder()
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
                .notes(user.getNotes())
                .build();

        return userResponse;
    }



    public  static UserResponseForTable entityToDtoForTable(User user){
        return UserResponseForTable.builder().name(user.getLastname()+" "+user.getFirstname()+" "+ user.getSurname()).id(user.getId()).build();
    }
}

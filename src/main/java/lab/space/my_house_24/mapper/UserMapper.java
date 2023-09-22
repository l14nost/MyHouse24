package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.model.apartment.ApartmentResponseForTable;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.user.*;
import org.springframework.context.i18n.LocaleContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class UserMapper {

    public  static UserResponse entityToMainPageDto(User user){
        List<ApartmentResponseForTable> apartments = new ArrayList<>();
        List<HouseResponseForTable> houses = new ArrayList<>();
        Boolean duty = false;
        if (user.getApartmentList()!=null){
            for (Apartment apartment : user.getApartmentList() ){
                if (apartment.getBankBook()!=null){
                    if (apartment.getBankBook().getTotalPrice().compareTo(BigDecimal.ZERO)<0){
                        duty = true;
                    }
                }
                apartments.add(ApartmentMapper.entityToDtoForTable(apartment));
                houses.add(HouseMapper.entityToDtoForTable(apartment.getHouse()));
            }
        }

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .duty(duty)
                .fullName(user.getLastname() + " " + user.getFirstname() + " " + user.getSurname())
                .status(EnumResponse.builder().value(user.getUserStatus().getUserStatus(LocaleContextHolder.getLocale())).name(user.getUserStatus().name()).build())
                .number(user.getNumber())
                .filename(user.getFilename())
                .apartments(apartments)
                .houses(houses)
                .addDate(user.getAddDate().atZone(ZoneId.systemDefault()).toLocalDate())
                .build();

        return userResponse;
    }


    public  static UserCardResponse entityToCardDto(User user){
        LocalDate localDate = null;
        if (user.getDate()!=null){
            localDate = user.getDate().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        UserCardResponse userResponse = UserCardResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .surname(user.getSurname())
                .status(EnumMapper.toSimpleDto(user.getUserStatus().name(), user.getUserStatus().getUserStatus(LocaleContextHolder.getLocale())))
                .number(user.getNumber())
                .filename(user.getFilename())
                .date(localDate)
                .viber(user.getViber())
                .telegram(user.getTelegram())
                .notes(user.getNotes())
                .build();
        return userResponse;
    }

    public  static UserEditResponse entityToEditDto(User user){
        LocalDate localDate = null;
        if (user.getDate()!=null){
            localDate = user.getDate().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        UserEditResponse userResponse = UserEditResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .surname(user.getSurname())
                .status(user.getUserStatus())
                .number(user.getNumber())
                .filename(user.getFilename())
                .date(localDate)
                .viber(user.getViber())
                .telegram(user.getTelegram())
                .notes(user.getNotes())
                .build();

        return userResponse;
    }



    public  static UserResponseForTable entityToDtoForTable(User user){
        return UserResponseForTable.builder().name(user.getLastname()+" "+user.getFirstname()+" "+ user.getSurname()).id(user.getId()).build();
    }

    public static UserResponseForMastersApplication entityToResponseForMastersApplication(User user){
        return UserResponseForMastersApplication.builder()
                .id(user.getId())
                .fullName(user.getLastname() + " " + user.getFirstname() + " " + user.getSurname())
                .phone(user.getNumber())
                .build();
    }

    public static UserResponseForHeader entityToHeaderDto(User user) {
        return UserResponseForHeader.builder()
                .id(user.getId())
                .fullName(user.getLastname()+" "+user.getFirstname())
                .build();
    }
}

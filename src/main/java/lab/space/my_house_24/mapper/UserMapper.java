package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.model.user.UserResponse;

import java.util.ArrayList;
import java.util.List;


public class UserMapper {

    public  static UserResponse entityToDto(User user){
        List<Long> apartmentIds = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        List<Long> houseIds = new ArrayList<>();
        List<String> names = new ArrayList<>();
        if (user.getApartmentList()!=null){
            for (Apartment apartment : user.getApartmentList() ){
                apartmentIds.add(apartment.getId());
                numbers.add(apartment.getNumber());
                houseIds.add(apartment.getHouse().getId());
                names.add(apartment.getHouse().getName());
            }
        }
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .duty(user.isDuty())
                .fullName(user.getLastname() + " " + user.getFirstname() + " " + user.getSurname())
                .status(user.getStatus())
                .number(user.getNumber())
                .filename(user.getFilename())
                .apartmentIds(apartmentIds)
                .apartmentNumbers(numbers)
                .houseIds(houseIds)
                .houseNames(names)
                .build();

        return userResponse;
    }
}

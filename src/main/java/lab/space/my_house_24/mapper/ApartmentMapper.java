package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.model.apartment.ApartmentResponse;

import java.math.BigDecimal;

public class ApartmentMapper {

    public  static ApartmentResponse entityToDtoForMainPage(Apartment apartment){
//        BigDecimal balance = BigDecimal.ZERO;
//        for (int i = 0; i< apartment.getBill().size(); i++){
//            if (apartment.getBill().get(i).getDraft()) {
//                balance = balance.add(apartment.getBill().get(i).getTotalPrice());
//            }
//            else {
//                balance = balance.subtract(apartment.getBill().get(i).getTotalPrice());
//            }
//        }

        return ApartmentResponse.builder()
                .id(apartment.getId())
                .number(apartment.getNumber())
                .house(apartment.getHouse().getName())
                .owner(apartment.getUser().getLastname()+" "+apartment.getUser().getFirstname()+" "+apartment.getUser().getSurname())
                .floor(apartment.getFloor().getName())
                .section(apartment.getSection().getName())
                .balance(BigDecimal.ZERO)
                .build();
    }
}

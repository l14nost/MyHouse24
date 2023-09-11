package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.model.apartment.*;

import java.math.BigDecimal;

public class ApartmentMapper {
    public static ApartmentResponseForTable entityToDtoForTable(Apartment apartment) {
        return ApartmentResponseForTable.builder().number(apartment.getNumber()).id(apartment.getId()).build();
    }

    public static ApartmentResponse entityToDtoForMainPage(Apartment apartment) {
        BigDecimal balance = BigDecimal.ZERO;
       if (apartment.getBankBook()!=null){
           balance = apartment.getBankBook().getTotalPrice();
       }

        return ApartmentResponse.builder()
                .id(apartment.getId())
                .number(apartment.getNumber())
                .house(apartment.getHouse().getName())
                .owner(apartment.getUser().getLastname() + " " + apartment.getUser().getFirstname() + " " + apartment.getUser().getSurname())
                .floor(apartment.getFloor().getName())
                .section(apartment.getSection().getName())
                .balance(balance)
                .build();
    }

    public static ApartmentResponseForCard entityToDtoForCard(Apartment apartment) {
        return ApartmentResponseForCard.builder()
                .id(apartment.getId())
                .title("№" + apartment.getNumber() + "," + apartment.getHouse().getName())
                .area(apartment.getArea())
                .bankBook(BankBookMapper.entityToDtoForTable(apartment.getBankBook()))
                .rate(RateMapper.entityToDtoForTable(apartment.getRate()))
                .section(apartment.getSection().getName())
                .floor(apartment.getFloor().getName())
                .house(HouseMapper.entityToDtoForTable(apartment.getHouse()))
                .owner(UserMapper.entityToDtoForTable(apartment.getUser()))
                .number(apartment.getNumber())
                .build();
    }

    public static ApartmentResponseForEdit entityToDtoForEdit(Apartment apartment){
        return ApartmentResponseForEdit.builder().number(apartment.getNumber())
                .section(SectionMapper.entityToDtoForTable(apartment.getSection()))
                .rate(RateMapper.entityToDtoForTable(apartment.getRate()))
                .bankBook(BankBookMapper.entityToDtoForTable(apartment.getBankBook()))
                .house(HouseMapper.entityToDtoForTable(apartment.getHouse()))
                .floor(FloorMapper.entityToDtoForTable(apartment.getFloor()))
                .area(apartment.getArea())
                .owner(UserMapper.entityToDtoForTable(apartment.getUser())).build();

    }

    public static ApartmentResponseForMastersApplication entityToResponseForMastersApplication(Apartment apartment) {
        return ApartmentResponseForMastersApplication.builder()
                .id(apartment.getId())
                .name(apartment.getNumber() + ", " + apartment.getHouse().getName())
                .floor(apartment.getFloor().getName())
                .houseId(apartment.getHouse().getId())
                .house(apartment.getHouse().getName())
                .section(apartment.getSection().getName())
                .owner(UserMapper.entityToResponseForMastersApplication(apartment.getUser()))
                .build();

    }

    public static ApartmentResponseForMastersApplication entityToResponseForMastersApplicationTable(Apartment apartment) {
        return ApartmentResponseForMastersApplication.builder()
                .id(apartment.getId())
                .name(apartment.getNumber() + ", " + apartment.getHouse().getName())
                .owner(UserMapper.entityToResponseForMastersApplication(apartment.getUser()))
                .build();

    }

    public static ApartmentResponseForBankBook entityToResponseForBankBook(Apartment apartment) {
        return ApartmentResponseForBankBook.builder()
                .id(apartment.getId())
                .number(String.valueOf(apartment.getNumber()))
                .house(HouseMapper.entityToDtoForTable(apartment.getHouse()))
                .section(SectionMapper.entityToDtoForTable(apartment.getSection()))
                .owner(UserMapper.entityToResponseForMastersApplication(apartment.getUser()))
                .build();

    }

    public static ApartmentResponseForBill entityToResponseForBill(Apartment apartment) {
        return ApartmentResponseForBill.builder()
                .id(apartment.getId())
                .number(String.valueOf(apartment.getNumber()))
                .house(HouseMapper.entityToDtoForTable(apartment.getHouse()))
                .section(SectionMapper.entityToDtoForTable(apartment.getSection()))
                .owner(UserMapper.entityToResponseForMastersApplication(apartment.getUser()))
                .bankBook(BankBookMapper.toSimpleBankBookResponse(apartment.getBankBook()))
                .build();

    }
}

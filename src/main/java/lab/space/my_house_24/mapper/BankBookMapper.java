package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.model.bankBook.*;
import org.springframework.context.i18n.LocaleContextHolder;

import static java.util.Objects.nonNull;


public interface BankBookMapper {
    static BankBookResponseForTable entityToDtoForTable(BankBook bankBook) {
        return BankBookResponseForTable.builder().number(bankBook.getNumber()).id(bankBook.getId()).build();
    }

    static BankBookResponse toSimpleBankBookResponse(BankBook bankBook) {
        return BankBookResponse.builder()
                .id(bankBook.getId())
                .number(bankBook.getNumber())
                .build();
    }

    static BankBookResponse toBankBookResponse(BankBook bankBook) {
        return BankBookResponse.builder()
                .id(bankBook.getId())
                .number(bankBook.getNumber())
                .status(EnumMapper.toSimpleDto(
                        bankBook.getBankBookStatus().name(),
                        bankBook.getBankBookStatus().getBankBookStatus(LocaleContextHolder.getLocale()))
                )
                .apartment(nonNull(bankBook.getApartment()) ? ApartmentMapper.entityToResponseForBankBook(bankBook.getApartment()) : null)
                .build();
    }

    static BankBookResponseForCashBox toBankBookResponseForCashBox(BankBook bankBook) {
        return BankBookResponseForCashBox.builder()
                .id(bankBook.getId())
                .number(bankBook.getNumber())
                .user(UserMapper.entityToResponseForMastersApplication(bankBook.getApartment().getUser()))
                .build();
    }

    static BankBook toBankBook(BankBookSaveRequest request, String number, Apartment apartment) {
        return BankBook.builder()
                .number(number)
                .bankBookStatus(request.status())
                .apartment(apartment)
                .build();
    }

    static BankBook toBankBook(BankBookUpdateRequest request, String number, BankBook bankBook, Apartment apartment) {
        return bankBook
                .setNumber(number)
                .setBankBookStatus(request.status())
                .setApartment(apartment);
    }
}

package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.model.bankBook.*;
import org.springframework.context.i18n.LocaleContextHolder;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;


public interface BankBookMapper {
    static BankBookResponseForTable entityToDtoForTable(BankBook bankBook) {
        if (bankBook!=null) {
            return BankBookResponseForTable.builder().number(bankBook.getNumber()).id(bankBook.getId()).build();
        }
        else return BankBookResponseForTable.builder().build();
    }
    static BankBookResponseForApartmentCard entityToDtoForApartmentCard(BankBook bankBook) {

        if (bankBook!=null) {
            Long idCashBox = 0L;
            if (!bankBook.getCashBoxes().isEmpty()){
                idCashBox = bankBook.getCashBoxes().get(bankBook.getCashBoxes().size()-1).getId();
            }
            return BankBookResponseForApartmentCard.builder().number(bankBook.getNumber()).id(bankBook.getId()).idCashBox(idCashBox).build();
        }
        else return BankBookResponseForApartmentCard.builder().build();
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
                .totalPrice(bankBook.getTotalPrice())
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
                .totalPrice(BigDecimal.ZERO)
                .build();
    }

    static BankBook toBankBook(BankBookUpdateRequest request, String number, BankBook bankBook, Apartment apartment) {
        return bankBook
                .setNumber(number)
                .setBankBookStatus(request.status())
                .setApartment(apartment);
    }
}

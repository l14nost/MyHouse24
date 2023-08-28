package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.model.bankBook.BankBookResponseForTable;

import java.util.List;
import java.util.Optional;

public interface BankBookService {

    BankBook findById(Long id);

    Optional<BankBook> findByNumber(String number);
    List<BankBookResponseForTable> bankBookListForTable();

    void setBankBookApartmentIdNull(Long id);

    void update(Long id,BankBook bankBook);

    Long count();
}

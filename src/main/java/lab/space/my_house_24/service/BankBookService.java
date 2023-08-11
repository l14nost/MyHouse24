package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.model.bankBook.BankBookResponseForTable;

import java.util.List;

public interface BankBookService {

    BankBook findById(Long id);
    List<BankBookResponseForTable> bankBookListForTable();

    void setBankBookApartmentIdNull(Long id);
}

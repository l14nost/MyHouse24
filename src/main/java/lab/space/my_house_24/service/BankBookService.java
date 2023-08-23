package lab.space.my_house_24.service;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.model.bankBook.BankBookResponse;
import lab.space.my_house_24.model.bankBook.BankBookResponseForTable;
import lab.space.my_house_24.model.bankBook.BankBookSaveRequest;
import lab.space.my_house_24.model.bankBook.BankBookUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;

import java.util.List;
import java.util.Optional;

public interface BankBookService {

    BankBook getBankBookById(Long id) throws EntityNotFoundException;

    BankBookResponse getBankBookResponseById(Long id) throws EntityNotFoundException;

    Optional<BankBook> findByNumber(String number);

    List<BankBookResponseForTable> bankBookListForTable();

    List<EnumResponse> getAllBankBookStatus();

    void updateBankBookByRequest(BankBookUpdateRequest request) throws EntityNotFoundException;

    void saveBankBookByRequest(BankBookSaveRequest request) throws EntityNotFoundException;

    void saveBankBook(BankBook bankBook);

    void deleteBankBookById(Long id) throws EntityNotFoundException;
}

package lab.space.my_house_24.service;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.entity.Bill;
import lab.space.my_house_24.model.bankBook.*;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BankBookService {

    BankBook getBankBookById(Long id) throws EntityNotFoundException;

    BankBookResponse getBankBookResponseById(Long id) throws EntityNotFoundException;

    Optional<BankBook> findByNumber(String number);

    List<BankBookResponseForTable> bankBookListForTable();

    Page<BankBookResponseForCashBox> getBankBookListForCashBoxByUserId(Integer pageIndex, Long userId, String bankBookNumber);

    Page<BankBookResponse> getAllBankBookResponse(BankBookRequest request);

    List<BankBook> getAllBankBookIsActive();

    List<EnumResponse> getAllBalanceStatus();

    List<EnumResponse> getAllBankBookStatus();

    void updateBankBookByRequest(BankBookUpdateRequest request) throws EntityNotFoundException;

    void saveBankBookByRequest(BankBookSaveRequest request) throws EntityNotFoundException;

    void saveBankBook(BankBook bankBook);

    InputStreamResource getExcel(BankBookRequest request) throws IOException;

    void calculateBankBook(Long id, BigDecimal price, BigDecimal historyPrice, Bill bill);

    void calculateBankBook(Long id, BigDecimal price, Boolean type, Bill bill);

    void deleteBankBookById(Long id) throws EntityNotFoundException;

    Long count();
}

package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.enums.BalanceStatus;
import lab.space.my_house_24.enums.BankBookStatus;
import lab.space.my_house_24.mapper.BankBookMapper;
import lab.space.my_house_24.mapper.EnumMapper;
import lab.space.my_house_24.model.bankBook.*;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.repository.ApartmentRepository;
import lab.space.my_house_24.repository.BankBookRepository;
import lab.space.my_house_24.service.BankBookService;
import lab.space.my_house_24.specification.BankBookSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankBookServiceImpl implements BankBookService {

    private final BankBookRepository bankBookRepository;
    private final ApartmentRepository apartmentRepository;
    private final BankBookSpecification bankBookSpecification;

    @Override
    public BankBook getBankBookById(Long id) throws EntityNotFoundException {
        log.info("Try to find BankBook");
        return bankBookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bank book not found by id " + id));
    }

    @Override
    public BankBookResponse getBankBookResponseById(Long id) throws EntityNotFoundException {
        log.info("Try to find BankBook adn convert to Response");
        return BankBookMapper.toBankBookResponse(getBankBookById(id));
    }

    @Override
    public Optional<BankBook> findByNumber(String number) {
        return bankBookRepository.findBankBookByNumber(number);
    }

    @Override
    public List<BankBookResponseForTable> bankBookListForTable() {
        return bankBookRepository.findAllByApartmentIsNull().stream().map(BankBookMapper::entityToDtoForTable).toList();
    }

    @Override
    public List<BankBookResponseForCashBox> getBankBookListForCashBoxByUserId(Long userId) {
        return bankBookRepository.findAll(bankBookSpecification.getBankBookByUser(userId)).stream().map(BankBookMapper::toBankBookResponseForCashBox).toList();
    }

    @Override
    public Page<BankBookResponse> getAllBankBookResponse(BankBookRequest request) {
        log.info("Try to get all BankBookResponse by Request");
        final int DEFAULT_PAGE_SIZE = 10;
        return bankBookRepository.findAll(
                bankBookSpecification.getBankBookByRequest(request),
                PageRequest.of(request.pageIndex(), DEFAULT_PAGE_SIZE)).map(BankBookMapper::toBankBookResponse);
    }

    @Override
    public List<EnumResponse> getAllBalanceStatus() {
        log.info("Try to get all BalanceStatus");
        return Arrays.stream(BalanceStatus.values())
                .map(status -> EnumMapper.toSimpleDto(
                        status.name(),
                        status.getBalanceStatus(LocaleContextHolder.getLocale())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<EnumResponse> getAllBankBookStatus() {
        log.info("Try to get all BankBookStatus");
        return Arrays.stream(BankBookStatus.values())
                .map(status -> EnumMapper.toSimpleDto(
                        status.name(),
                        status.getBankBookStatus(LocaleContextHolder.getLocale())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void updateBankBookByRequest(BankBookUpdateRequest request) throws EntityNotFoundException {
        log.info("Try to Update BankBook by Request");
        if (nonNull(request.apartmentId())) {
            saveBankBook(BankBookMapper.toBankBook(request,
                    ((nonNull(request.number()) ? request.number() : generateNumber())),
                    getBankBookById(request.id()),
                    getApartmentById(request.apartmentId())));
        } else {
            saveBankBook(BankBookMapper.toBankBook(request,
                    ((nonNull(request.number()) ? request.number() : generateNumber())),
                    getBankBookById(request.id()),
                    null));
        }
        log.info("Success Update BankBook by Request");
    }

    @Override
    public void saveBankBookByRequest(BankBookSaveRequest request) throws EntityNotFoundException {
        log.info("Try to Save BankBook by Request");
        if (nonNull(request.apartmentId())) {
            saveBankBook(BankBookMapper.toBankBook(request,
                    ((nonNull(request.number()) ? request.number() : generateNumber())),
                    getApartmentById(request.apartmentId())));
        } else {
            saveBankBook(BankBookMapper.toBankBook(request,
                    ((nonNull(request.number()) ? request.number() : generateNumber())), null));
        }
        log.info("Success Save BankBook by Request");
    }

    @Override
    public void saveBankBook(BankBook bankBook) {
        log.info("Try to Save BankBook");
        bankBookRepository.save(bankBook);
        log.info("Success Save BankBook");
    }

    @Override
    public void deleteBankBookById(Long id) throws EntityNotFoundException {
        log.info("Try to Delete BankBook");
        bankBookRepository.delete(getBankBookById(id));
        log.info("Success Delete BankBook");
    }

    private Apartment getApartmentById(Long id) throws EntityNotFoundException {
        log.info("Try to find Apartment");
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Apartment not found by id " + id));
    }

    private String generateNumber() {
        log.info("Try to generate Number");
        List<BankBook> bankBookList = bankBookRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        if (bankBookList.isEmpty()){
            log.warn("Bank books not found");
            return String.format("%05d-%05d", 0, 1);
        }
        BankBook bankBook = bankBookList.get(0);

        String number = bankBook.getNumber();
        String[] parts;

        int firstPart;
        int secondPart;

        do {
            parts = number.split("-");
            firstPart = Integer.parseInt(parts[0]);
            secondPart = Integer.parseInt(parts[1]);
            secondPart++;
            if (secondPart > 99999) {
                firstPart++;
                secondPart = 1;
            }
            number = String.format("%05d-%05d", firstPart, secondPart);
        } while (bankBookRepository.existsByNumber(number));
        log.info("Success generate Number");
        return number;
    }

    @Override
    public Long count() {
        return bankBookRepository.count();
    }
}

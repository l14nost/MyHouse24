package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.enums.BalanceStatus;
import lab.space.my_house_24.enums.BankBookStatus;
import lab.space.my_house_24.enums.BillStatus;
import lab.space.my_house_24.mapper.BankBookMapper;
import lab.space.my_house_24.mapper.EnumMapper;
import lab.space.my_house_24.model.bankBook.*;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.repository.ApartmentRepository;
import lab.space.my_house_24.repository.BankBookRepository;
import lab.space.my_house_24.repository.BillRepository;
import lab.space.my_house_24.repository.CashBoxRepository;
import lab.space.my_house_24.service.BankBookService;
import lab.space.my_house_24.service.ExcelService;
import lab.space.my_house_24.service.StatisticService;
import lab.space.my_house_24.specification.BankBookSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankBookServiceImpl implements BankBookService {

    private final BankBookRepository bankBookRepository;
    private final ApartmentRepository apartmentRepository;
    private final BankBookSpecification bankBookSpecification;
    private final BillRepository billRepository;
    private final CashBoxRepository cashBoxRepository;
    private final MessageSource message;
    private final StatisticService statisticService;
    private final ExcelService excelService;

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
        log.info("Try to find BankBook by number");
        return bankBookRepository.findBankBookByNumber(number);
    }

    @Override
    public List<BankBookResponseForTable> bankBookListForTable() {
        log.info("Try to get all BankBookResponseForTable by Active Status");
        return bankBookRepository.findAllByApartmentIsNullAndBankBookStatus(BankBookStatus.ACTIVE).stream().map(BankBookMapper::entityToDtoForTable).toList();
    }

    @Override
    public Page<BankBookResponseForCashBox> getBankBookListForCashBoxByUserId(Integer pageIndex, Long userId, String bankBookNumber) {
        log.info("Try to get all BankBookResponseForCashBox by User Id");
        return bankBookRepository.findAll(bankBookSpecification.getBankBookByUser(userId, bankBookNumber), PageRequest.of(pageIndex, 10)).map(BankBookMapper::toBankBookResponseForCashBox);
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
    public List<BankBook> getAllBankBookIsActive() {
        log.info("Try to get all BankBook is active ");
        return bankBookRepository.findAll(
                bankBookSpecification.getBankBookByRequest(BankBookRequest.builder().statusQuery(BankBookStatus.ACTIVE).build()));
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
    public void updateBankBookByRequest(BankBookUpdateRequest request) throws EntityNotFoundException, IllegalArgumentException {
        log.info("Try to Update BankBook by Request");
        saveBankBook(
                BankBookMapper.toBankBook(
                        request,
                        ((nonNull(request.number()) ? request.number() : generateNumber())),
                        getBankBookById(request.id()),
                        (nonNull(request.apartmentId()) ? getApartmentById(request.apartmentId()) : null)
                )
        );
        log.info("Success Update BankBook by Request");
    }

    @Override
    public void saveBankBookByRequest(BankBookSaveRequest request) throws EntityNotFoundException, IllegalArgumentException {
        log.info("Try to Save BankBook by Request");
        saveBankBook(
                BankBookMapper.toBankBook(
                        request,
                        (nonNull(request.number()) ? request.number() : generateNumber()),
                        (nonNull(request.apartmentId()) ? getApartmentById(request.apartmentId()) : null)
                )
        );
        log.info("Success Save BankBook by Request");
    }

    @Override
    public void saveBankBook(BankBook bankBook) {
        log.info("Try to Save BankBook");
        bankBookRepository.save(bankBook);
        log.info("Success Save BankBook");
    }

    @Override
    @Transactional
    public InputStreamResource getExcel(BankBookRequest request) throws IOException {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            List<BankBookResponse> bankBookResponses = getAllBankBookResponse(request).stream().toList();
            String[] header = {
                    "№",
                    message.getMessage("bank_book.status", null, LocaleContextHolder.getLocale()),
                    message.getMessage("bank_book.apartment", null, LocaleContextHolder.getLocale()),
                    message.getMessage("bank_book.house", null, LocaleContextHolder.getLocale()),
                    message.getMessage("bank_book.section", null, LocaleContextHolder.getLocale()),
                    message.getMessage("bank_book.owner", null, LocaleContextHolder.getLocale()),
                    message.getMessage("bank_book.balance", null, LocaleContextHolder.getLocale())
            };

            excelService.getExcelForBankBookTable(workbook, header, bankBookResponses);
            workbook.write(out);

            return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
        }
    }

    @Override
    public void calculateBankBook(Long id, BigDecimal price, BigDecimal historyPrice, Bill bill) {
        log.info("Try to Calculate BankBook");
        BankBook bankBook = getBankBookById(id);
        BigDecimal totalPrice = bankBook.getTotalPrice();
        List<Bill> bills = bankBook.getBill();
        List<CashBox> cashBoxes = bankBook.getCashBoxes();

        calculateBankBookForBills(bills, historyPrice, totalPrice, true);
        calcMoneyUsedToCashBox(cashBoxes, price, totalPrice, false, bill);
        addSumBankBook(bankBook, totalPrice, price, true);
        log.info("Success Calculate BankBook");
    }

    @Override
    public void calculateBankBook(Long id, BigDecimal price, Boolean type, Bill bill) {
        log.info("Try to Calculate BankBook");
        BankBook bankBook = getBankBookById(id);
        BigDecimal totalPrice = bankBook.getTotalPrice();
        List<Bill> bills = bankBook.getBill();
        List<CashBox> cashBoxes = bankBook.getCashBoxes();
        BigDecimal sum = BigDecimal.ZERO;
        for (Bill bill1 : bills) {
            sum = sum.add(bill1.getTotalPrice().subtract(bill1.getPayed()));
        }
        if ((sum.compareTo(totalPrice) >= 0 && type) || (sum.compareTo(totalPrice) <= 0 && !type) || (nonNull(bill) && bill.getDraft())) {
            calculateBankBookForBills(bills, price, totalPrice, type);
        }

        calcMoneyUsedToCashBox(cashBoxes, price, totalPrice, !type, bill);
        addSumBankBook(bankBook, price, totalPrice, type);
        log.info("Success Calculate BankBook");
    }

    private void calculateBankBookForBills(List<Bill> bills, BigDecimal calculatePrice, BigDecimal calculateTotalPrice, Boolean type) {
        log.info("Try to Calculate Bills in BankBook");
        if (calculateTotalPrice.compareTo(BigDecimal.ZERO) < 0 && type) {
            for (int i = 0; i < bills.size() && calculatePrice.compareTo(BigDecimal.ZERO) != 0 && calculateTotalPrice.compareTo(BigDecimal.ZERO) < 0; i++) {
                BigDecimal payed = bills.get(i).getPayed();
                BigDecimal totalPrice = bills.get(i).getTotalPrice();
                boolean actionCheck = false;

                if (calculatePrice.compareTo(totalPrice.subtract(payed)) < 1 && bills.get(i).getDraft()) {
                    bills.get(i).setPayed(payed.add(calculatePrice));
                    bills.get(i).setAutoPayed(true);
                    bills.get(i).setPayedCashBox(bills.get(i).getPayedCashBox().add(calculatePrice));
                    calculatePrice = BigDecimal.ZERO;

                    if (totalPrice.subtract(bills.get(i).getPayed()).compareTo(BigDecimal.ZERO) > 0) {
                        bills.get(i).setStatus(BillStatus.PARTLY_PAID);
                    } else {
                        bills.get(i).setStatus(BillStatus.PAID);
                    }

                    actionCheck = true;
                } else if (totalPrice.compareTo(payed) > 0 && bills.get(i).getDraft()) {
                    bills.get(i).setPayed(payed.add(totalPrice.subtract(payed)));
                    bills.get(i).setStatus(BillStatus.PAID);
                    bills.get(i).setAutoPayed(true);
                    bills.get(i).setPayedCashBox(bills.get(i).getPayedCashBox().add(totalPrice.subtract(payed)));

                    calculatePrice = calculatePrice.subtract(totalPrice.subtract(payed));
                    calculateTotalPrice = calculateTotalPrice.add(totalPrice.subtract(payed));

                    actionCheck = true;
                }
                if (actionCheck) {
                    saveBill(bills.get(i));
                }
            }
        } else if (calculateTotalPrice.compareTo(BigDecimal.ZERO) > 0 && !type) {
            for (int i = 0; i < bills.size() && !calculatePrice.equals(BigDecimal.ZERO) && calculateTotalPrice.compareTo(BigDecimal.ZERO) > 0; i++) {
                BigDecimal payed = bills.get(i).getPayed();
                BigDecimal totalPrice = bills.get(i).getTotalPrice();
                boolean actionCheck = false;

                if (calculateTotalPrice.compareTo(totalPrice.subtract(payed)) < 1 && bills.get(i).getDraft()) {
                    bills.get(i).setPayed(payed.add(calculateTotalPrice));
                    bills.get(i).setAutoPayed(true);
                    bills.get(i).setPayedCashBox(bills.get(i).getPayedCashBox().add(calculateTotalPrice));
                    calculateTotalPrice = BigDecimal.ZERO;

                    if (totalPrice.subtract(bills.get(i).getPayed()).compareTo(BigDecimal.ZERO) > 0) {
                        bills.get(i).setStatus(BillStatus.PARTLY_PAID);
                    } else {
                        bills.get(i).setStatus(BillStatus.PAID);
                    }

                    actionCheck = true;
                } else if (totalPrice.compareTo(payed) > 0 && bills.get(i).getDraft()) {
                    bills.get(i).setPayed(payed.add(totalPrice.subtract(payed)));
                    bills.get(i).setStatus(BillStatus.PAID);
                    bills.get(i).setAutoPayed(true);
                    bills.get(i).setPayedCashBox(bills.get(i).getPayedCashBox().add(totalPrice.subtract(payed)));

                    calculatePrice = calculatePrice.subtract(totalPrice.subtract(payed));
                    calculateTotalPrice = calculateTotalPrice.add(totalPrice.subtract(payed));

                    actionCheck = true;
                }
                if (actionCheck) {
                    saveBill(bills.get(i));
                }
            }
        }
        log.info("Success Calculate Bills in BankBook");
    }

    private void addSumBankBook(BankBook bankBook, BigDecimal price, BigDecimal totalPrice, Boolean type) {
        log.info("Try to add sum to BankBook");
        if (type) {
            totalPrice = totalPrice.add(price);
            statisticService.updateStatistic(
                    Statistic.builder()
                            .cashBoxState(BigDecimal.ZERO)
                            .bankBookBalance(price)
                            .bankBookExpense(BigDecimal.ZERO)
                            .build()
            );
        } else {
            totalPrice = totalPrice.subtract(price);
            statisticService.updateStatistic(
                    Statistic.builder()
                            .cashBoxState(BigDecimal.ZERO)
                            .bankBookBalance(BigDecimal.ZERO.subtract(price))
                            .bankBookExpense(BigDecimal.ZERO)
                            .build()
            );
        }
        if (bankBook.getTotalPrice().compareTo(totalPrice) > 0) {
            if (bankBook.getTotalPrice().compareTo(BigDecimal.ZERO) <= 0 && totalPrice.compareTo(BigDecimal.ZERO) < 0) {
                statisticService.updateStatistic(
                        Statistic.builder()
                                .cashBoxState(BigDecimal.ZERO)
                                .bankBookBalance(BigDecimal.ZERO)
                                .bankBookExpense(price)
                                .build()
                );
            } else if (bankBook.getTotalPrice().compareTo(BigDecimal.ZERO) > 0 && totalPrice.compareTo(BigDecimal.ZERO) < 0) {
                statisticService.updateStatistic(
                        Statistic.builder()
                                .cashBoxState(BigDecimal.ZERO)
                                .bankBookBalance(BigDecimal.ZERO)
                                .bankBookExpense(totalPrice.abs())
                                .build()
                );
            }
        } else if (bankBook.getTotalPrice().compareTo(totalPrice) < 0) {
            if (bankBook.getTotalPrice().compareTo(BigDecimal.ZERO) < 0 && totalPrice.compareTo(BigDecimal.ZERO) < 0) {
                statisticService.updateStatistic(
                        Statistic.builder()
                                .cashBoxState(BigDecimal.ZERO)
                                .bankBookBalance(BigDecimal.ZERO)
                                .bankBookExpense(BigDecimal.ZERO.subtract(price))
                                .build()
                );
            } else if (bankBook.getTotalPrice().compareTo(BigDecimal.ZERO) < 0 && totalPrice.compareTo(BigDecimal.ZERO) >= 0) {
                statisticService.updateStatistic(
                        Statistic.builder()
                                .cashBoxState(BigDecimal.ZERO)
                                .bankBookBalance(BigDecimal.ZERO)
                                .bankBookExpense(bankBook.getTotalPrice())
                                .build()
                );
            }
        }
        bankBook.setTotalPrice(totalPrice);
        saveBankBook(bankBook);
        log.info("Success add sum to BankBook");
    }

    private void calcMoneyUsedToCashBox(List<CashBox> cashBoxes, BigDecimal calculatePrice, BigDecimal calculateTotalPrice, Boolean type, Bill bill) {
        log.info("Try to Calculate moneyUsed in CashBox");
        if (calculateTotalPrice.compareTo(BigDecimal.ZERO) != 0 && type) {
            for (CashBox cashBox : cashBoxes) {
                if (!cashBox.getDraft()) {
                    continue;
                }

                BigDecimal payed = cashBox.getMoneyUsed();
                BigDecimal totalPrice = cashBox.getPrice();

                BigDecimal remainingAmount = totalPrice.subtract(payed);

                if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }

                BigDecimal amountToPay = calculatePrice.min(remainingAmount);

                cashBox.setMoneyUsed(payed.add(amountToPay));

                calculateTotalPrice = calculateTotalPrice.subtract(amountToPay);
                calculatePrice = calculatePrice.subtract(amountToPay);

                saveCashBox(cashBox);
            }
        } else if (calculateTotalPrice.compareTo(BigDecimal.ZERO) != 0 && !type) {
            for (CashBox cashBox : cashBoxes) {
                if (isNull(bill) && cashBox.getPrice().compareTo(calculatePrice) == 0) {
                    break;
                }
                if (!cashBox.getDraft()) {
                    continue;
                }

                BigDecimal payed = cashBox.getMoneyUsed();
                BigDecimal totalPrice = BigDecimal.ZERO;

                BigDecimal remainingAmount = totalPrice.subtract(payed);

                if (remainingAmount.compareTo(BigDecimal.ZERO) >= 0) {
                    continue;
                }

                BigDecimal amountToPay = calculatePrice.min(remainingAmount);

                cashBox.setMoneyUsed(payed.add(amountToPay));

                calculateTotalPrice = calculateTotalPrice.subtract(amountToPay);
                calculatePrice = calculatePrice.subtract(amountToPay);

                saveCashBox(cashBox);
            }
        }
        log.info("Success Calculate moneyUsed in CashBox");
    }


    private void saveBill(Bill bill) {
        log.info("Try to save Bill " + bill.getId());
        billRepository.save(bill);
        log.info("Success save Bill " + bill.getId());
    }

    private void saveCashBox(CashBox cashBox) {
        log.info("Try to save CashBox " + cashBox.getId());
        cashBoxRepository.save(cashBox);
        log.info("Success save CashBox " + cashBox.getId());
    }

    @Override
    public void deleteBankBookById(Long id) throws EntityNotFoundException, IllegalArgumentException {
        log.info("Try to Delete BankBook");
        BankBook bankBook = getBankBookById(id);
        if (nonNull(bankBook.getApartment())) {
            log.warn("BankBook cannot be deleted");
            throw new IllegalArgumentException(message.getMessage("bank_book.used.error", null, LocaleContextHolder.getLocale()));
        } else if (bankBook.getBankBookStatus() != BankBookStatus.INACTIVE) {
            log.warn("BankBook cannot be deleted");
            throw new IllegalArgumentException(message.getMessage("bank_book.active.error", null, LocaleContextHolder.getLocale()));
        } else {
            bankBookRepository.delete(bankBook);
            log.info("Success delete BankBook");
        }
    }

    private Apartment getApartmentById(Long id) throws EntityNotFoundException {
        log.info("Try to find Apartment");
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Apartment not found by id " + id));
    }

    private String generateNumber() throws IllegalArgumentException {
        log.info("Try to generate Number");
        List<BankBook> bankBookList = bankBookRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        if (bankBookList.isEmpty()) {
            log.warn("Bank books not found");
            return String.format("%05d-%05d", 0, 1);
        }
        BankBook bankBook = bankBookList.get(0);

        String number = bankBook.getNumber();
        String[] parts;

        int firstPart;
        int secondPart;
        long i = 0L;

        do {
            if (i > 9999999999L) {
                throw new IllegalArgumentException("The number of valid numbers has expired");
            }

            parts = number.split("-");
            firstPart = Integer.parseInt(parts[0]);
            secondPart = Integer.parseInt(parts[1]);
            secondPart++;
            if (secondPart > 99999) {
                firstPart++;
                secondPart = 1;
            }
            if (firstPart > 99999) {
                firstPart = 0;
                secondPart = 1;
            }
            number = String.format("%05d-%05d", firstPart, secondPart);

            i += 1L;
        } while (bankBookRepository.existsByNumber(number));
        log.info("Success generate Number");
        return number;
    }

    @Override
    public Long count() {
        return bankBookRepository.count();
    }
}

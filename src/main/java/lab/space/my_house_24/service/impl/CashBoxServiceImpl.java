package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.CashBox;
import lab.space.my_house_24.entity.Statistic;
import lab.space.my_house_24.mapper.CashBoxMapper;
import lab.space.my_house_24.mapper.EnumMapper;
import lab.space.my_house_24.model.cash_box.CashBoxRequest;
import lab.space.my_house_24.model.cash_box.CashBoxResponse;
import lab.space.my_house_24.model.cash_box.CashBoxSaveRequest;
import lab.space.my_house_24.model.cash_box.CashBoxUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.repository.CashBoxRepository;
import lab.space.my_house_24.service.*;
import lab.space.my_house_24.specification.CashBoxSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashBoxServiceImpl implements CashBoxService {
    private final CashBoxRepository cashBoxRepository;
    private final CashBoxSpecification cashBoxSpecification;
    private final MessageSource message;
    private final ArticleService articleService;
    private final StaffService staffService;
    private final BankBookService bankBookService;
    private final BillService billService;
    private final StatisticService statisticService;
    private final ExcelService excelService;

    @Override
    public CashBox getCashBoxById(Long id) throws EntityNotFoundException {
        log.info("Try to find CashBox");
        return cashBoxRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CashBox not found by id " + id));
    }

    @Override
    public CashBoxResponse getCashBoxResponseById(Long id) throws EntityNotFoundException {
        log.info("Try to find CashBox and convert to Response");
        return getCashBoxResponse(getCashBoxById(id));
    }

    @Override
    public Page<CashBoxResponse> getAllCashBoxResponse(CashBoxRequest request) {
        log.info("Try to get all CashBoxResponse by Request");
        final int DEFAULT_PAGE_SIZE = 10;
        return cashBoxRepository.findAll(
                cashBoxSpecification.getCashBoxByRequest(request),
                PageRequest.of(request.pageIndex(), DEFAULT_PAGE_SIZE)).map(this::getCashBoxResponse);
    }

    @Override
    public List<CashBox> getAllCashBoxIsActive() {
        log.info("Try to get all CashBox is active ");
        return cashBoxRepository.findAll(cashBoxSpecification.getCashBoxByRequest(CashBoxRequest.builder().draftQuery(true).build()));
    }

    private CashBoxResponse getCashBoxResponse(CashBox cashBox) {
        log.info("Try to convert to CashBoxResponse");
        EnumResponse draft = cashBox.getDraft() ?
                EnumMapper.toSimpleDto("true", message.getMessage("bills.draft", null, LocaleContextHolder.getLocale())) :
                EnumMapper.toSimpleDto("false", message.getMessage("bills.no_draft", null, LocaleContextHolder.getLocale()));
        return CashBoxMapper.toCashBoxResponse(cashBox, draft);
    }

    @Override
    public List<EnumResponse> getDraft() {
        log.info("Try to get all Draft");
        return List.of(
                EnumMapper.toSimpleDto("true", message.getMessage("bills.draft", null, LocaleContextHolder.getLocale())),
                EnumMapper.toSimpleDto("false", message.getMessage("bills.no_draft", null, LocaleContextHolder.getLocale()))
        );
    }

    @Override
    public void updateCashBoxByRequest(CashBoxUpdateRequest request) throws EntityNotFoundException {
        log.info("Try to update CashBox by Request");
        CashBox cashBox = getCashBoxById(request.id());
        CashBox newCashBox = new CashBox()
                .setBankBook(cashBox.getBankBook())
                .setDraft(cashBox.getDraft())
                .setPrice(cashBox.getPrice());
        CashBox saveCashBox = saveCashBox(
                CashBoxMapper.toCashBox(
                        cashBox,
                        request,
                        nonNull(request.bankBookId()) ? bankBookService.getBankBookById(request.bankBookId()) : null,
                        staffService.getStaffById(request.staffId()),
                        articleService.getArticleById(request.articleId())
                )
        );
        if (nonNull(saveCashBox.getBankBook()) && saveCashBox.getType()) {
            CashBoxCalculate(newCashBox, saveCashBox);
        }
        CashBoxCalculateStatistic(newCashBox, saveCashBox);
        log.info("Success update CashBox by Request");
    }

    @Override
    public void saveCashBoxByRequest(CashBoxSaveRequest request) throws EntityNotFoundException {
        log.info("Try to save CashBox by Request");
        CashBox saveCashBox = saveCashBox(
                CashBoxMapper.toCashBox(
                        request,
                        nonNull(request.bankBookId()) ? bankBookService.getBankBookById(request.bankBookId()) : null,
                        staffService.getStaffById(request.staffId()),
                        articleService.getArticleById(request.articleId())
                )
        );
        if (nonNull(saveCashBox.getBankBook()) && saveCashBox.getType()) {
            CashBoxCalculate(saveCashBox);
        }
        CashBoxCalculateStatistic(saveCashBox);
        log.info("Success save CashBox by Request");
    }

    private void CashBoxCalculateStatistic(CashBox cashBox, CashBox saveCashBox) {
        log.info("Try to Calculate Statistic CashBox by Update");
        if (nonNull(cashBox.getBankBook()) && Objects.equals(cashBox.getBankBook().getId(), saveCashBox.getBankBook().getId())) {
            if (!cashBox.getDraft() && saveCashBox.getDraft()) {
                statisticService.updateStatistic(
                        Statistic.builder()
                                .cashBoxState(saveCashBox.getPrice())
                                .bankBookBalance(BigDecimal.ZERO)
                                .bankBookExpense(BigDecimal.ZERO)
                                .build()
                );
            } else if (cashBox.getDraft() && !saveCashBox.getDraft()) {
                statisticService.updateStatistic(
                        Statistic.builder()
                                .cashBoxState(BigDecimal.ZERO.subtract(saveCashBox.getPrice()))
                                .bankBookBalance(BigDecimal.ZERO)
                                .bankBookExpense(BigDecimal.ZERO)
                                .build()
                );
            } else if (saveCashBox.getDraft() && !Objects.equals(cashBox.getPrice(), saveCashBox.getPrice())) {
                statisticService.updateStatistic(
                        Statistic.builder()
                                .cashBoxState(saveCashBox.getPrice().subtract(cashBox.getPrice()))
                                .bankBookBalance(BigDecimal.ZERO)
                                .bankBookExpense(BigDecimal.ZERO)
                                .build()
                );
            }
        } else if (isNull(cashBox.getBankBook())) {
            if (!cashBox.getDraft() && saveCashBox.getDraft()) {
                statisticService.updateStatistic(
                        Statistic.builder()
                                .cashBoxState(BigDecimal.ZERO.subtract(saveCashBox.getPrice()))
                                .bankBookBalance(BigDecimal.ZERO)
                                .bankBookExpense(BigDecimal.ZERO)
                                .build()
                );
            } else if (cashBox.getDraft() && !saveCashBox.getDraft()) {
                statisticService.updateStatistic(
                        Statistic.builder()
                                .cashBoxState(saveCashBox.getPrice())
                                .bankBookBalance(BigDecimal.ZERO)
                                .bankBookExpense(BigDecimal.ZERO)
                                .build()
                );
            }
        } else if (cashBox.getDraft() && !saveCashBox.getDraft()) {
            statisticService.updateStatistic(
                    Statistic.builder()
                            .cashBoxState(BigDecimal.ZERO.subtract(cashBox.getPrice()))
                            .bankBookBalance(BigDecimal.ZERO)
                            .bankBookExpense(BigDecimal.ZERO)
                            .build()
            );
        }
        log.info("Success Calculate Statistic CashBox by Update");
    }

    private void CashBoxCalculateStatistic(CashBox cashBox) {
        log.info("Try to Calculate Statistic CashBox by Save");
        if (cashBox.getDraft()) {
            if (cashBox.getType()) {
                statisticService.updateStatistic(
                        Statistic.builder()
                                .cashBoxState(cashBox.getPrice())
                                .bankBookBalance(BigDecimal.ZERO)
                                .bankBookExpense(BigDecimal.ZERO)
                                .build()
                );
            } else {
                statisticService.updateStatistic(
                        Statistic.builder()
                                .cashBoxState(BigDecimal.ZERO.subtract(cashBox.getPrice()))
                                .bankBookBalance(BigDecimal.ZERO)
                                .bankBookExpense(BigDecimal.ZERO)
                                .build()
                );
            }
        }
        log.info("Success Calculate Statistic CashBox by Update");
    }

    private void CashBoxCalculate(CashBox cashBox, CashBox saveCashBox) {
        log.info("Try to Calculate CashBox by Update");
        if (Objects.equals(cashBox.getBankBook().getId(), saveCashBox.getBankBook().getId())) {
            if (!cashBox.getDraft() && saveCashBox.getDraft()) {
                bankBookService.calculateBankBook(saveCashBox.getBankBook().getId(), saveCashBox.getPrice(), true, null);
            } else if (cashBox.getDraft() && !saveCashBox.getDraft()) {
                bankBookService.calculateBankBook(saveCashBox.getBankBook().getId(), saveCashBox.getPrice(), false, null);
            } else if (saveCashBox.getDraft() && !Objects.equals(cashBox.getPrice(), saveCashBox.getPrice())) {
                bankBookService.calculateBankBook(
                        saveCashBox.getBankBook().getId(),
                        saveCashBox.getPrice().subtract(cashBox.getPrice()),
                        true,
                        null
                );
            }
        } else if (!cashBox.getDraft() && saveCashBox.getDraft()) {
            cashBox.setBankBook(saveCashBox.getBankBook());
            CashBoxCalculate(cashBox, saveCashBox);
        } else if (cashBox.getDraft() && !saveCashBox.getDraft()) {
            bankBookService.calculateBankBook(cashBox.getBankBook().getId(), cashBox.getPrice(), false, null);
        } else if (saveCashBox.getDraft()) {
            bankBookService.calculateBankBook(cashBox.getBankBook().getId(), cashBox.getPrice(), false, null);
            bankBookService.calculateBankBook(saveCashBox.getBankBook().getId(), saveCashBox.getPrice(), true, null);
        }
        log.info("Success Calculate CashBox by Update");
    }

    private void CashBoxCalculate(CashBox cashBox) {
        log.info("Try to Calculate CashBox by Save");
        if (cashBox.getDraft()) {
            bankBookService.calculateBankBook(cashBox.getBankBook().getId(), cashBox.getPrice(), true, null);
        }
        log.info("Success Calculate CashBox by Save");
    }

    @Override
    public CashBox saveCashBox(CashBox cashBox) {
        log.info("Try to save CashBox");
        cashBoxRepository.save(cashBox);
        log.info("Success save CashBox");
        return cashBox;
    }

    @Override
    public void deleteCashBoxById(Long id) throws EntityNotFoundException {
        log.info("Try to Delete CashBox");
        CashBox cashBox = getCashBoxById(id);
        if (cashBox.getDraft() && cashBox.getIsActive()) {
            log.warn("CashBox cannot be deleted");
            throw new IllegalArgumentException(message.getMessage("cash_box.delete.error", null, LocaleContextHolder.getLocale()));
        } else {
            cashBoxRepository.delete(cashBox);
            log.info("Success Delete CashBox");
        }
    }

    @Override
    public CashBoxResponse getNewCashBoxResponse(Boolean type) throws IllegalArgumentException {
        return CashBoxMapper.toCashBoxResponse(generateNumber(), getTodayDate());
    }

    @Override
    @Transactional
    public InputStreamResource getExcel(CashBoxRequest request) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            List<CashBoxResponse> cashBoxResponses = getAllCashBoxResponse(request).stream().toList();
            String[] header = {
                    "№",
                    message.getMessage("cash_box.date", null, LocaleContextHolder.getLocale()),
                    message.getMessage("cash_box.status", null, LocaleContextHolder.getLocale()),
                    message.getMessage("cash_box.article", null, LocaleContextHolder.getLocale()),
                    message.getMessage("cash_box.owner", null, LocaleContextHolder.getLocale()),
                    message.getMessage("cash_box.bank_book", null, LocaleContextHolder.getLocale()),
                    message.getMessage("cash_box.type", null, LocaleContextHolder.getLocale()),
                    message.getMessage("cash_box.sum", null, LocaleContextHolder.getLocale())
            };

            excelService.getExcelForCashBoxTable(workbook, header, cashBoxResponses);
            workbook.write(out);

            return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public InputStreamResource getExcel(Long id) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            CashBoxResponse cashBoxResponse = getCashBoxResponseById(id);
            String[] header = {
                    "№",
                    message.getMessage("cash_box.date", null, LocaleContextHolder.getLocale()),
                    message.getMessage("cash_box.card.owner", null, LocaleContextHolder.getLocale()),
                    message.getMessage("cash_box.card.bank_book", null, LocaleContextHolder.getLocale()),
                    message.getMessage("cash_box.card.article", null, LocaleContextHolder.getLocale()),
                    message.getMessage("cash_box.card.draft", null, LocaleContextHolder.getLocale()),
                    message.getMessage("cash_box.card.manager", null, LocaleContextHolder.getLocale()),
                    message.getMessage("cash_box.card.sum", null, LocaleContextHolder.getLocale()),
                    message.getMessage("cash_box.card.comment", null, LocaleContextHolder.getLocale())
            };

            excelService.getExcelForCashBoxCard(workbook, header, cashBoxResponse);
            workbook.write(out);

            return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
        }
    }

    private String generateNumber() throws IllegalArgumentException {
        log.info("Try to generate Number");

        List<CashBox> cashBoxes = cashBoxRepository.findAll();

        if (cashBoxes.isEmpty()) {
            log.warn("CashBoxes not found");
            return String.format("%010d", 1);
        }
        CashBox cashBox = cashBoxes.get(0);

        String stringNumber = cashBox.getNumber();
        long number;
        long i = 0;

        do {
            if (i > 9999999999L) {
                throw new IllegalArgumentException("The number of valid numbers has expired");
            }

            number = Long.parseLong(stringNumber);
            number++;
            i++;
            if (number > 9999999999L) {
                number = 1;
            }
            stringNumber = String.format("%010d", number);
        } while (cashBoxRepository.existsByNumber(stringNumber));
        log.info("Success generate Number");
        return stringNumber;
    }

    private LocalDate getTodayDate() {
        return Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public List<BigDecimal> statisticSumByType(Boolean type) {
        List<CashBox> all = cashBoxRepository.findAllByType(type);
        List<BigDecimal> allSumByMonths = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            BigDecimal sum = BigDecimal.ZERO;
            for (CashBox cashBox : all) {
                if (cashBox.getCreateAt().atZone(ZoneId.systemDefault()).getMonth().getValue() == i) {
                    sum = sum.add(cashBox.getPrice());
                }
            }
            allSumByMonths.add(sum);
        }
        return allSumByMonths;
    }

    @Override
    public BigDecimal statisticCashStatement() {
        List<CashBox> allComing = cashBoxRepository.findAllByType(true);
        List<CashBox> allCosts = cashBoxRepository.findAllByType(false);
        BigDecimal sumComing = BigDecimal.ZERO;
        for (CashBox cashBox : allComing) {
            sumComing = sumComing.add(cashBox.getPrice());

        }
        BigDecimal sumCosts = BigDecimal.ZERO;
        for (CashBox cashBox : allCosts) {
            sumCosts = sumCosts.add(cashBox.getPrice());

        }
        return sumComing.subtract(sumCosts);
    }

    @Override
    public BigDecimal statisticAccountBalance() {
        BigDecimal billsSum = billService.sumOfAllBills();
        List<CashBox> cashBoxes = cashBoxRepository.findAllByType(true);
        BigDecimal cashBoxSum = BigDecimal.ZERO;
        for (CashBox cashBox : cashBoxes) {
            cashBoxSum = cashBoxSum.add(cashBox.getPrice());
        }
        return cashBoxSum.subtract(billsSum);
    }
}

package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.CashBox;
import lab.space.my_house_24.enums.ArticleType;
import lab.space.my_house_24.mapper.CashBoxMapper;
import lab.space.my_house_24.mapper.EnumMapper;
import lab.space.my_house_24.model.cash_box.CashBoxRequest;
import lab.space.my_house_24.model.cash_box.CashBoxResponse;
import lab.space.my_house_24.model.cash_box.CashBoxSaveRequest;
import lab.space.my_house_24.model.cash_box.CashBoxUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.repository.CashBoxRepository;
import lab.space.my_house_24.service.ArticleService;
import lab.space.my_house_24.service.BankBookService;
import lab.space.my_house_24.service.CashBoxService;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.specification.CashBoxSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

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
        saveCashBox(
                CashBoxMapper.toCashBox(
                        getCashBoxById(request.id()),
                        request,
                        nonNull(request.bankBookId()) ? bankBookService.getBankBookById(request.bankBookId()) : null,
                        staffService.getStaffById(request.staffId()),
                        articleService.getArticleById(request.articleId())
                )
        );
        log.info("Success update CashBox by Request");
    }

    @Override
    public void saveCashBoxByRequest(CashBoxSaveRequest request) throws EntityNotFoundException {
        log.info("Try to save CashBox by Request");
        saveCashBox(
                CashBoxMapper.toCashBox(
                        request,
                        nonNull(request.bankBookId()) ? bankBookService.getBankBookById(request.bankBookId()) : null,
                        staffService.getStaffById(request.staffId()),
                        articleService.getArticleById(request.articleId())
                )
        );
        log.info("Success save CashBox by Request");
    }

    @Override
    public void saveCashBox(CashBox cashBox) {
        log.info("Try to save CashBox");
        cashBoxRepository.save(cashBox);
        log.info("Success save CashBox");
    }

    @Override
    public void deleteCashBoxById(Long id) throws EntityNotFoundException {
        log.info("Try to Delete CashBox");
        CashBox cashBox = getCashBoxById(id);
        if (!cashBox.getDraft() && !cashBox.getIsActive()){
            cashBoxRepository.delete(cashBox);
            log.info("Success Delete CashBox");
        }else {
            String e;
            if (LocaleContextHolder.getLocale().toLanguageTag().equals("uk")) {
                e = "Відомість використовується/використовувалася в розрахунках, її неможливо видалити.(id-" + id +")";
            } else {
                e = "Statement is/was used in calculations, it cannot be deleted.(id-" + id +")";
            }
            log.warn("CashBox cannot be deleted");
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public CashBoxResponse getNewCashBoxResponse(Boolean type) {
        return CashBoxMapper.toCashBoxResponse(generateNumber(type), getTodayDate());
    }

    private String generateNumber(Boolean type) {
        log.info("Try to generate Number");

        List<CashBox> cashBoxes = cashBoxRepository.findAll(cashBoxSpecification.getCashBoxByRequest(CashBoxRequest.builder()
                .typeQuery(type ? ArticleType.INCOME : ArticleType.EXPENSE).build()));

        if (cashBoxes.isEmpty()) {
            log.warn("CashBoxes not found");
            return String.format("%010d", 1);
        }
        CashBox cashBox = cashBoxes.get(0);

        String stringNumber = cashBox.getNumber();
        long number;

        do {
            number = Long.parseLong(stringNumber);
            number++;
            stringNumber = String.format("%010d", number);
        } while (cashBoxRepository.existsByNumberAndType(stringNumber, type));
        log.info("Success generate Number");
        return stringNumber;
    }

    private LocalDate getTodayDate() {
        return Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}

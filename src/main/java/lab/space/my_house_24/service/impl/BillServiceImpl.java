package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Bill;
import lab.space.my_house_24.enums.BillStatus;
import lab.space.my_house_24.mapper.BillMapper;
import lab.space.my_house_24.mapper.EnumMapper;
import lab.space.my_house_24.model.bill.*;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.repository.BillRepository;
import lab.space.my_house_24.service.BankBookService;
import lab.space.my_house_24.service.BillService;
import lab.space.my_house_24.service.RateService;
import lab.space.my_house_24.service.ServiceBillService;
import lab.space.my_house_24.specification.BillSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final ServiceBillService serviceBillService;
    private final BankBookService bankBookService;
    private final RateService rateService;
    private final BillSpecification billSpecification;
    private final MessageSource message;

    @Override
    public Bill getBillById(Long id) throws EntityNotFoundException {
        log.info("Try to find Bill");
        return billRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found by id " + id));
    }

    @Override
    public BillResponse getBillResponseById(Long id) throws EntityNotFoundException {
        log.info("Try to get Bill & convert to Response");
        return BillMapper.toBillResponse(getBillById(id));
    }

    @Override
    public Page<BillResponse> getAllBillResponse(BillRequest request) {
        log.info("Try to get all BillResponse by Request");
        final int DEFAULT_PAGE_SIZE = 10;
        return billRepository.findAll(
                billSpecification.getBillByRequest(request),
                PageRequest.of(request.pageIndex(), DEFAULT_PAGE_SIZE)).map(BillMapper::toBillResponse);
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
    public void updateBillByRequest(BillUpdateRequest request) throws EntityNotFoundException {
        log.info("Try to update Bill by Request");
        Bill bill = getBillById(request.id());
        Bill newBill = new Bill()
                .setDraft(bill.getDraft())
                .setTotalPrice(bill.getTotalPrice())
                .setPayed(bill.getPayed())
                .setBankBook(bill.getBankBook());

        Bill saveBill = saveBill(
                BillMapper.toBill(
                        bill,
                        request,
                        bankBookService.getBankBookById(request.bankBookId()),
                        request.rateId() != null ? rateService.getRateById(request.rateId()) : null
                )
        );
        serviceBillService.saveServiceBillByRequest(request.serviceBillList(), saveBill);
        BillCalculate(newBill, saveBill);

        log.info("Success update Bill by Request");
    }

    @Override
    public void saveBillByRequest(BillSaveRequest request) throws EntityNotFoundException {
        log.info("Try to save Bill by Request");
        Bill saveBill = saveBill(
                BillMapper.toBill(
                        request,
                        bankBookService.getBankBookById(request.bankBookId()),
                        request.rateId() != null ? rateService.getRateById(request.rateId()) : null
                )

        );
        BillCalculate(saveBill);
        serviceBillService.saveServiceBillByRequest(request.serviceBillList(), saveBill);
        log.info("Success save Bill by Request");
    }

    @Override
    public Bill saveBill(Bill bill) {
        log.info("Try to save Bill");
        billRepository.save(bill);
        log.info("Success save Bill");
        return bill;
    }

    @Override
    public void deleteBillByRequest(List<BillDeleteRequest> bills) throws EntityNotFoundException, IllegalArgumentException {
        log.info("Try to delete Bills");
        List<Bill> billList = bills.stream().map(billDeleteRequest -> getBillById(billDeleteRequest.id())).toList();
        if (billList.stream().anyMatch(billDeleteRequest -> billDeleteRequest.getAutoPayed() && billDeleteRequest.getIsActive())) {
            log.warn("Bills cannot be deleted");
            throw new IllegalArgumentException(
                    message.getMessage(
                            "bills.delete.error.bills_used",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );

        } else if (billList.stream().anyMatch(billDeleteRequest -> billDeleteRequest.getStatus() != BillStatus.UNPAID && billDeleteRequest.getPayed().compareTo(BigDecimal.ZERO) != 0)) {
            log.warn("Bills cannot be deleted");
            throw new IllegalArgumentException(
                    message.getMessage(
                            "bills.delete.error.bills_payed",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );

        } else {
            billRepository.deleteAll(billList);
            log.info("Success delete Bills");
        }
    }

    @Override
    public void deleteBillById(Long id) throws EntityNotFoundException, IllegalArgumentException {
        log.info("Try to delete Bill");
        Bill bill = getBillById(id);
        if (bill.getAutoPayed() && bill.getIsActive()) {
            log.warn("Bill cannot be deleted");
            throw new IllegalArgumentException(
                    message.getMessage(
                            "bills.delete.error.bill_used",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        } else if (bill.getStatus() != BillStatus.UNPAID && bill.getPayed().compareTo(BigDecimal.ZERO) != 0) {
            log.warn("Bill cannot be deleted");
            throw new IllegalArgumentException(
                    message.getMessage(
                            "bills.delete.error.bill_payed",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        } else {
            billRepository.delete(bill);
            log.info("Success delete Bill");
        }
    }


    @Override
    public List<EnumResponse> getAllBillStatus() {
        log.info("Try to get all BillStatus");
        return Arrays.stream(BillStatus.values())
                .map(status -> EnumMapper.toSimpleDto(
                        status.name(),
                        status.getBillStatus(LocaleContextHolder.getLocale())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public BillResponse getNewBillResponse() {
        return BillMapper.toBillResponse(generateNumber(), getTodayDate());
    }

    private void BillCalculate(Bill bill, Bill saveBill) {
        log.info("Try to Calculate Bill by Update");
        if (Objects.equals(bill.getBankBook().getId(), saveBill.getBankBook().getId())) {

            if (!bill.getDraft() && saveBill.getDraft()) {
                bankBookService.calculateBankBook(saveBill.getBankBook().getId(), saveBill.getTotalPrice().subtract(saveBill.getPayed()), false, saveBill);
            } else if (
                    bill.getDraft()
                            && !saveBill.getDraft()
                            && (bill.getPayed().compareTo(saveBill.getPayed()) != 0 || bill.getTotalPrice().compareTo(saveBill.getTotalPrice()) != 0)
            ) {
                resetBill(saveBill);
                bankBookService.calculateBankBook(saveBill.getBankBook().getId(), bill.getTotalPrice().subtract(bill.getPayed().subtract(saveBill.getHistoryPayedCashBox())), saveBill.getHistoryPayedCashBox(), saveBill);
            } else if (bill.getDraft() && !saveBill.getDraft()) {
                resetBill(saveBill);
                bankBookService.calculateBankBook(saveBill.getBankBook().getId(), saveBill.getTotalPrice().subtract(bill.getPayed().subtract(saveBill.getHistoryPayedCashBox())), saveBill.getHistoryPayedCashBox(), saveBill);
            } else if (saveBill.getDraft() && (!Objects.equals(bill.getTotalPrice(), saveBill.getTotalPrice()) || !Objects.equals(bill.getPayed(), saveBill.getPayed()))) {
                bankBookService.calculateBankBook(
                        saveBill.getBankBook().getId(),
                        saveBill.getTotalPrice()
                                .subtract(saveBill.getPayed())
                                .subtract(bill.getTotalPrice().subtract(bill.getPayed())),
                        false, saveBill);
            }

        } else if (!bill.getDraft() && saveBill.getDraft()) {
            bill.setBankBook(saveBill.getBankBook());
            BillCalculate(bill, saveBill);
        } else if (bill.getDraft() && !saveBill.getDraft()) {
            resetBill(saveBill);
            bankBookService.calculateBankBook(bill.getBankBook().getId(), bill.getTotalPrice().subtract(bill.getPayed().subtract(saveBill.getHistoryPayedCashBox())), true, saveBill);
        } else if (saveBill.getDraft()) {
            resetBill(saveBill);
            if (bill.getPayed().compareTo(saveBill.getPayed()) != 0 || bill.getTotalPrice().compareTo(saveBill.getTotalPrice()) != 0) {
                bankBookService.calculateBankBook(bill.getBankBook().getId(), bill.getTotalPrice().subtract(bill.getPayed().subtract(saveBill.getHistoryPayedCashBox())), true, saveBill);
            } else {
                bankBookService.calculateBankBook(bill.getBankBook().getId(), saveBill.getTotalPrice().subtract(saveBill.getPayed()), true, saveBill);
            }
            bankBookService.calculateBankBook(saveBill.getBankBook().getId(), saveBill.getTotalPrice().subtract(saveBill.getPayed()), false, saveBill);
        }
        log.info("Success Calculate Bill by Update");
    }

    private void resetBill(Bill bill) {
        log.info("Try to reset Bill");
        bill.setPayed(bill.getPayed().subtract(bill.getPayedCashBox()));
        bill.setStatus(bill.getPayed().compareTo(BigDecimal.ZERO) == 0 ? BillStatus.UNPAID : BillStatus.PARTLY_PAID);
        bill.setAutoPayed(false);
        bill.setHistoryPayedCashBox(bill.getPayedCashBox());
        bill.setPayedCashBox(BigDecimal.ZERO);
        saveBill(bill);
        log.info("Success reset Bill");
    }

    private void BillCalculate(Bill saveBill) {
        log.info("Try to Calculate Bill by Save");
        if (saveBill.getDraft()) {
            bankBookService.calculateBankBook(saveBill.getBankBook().getId(), saveBill.getTotalPrice().subtract(saveBill.getPayed()), false, saveBill);
        }
        log.info("Success Calculate Bill by Save");
    }

    private String generateNumber() {
        log.info("Try to generate Number");
        List<Bill> bills = billRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        if (bills.isEmpty()) {
            log.warn("Bills not found");
            return String.format("%010d", 1);
        }
        Bill bill = bills.get(0);

        String stringNumber = bill.getNumber();
        long number;

        do {
            number = Long.parseLong(stringNumber);
            number++;
            stringNumber = String.format("%010d", number);
        } while (billRepository.existsByNumber(stringNumber));
        log.info("Success generate Number");
        return stringNumber;
    }

    private LocalDate getTodayDate() {
        return Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    @Override
    public BigDecimal sumOfAllBills() {
        List<Bill> all = billRepository.findAll();
        BigDecimal totalSum = BigDecimal.ZERO;
        for (Bill bill: all){
            totalSum = totalSum.add(bill.getTotalPrice());
        }
        return totalSum;
    }

    @Override
    public List<BigDecimal> sumOffAllBillsByMonths() {
        List<BigDecimal> bigDecimalList = new ArrayList<>();
        for (int i = 1; i<13;i++){
            BigDecimal sum = BigDecimal.ZERO;
            for (Bill bill: billRepository.findAll()){
                if (bill.getCreateAt().atZone(ZoneId.systemDefault()).getMonth().getValue() == i){
                    sum = sum.add(bill.getTotalPrice());
                }
            }
            bigDecimalList.add(sum);
        }
        return bigDecimalList;
    }

    @Override
    public List<BigDecimal> sumOffAllPaidBillsByMonths() {
        List<BigDecimal> bigDecimalList = new ArrayList<>();
        List<Bill> bills = billRepository.findAllByStatus(BillStatus.PAID);
        for (int i = 1; i<13;i++){
            BigDecimal sum = BigDecimal.ZERO;
            for (Bill bill: bills){
                if (bill.getCreateAt().atZone(ZoneId.systemDefault()).getMonth().getValue() == i){
                    sum = sum.add(bill.getTotalPrice());
                }
            }
            bigDecimalList.add(sum);
        }
        return bigDecimalList;
    }
}

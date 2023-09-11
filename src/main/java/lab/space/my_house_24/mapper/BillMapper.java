package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.entity.Bill;
import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.model.bill.BillResponse;
import lab.space.my_house_24.model.bill.BillSaveRequest;
import lab.space.my_house_24.model.bill.BillUpdateRequest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import static java.util.Objects.nonNull;

public interface BillMapper {

    static BillResponse toBillResponse(String number, LocalDate date) {
        return BillResponse.builder()
                .number(number)
                .createAt(date)
                .build();
    }

    static BillResponse toBillResponse(Bill bill) {
        return BillResponse.builder()
                .id(bill.getId())
                .number(bill.getNumber())
                .createAt(bill.getCreateAt().atZone(ZoneId.systemDefault()).toLocalDate())
                .status(EnumMapper.toSimpleDto(
                        bill.getStatus().name(),
                        bill.getStatus().getBillStatus(LocaleContextHolder.getLocale()))
                )
                .rate(nonNull(bill.getRate()) ? RateMapper.toRateResponseForBill(bill.getRate()) : null)
                .draft(bill.getDraft())
                .totalPrice(bill.getTotalPrice())
                .payed(bill.getPayed())
                .bankBook(BankBookMapper.toBankBookResponse(bill.getBankBook()))
                .periodOf(bill.getPeriodOf().atZone(ZoneId.systemDefault()).toLocalDate())
                .periodTo(bill.getPeriodTo().atZone(ZoneId.systemDefault()).toLocalDate())
                .services(ServiceBillMapper.toServiceBillResponseList(bill.getServiceBillList()))
                .month(bill.getPeriodOf().atZone(ZoneId.systemDefault()).getMonth() + ", " + bill.getPeriodOf().atZone(ZoneId.systemDefault()).getYear())
                .build();
    }


    static Bill toBill(BillSaveRequest request, BankBook bankBook, Rate rate) {
        return Bill.builder()
                .number(request.number())
                .createAt(request.createAt().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
                .bankBook(bankBook)
                .draft(request.draft())
                .status(request.status())
                .rate(rate)
                .totalPrice(request.totalPrice())
                .periodOf(request.periodOf().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
                .periodTo(request.periodTo().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
                .isActive(request.draft())
                .autoPayed(false)
                .payedCashBox(BigDecimal.ZERO)
                .historyPayedCashBox(BigDecimal.ZERO)
                .payed(request.payed())
                .build();
    }

    static Bill toBill(Bill bill, BillUpdateRequest request, BankBook bankBook, Rate rate) {
        bill
                .setNumber(request.number())
                .setCreateAt(request.createAt().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
                .setBankBook(bankBook)
                .setDraft(request.draft())
                .setStatus(request.status())
                .setRate(rate)
                .setTotalPrice(request.totalPrice())
                .setPayed(request.payed())
                .setPeriodOf(request.periodOf().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
                .setPeriodTo(request.periodTo().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime());

        if (!bill.getIsActive() && request.draft()){
            bill.setIsActive(true);
        }
        return bill;
    }

}

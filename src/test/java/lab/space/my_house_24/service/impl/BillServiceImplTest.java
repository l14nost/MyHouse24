package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.enums.BankBookStatus;
import lab.space.my_house_24.enums.BillStatus;
import lab.space.my_house_24.model.bill.*;
import lab.space.my_house_24.model.service_bill.ServiceBillRequest;
import lab.space.my_house_24.repository.BillRepository;
import lab.space.my_house_24.service.BankBookService;
import lab.space.my_house_24.service.ExcelService;
import lab.space.my_house_24.service.RateService;
import lab.space.my_house_24.service.ServiceBillService;
import lab.space.my_house_24.specification.BillSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillServiceImplTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private ServiceBillService serviceBillService;

    @Mock
    private BankBookService bankBookService;

    @Mock
    private RateService rateService;

    @Mock
    private BillSpecification billSpecification;

    @Mock
    private MessageSource message;

    @Mock
    private ExcelService excelService;

    @InjectMocks
    private BillServiceImpl billService;

    @Test
    void getBillById() {
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(Bill.builder().build()));
        assertEquals(Bill.builder().build(), billService.getBillById(1L));
    }

    @Test
    void getBillResponseById() {
        Bill bill = Bill.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .status(BillStatus.UNPAID)
                .rate(Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build())
                .draft(true)
                .isActive(true)
                .totalPrice(BigDecimal.ONE)
                .payed(BigDecimal.ZERO)
                .bankBook(BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build())
                .periodOf(LocalDateTime.now())
                .periodTo(LocalDateTime.now())
                .serviceBillList(List.of(ServiceBill.builder()
                        .id(1L)
                        .count(123.4)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L)
                                .name("Test")
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()))
                .build();
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));
        assertEquals(1L, billService.getBillResponseById(1L).id());
    }

    @Test
    void getAllBillResponse() {
        Page<Bill> bills = new PageImpl<>(
                List.of(
                        Bill.builder()
                                .id(1L)
                                .number("0123456789")
                                .createAt(LocalDateTime.now())
                                .status(BillStatus.UNPAID)
                                .rate(Rate.builder()
                                        .id(1L)
                                        .name("Test")
                                        .priceRateList(List.of(
                                                PriceRate.builder()
                                                        .id(1L)
                                                        .price(BigDecimal.ZERO)
                                                        .service(Service.builder()
                                                                .id(1L)
                                                                .name("Test")
                                                                .unit(Unit.builder()
                                                                        .id(1L)
                                                                        .name("Test")
                                                                        .build())
                                                                .build())
                                                        .build()
                                        ))
                                        .build())
                                .draft(true)
                                .isActive(true)
                                .totalPrice(BigDecimal.ONE)
                                .payed(BigDecimal.ZERO)
                                .bankBook(BankBook.builder()
                                        .id(1L)
                                        .number("01234-56789")
                                        .bankBookStatus(BankBookStatus.ACTIVE)
                                        .totalPrice(BigDecimal.ZERO)
                                        .apartment(Apartment.builder()
                                                .id(1L)
                                                .number(1234)
                                                .house(House.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .section(Section.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .user(User.builder()
                                                        .id(1L)
                                                        .firstname("Test")
                                                        .surname("Test")
                                                        .lastname("Test")
                                                        .number("0123456789")
                                                        .build())
                                                .build())
                                        .build())
                                .periodOf(LocalDateTime.now())
                                .periodTo(LocalDateTime.now())
                                .serviceBillList(List.of(ServiceBill.builder()
                                        .id(1L)
                                        .count(123.4)
                                        .price(BigDecimal.ZERO)
                                        .totalPrice(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()))
                                .build(),
                        Bill.builder()
                                .id(1L)
                                .number("0123456789")
                                .createAt(LocalDateTime.now())
                                .status(BillStatus.UNPAID)
                                .rate(Rate.builder()
                                        .id(1L)
                                        .name("Test")
                                        .priceRateList(List.of(
                                                PriceRate.builder()
                                                        .id(1L)
                                                        .price(BigDecimal.ZERO)
                                                        .service(Service.builder()
                                                                .id(1L)
                                                                .name("Test")
                                                                .unit(Unit.builder()
                                                                        .id(1L)
                                                                        .name("Test")
                                                                        .build())
                                                                .build())
                                                        .build()
                                        ))
                                        .build())
                                .draft(true)
                                .isActive(true)
                                .totalPrice(BigDecimal.ONE)
                                .payed(BigDecimal.ZERO)
                                .bankBook(BankBook.builder()
                                        .id(1L)
                                        .number("01234-56789")
                                        .bankBookStatus(BankBookStatus.ACTIVE)
                                        .totalPrice(BigDecimal.ZERO)
                                        .apartment(Apartment.builder()
                                                .id(1L)
                                                .number(1234)
                                                .house(House.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .section(Section.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .user(User.builder()
                                                        .id(1L)
                                                        .firstname("Test")
                                                        .surname("Test")
                                                        .lastname("Test")
                                                        .number("0123456789")
                                                        .build())
                                                .build())
                                        .build())
                                .periodOf(LocalDateTime.now())
                                .periodTo(LocalDateTime.now())
                                .serviceBillList(List.of(ServiceBill.builder()
                                        .id(1L)
                                        .count(123.4)
                                        .price(BigDecimal.ZERO)
                                        .totalPrice(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()))
                                .build()
                )
        );
        when(billRepository.findAll((Specification<Bill>) any(), any(PageRequest.class))).thenReturn(bills);
        Page<BillResponse> billResponses = billService.getAllBillResponse(BillRequest.builder().pageIndex(1).build());
        verify(billRepository, times(1)).findAll((Specification<Bill>) any(), any(PageRequest.class));
        assertEquals(2, billResponses.getTotalElements());
        assertEquals(BillResponse.class, billResponses.iterator().next().getClass());
    }

    @Test
    void getDraft() {
        assertEquals(2, billService.getDraft().size());
    }

    @Test
    void updateBillByRequest() {
        Bill bill = Bill.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .status(BillStatus.UNPAID)
                .rate(Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build())
                .draft(true)
                .isActive(true)
                .totalPrice(BigDecimal.ONE)
                .payed(BigDecimal.ONE)
                .autoPayed(true)
                .payedCashBox(BigDecimal.ONE)
                .bankBook(BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build())
                .periodOf(LocalDateTime.now())
                .periodTo(LocalDateTime.now())
                .serviceBillList(List.of(ServiceBill.builder()
                        .id(1L)
                        .count(123.4)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L)
                                .name("Test")
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()))
                .build();

        BillUpdateRequest request = BillUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .bankBookId(1L)
                .draft(true)
                .status(BillStatus.PARTLY_PAID)
                .rateId(1L)
                .totalPrice(BigDecimal.TEN)
                .payed(BigDecimal.ONE)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.ONE)
                        .totalPrice(BigDecimal.ONE)
                        .count(1.00)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        when(bankBookService.getBankBookById(anyLong())).thenReturn(
                BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build());
        when(rateService.getRateById(anyLong())).thenReturn(
                Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build()
        );
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));

        billService.updateBillByRequest(request);
        verify(bankBookService, times(1)).getBankBookById(anyLong());
        verify(rateService, times(1)).getRateById(anyLong());
        verify(billRepository, times(1)).save(any());
    }

    @Test
    void updateBillByRequestFalseDraft() {
        Bill bill = Bill.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .status(BillStatus.UNPAID)
                .rate(Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build())
                .draft(false)
                .isActive(true)
                .totalPrice(BigDecimal.ONE)
                .payed(BigDecimal.ONE)
                .autoPayed(true)
                .payedCashBox(BigDecimal.ONE)
                .bankBook(BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build())
                .periodOf(LocalDateTime.now())
                .periodTo(LocalDateTime.now())
                .serviceBillList(List.of(ServiceBill.builder()
                        .id(1L)
                        .count(123.4)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L)
                                .name("Test")
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()))
                .build();

        BillUpdateRequest request = BillUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .bankBookId(1L)
                .draft(true)
                .status(BillStatus.PARTLY_PAID)
                .rateId(1L)
                .totalPrice(BigDecimal.TEN)
                .payed(BigDecimal.ONE)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.ONE)
                        .totalPrice(BigDecimal.ONE)
                        .count(1.00)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        when(bankBookService.getBankBookById(anyLong())).thenReturn(
                BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build());
        when(rateService.getRateById(anyLong())).thenReturn(
                Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build()
        );
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));

        billService.updateBillByRequest(request);
        verify(bankBookService, times(1)).getBankBookById(anyLong());
        verify(rateService, times(1)).getRateById(anyLong());
        verify(billRepository, times(1)).save(any());
    }

    @Test
    void updateBillByRequestTrueDraft() {
        Bill bill = Bill.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .status(BillStatus.UNPAID)
                .rate(Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build())
                .draft(true)
                .isActive(true)
                .totalPrice(BigDecimal.TEN)
                .payed(BigDecimal.ONE)
                .autoPayed(true)
                .payedCashBox(BigDecimal.ONE)
                .bankBook(BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build())
                .periodOf(LocalDateTime.now())
                .periodTo(LocalDateTime.now())
                .serviceBillList(List.of(ServiceBill.builder()
                        .id(1L)
                        .count(123.4)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L)
                                .name("Test")
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()))
                .build();

        BillUpdateRequest request = BillUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .bankBookId(1L)
                .draft(false)
                .status(BillStatus.PARTLY_PAID)
                .rateId(1L)
                .totalPrice(BigDecimal.TEN)
                .payed(BigDecimal.ONE)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.ONE)
                        .totalPrice(BigDecimal.ONE)
                        .count(1.00)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        when(bankBookService.getBankBookById(anyLong())).thenReturn(
                BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build());
        when(rateService.getRateById(anyLong())).thenReturn(
                Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build()
        );
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));

        billService.updateBillByRequest(request);
        verify(bankBookService, times(1)).getBankBookById(anyLong());
        verify(rateService, times(1)).getRateById(anyLong());
        verify(billRepository, times(2)).save(any());
    }

    @Test
    void updateBillByRequestTrueDraftAnyPayed() {
        Bill bill = Bill.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .status(BillStatus.UNPAID)
                .rate(Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build())
                .draft(true)
                .isActive(true)
                .totalPrice(BigDecimal.ONE)
                .payed(BigDecimal.ONE)
                .autoPayed(true)
                .payedCashBox(BigDecimal.ONE)
                .bankBook(BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build())
                .periodOf(LocalDateTime.now())
                .periodTo(LocalDateTime.now())
                .serviceBillList(List.of(ServiceBill.builder()
                        .id(1L)
                        .count(123.4)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L)
                                .name("Test")
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()))
                .build();

        BillUpdateRequest request = BillUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .bankBookId(1L)
                .draft(false)
                .status(BillStatus.PARTLY_PAID)
                .rateId(1L)
                .totalPrice(BigDecimal.TEN)
                .payed(BigDecimal.ONE)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.TEN)
                        .totalPrice(BigDecimal.TEN)
                        .count(1.00)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        when(bankBookService.getBankBookById(anyLong())).thenReturn(
                BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build());
        when(rateService.getRateById(anyLong())).thenReturn(
                Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build()
        );
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));

        billService.updateBillByRequest(request);
        verify(bankBookService, times(1)).getBankBookById(anyLong());
        verify(rateService, times(1)).getRateById(anyLong());
        verify(billRepository, times(2)).save(any());
    }

    @Test
    void updateBillByRequestAnyId() {
        Bill bill = Bill.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .status(BillStatus.UNPAID)
                .rate(Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build())
                .draft(true)
                .isActive(true)
                .totalPrice(BigDecimal.TEN)
                .payed(BigDecimal.ONE)
                .autoPayed(true)
                .payedCashBox(BigDecimal.ONE)
                .bankBook(BankBook.builder()
                        .id(2L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build())
                .periodOf(LocalDateTime.now())
                .periodTo(LocalDateTime.now())
                .serviceBillList(List.of(ServiceBill.builder()
                        .id(1L)
                        .count(123.4)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L)
                                .name("Test")
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()))
                .build();

        BillUpdateRequest request = BillUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .bankBookId(1L)
                .draft(true)
                .status(BillStatus.PARTLY_PAID)
                .rateId(1L)
                .totalPrice(BigDecimal.TEN)
                .payed(BigDecimal.ONE)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.ONE)
                        .totalPrice(BigDecimal.ONE)
                        .count(1.00)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        when(bankBookService.getBankBookById(anyLong())).thenReturn(
                BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build());
        when(rateService.getRateById(anyLong())).thenReturn(
                Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build()
        );
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));

        billService.updateBillByRequest(request);
        verify(bankBookService, times(1)).getBankBookById(anyLong());
        verify(rateService, times(1)).getRateById(anyLong());
        verify(billRepository, times(2)).save(any());
    }

    @Test
    void updateBillByRequestFalseDraftAnyId() {
        Bill bill = Bill.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .status(BillStatus.UNPAID)
                .rate(Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build())
                .draft(false)
                .isActive(true)
                .totalPrice(BigDecimal.ONE)
                .payed(BigDecimal.ONE)
                .autoPayed(true)
                .payedCashBox(BigDecimal.ONE)
                .bankBook(BankBook.builder()
                        .id(2L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build())
                .periodOf(LocalDateTime.now())
                .periodTo(LocalDateTime.now())
                .serviceBillList(List.of(ServiceBill.builder()
                        .id(1L)
                        .count(123.4)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L)
                                .name("Test")
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()))
                .build();

        BillUpdateRequest request = BillUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .bankBookId(1L)
                .draft(true)
                .status(BillStatus.PARTLY_PAID)
                .rateId(1L)
                .totalPrice(BigDecimal.TEN)
                .payed(BigDecimal.ONE)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.ONE)
                        .totalPrice(BigDecimal.ONE)
                        .count(1.00)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        when(bankBookService.getBankBookById(anyLong())).thenReturn(
                BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build());
        when(rateService.getRateById(anyLong())).thenReturn(
                Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build()
        );
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));

        billService.updateBillByRequest(request);
        verify(bankBookService, times(1)).getBankBookById(anyLong());
        verify(rateService, times(1)).getRateById(anyLong());
        verify(billRepository, times(1)).save(any());
    }

    @Test
    void updateBillByRequestTrueDraftAnyId() {
        Bill bill = Bill.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .status(BillStatus.UNPAID)
                .rate(Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build())
                .draft(true)
                .isActive(true)
                .totalPrice(BigDecimal.TEN)
                .payed(BigDecimal.ONE)
                .autoPayed(true)
                .payedCashBox(BigDecimal.ONE)
                .bankBook(BankBook.builder()
                        .id(2L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build())
                .periodOf(LocalDateTime.now())
                .periodTo(LocalDateTime.now())
                .serviceBillList(List.of(ServiceBill.builder()
                        .id(1L)
                        .count(123.4)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L)
                                .name("Test")
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()))
                .build();

        BillUpdateRequest request = BillUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .bankBookId(1L)
                .draft(false)
                .status(BillStatus.PARTLY_PAID)
                .rateId(1L)
                .totalPrice(BigDecimal.TEN)
                .payed(BigDecimal.ONE)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.ONE)
                        .totalPrice(BigDecimal.ONE)
                        .count(1.00)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        when(bankBookService.getBankBookById(anyLong())).thenReturn(
                BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build());
        when(rateService.getRateById(anyLong())).thenReturn(
                Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build()
        );
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));

        billService.updateBillByRequest(request);
        verify(bankBookService, times(1)).getBankBookById(anyLong());
        verify(rateService, times(1)).getRateById(anyLong());
        verify(billRepository, times(2)).save(any());
    }

    @Test
    void updateBillByRequestAnyPayedAnyId() {
        Bill bill = Bill.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .status(BillStatus.UNPAID)
                .rate(Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build())
                .draft(true)
                .isActive(true)
                .totalPrice(BigDecimal.TEN)
                .payed(BigDecimal.ONE)
                .autoPayed(true)
                .payedCashBox(BigDecimal.ONE)
                .bankBook(BankBook.builder()
                        .id(2L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build())
                .periodOf(LocalDateTime.now())
                .periodTo(LocalDateTime.now())
                .serviceBillList(List.of(ServiceBill.builder()
                        .id(1L)
                        .count(123.4)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L)
                                .name("Test")
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()))
                .build();

        BillUpdateRequest request = BillUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .bankBookId(1L)
                .draft(true)
                .status(BillStatus.PAID)
                .rateId(1L)
                .totalPrice(BigDecimal.TEN)
                .payed(BigDecimal.ONE.add(BigDecimal.ONE))
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.TEN)
                        .totalPrice(BigDecimal.TEN)
                        .count(1.00)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        when(bankBookService.getBankBookById(anyLong())).thenReturn(
                BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build());
        when(rateService.getRateById(anyLong())).thenReturn(
                Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build()
        );
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));

        billService.updateBillByRequest(request);
        verify(bankBookService, times(1)).getBankBookById(anyLong());
        verify(rateService, times(1)).getRateById(anyLong());
        verify(billRepository, times(2)).save(any());
    }

    @Test
    void saveBillByRequest() {
        BillSaveRequest request = BillSaveRequest.builder()
                .number("0123456789")
                .createAt(LocalDate.now())
                .bankBookId(1L)
                .draft(true)
                .status(BillStatus.PAID)
                .rateId(1L)
                .totalPrice(BigDecimal.ONE)
                .payed(BigDecimal.ONE)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.ONE)
                        .totalPrice(BigDecimal.ONE)
                        .count(1.00)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        when(bankBookService.getBankBookById(anyLong())).thenReturn(
                BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build());
        when(rateService.getRateById(anyLong())).thenReturn(
                Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build()
        );
        billService.saveBillByRequest(request);
        verify(bankBookService, times(1)).getBankBookById(anyLong());
        verify(rateService, times(1)).getRateById(anyLong());
        verify(billRepository, times(1)).save(any());
    }

    @Test
    void saveBill() {
        assertEquals(Bill.builder().build(), billService.saveBill(Bill.builder().build()));
    }

    @Test
    void deleteBillByRequest() {
        List<BillDeleteRequest> request = List.of(
                BillDeleteRequest.builder().id(1L).value(true).build(),
                BillDeleteRequest.builder().id(2L).value(true).build(),
                BillDeleteRequest.builder().id(3L).value(true).build()
        );
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(Bill.builder()
                .autoPayed(false)
                .isActive(false)
                .status(BillStatus.UNPAID)
                .payed(BigDecimal.ZERO)
                .build()));
        billService.deleteBillByRequest(request);
        verify(billRepository, times(3)).findById(anyLong());
        verify(billRepository, times(1)).deleteAll(any());
    }

    @Test
    void deleteBillByRequestIsActive() {
        List<BillDeleteRequest> request = List.of(
                BillDeleteRequest.builder().id(1L).value(true).build(),
                BillDeleteRequest.builder().id(2L).value(true).build(),
                BillDeleteRequest.builder().id(3L).value(true).build()
        );
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(Bill.builder()
                .autoPayed(true)
                .isActive(true)
                .status(BillStatus.UNPAID)
                .payed(BigDecimal.ZERO)
                .build()));
        assertThrows(IllegalArgumentException.class, () -> billService.deleteBillByRequest(request));
        verify(billRepository, times(3)).findById(anyLong());
        verify(billRepository, times(0)).deleteAll(any());
    }

    @Test
    void deleteBillByRequestAnyPayed() {
        List<BillDeleteRequest> request = List.of(
                BillDeleteRequest.builder().id(1L).value(true).build(),
                BillDeleteRequest.builder().id(2L).value(true).build(),
                BillDeleteRequest.builder().id(3L).value(true).build()
        );
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(Bill.builder()
                .autoPayed(false)
                .isActive(false)
                .status(BillStatus.PAID)
                .payed(BigDecimal.ONE)
                .build()));
        assertThrows(IllegalArgumentException.class, () -> billService.deleteBillByRequest(request));
        verify(billRepository, times(3)).findById(anyLong());
        verify(billRepository, times(0)).deleteAll(any());
    }

    @Test
    void deleteBillById() {
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(Bill.builder()
                .autoPayed(false)
                .isActive(false)
                .status(BillStatus.UNPAID)
                .payed(BigDecimal.ZERO)
                .build()));
        billService.deleteBillById(1L);
        verify(billRepository, times(1)).findById(anyLong());
        verify(billRepository, times(1)).delete((Bill) any());
    }

    @Test
    void deleteBillByIdIsActive() {
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(Bill.builder()
                .autoPayed(false)
                .isActive(false)
                .status(BillStatus.PARTLY_PAID)
                .payed(BigDecimal.ONE)
                .build()));
        assertThrows(IllegalArgumentException.class, () -> billService.deleteBillById(1L));
        verify(billRepository, times(1)).findById(anyLong());
        verify(billRepository, times(0)).delete((Bill) any());
    }

    @Test
    void deleteBillByIdAnyPayed() {
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(Bill.builder()
                .autoPayed(true)
                .isActive(true)
                .status(BillStatus.PARTLY_PAID)
                .payed(BigDecimal.ONE)
                .build()));
        assertThrows(IllegalArgumentException.class, () -> billService.deleteBillById(1L));
        verify(billRepository, times(1)).findById(anyLong());
        verify(billRepository, times(0)).delete((Bill) any());
    }

    @Test
    void getAllBillStatus() {
        assertEquals(3, billService.getAllBillStatus().size());
    }

    @Test
    void getExcel() throws IOException {
        Bill bill = Bill.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .status(BillStatus.UNPAID)
                .rate(Rate.builder()
                        .id(1L)
                        .name("Test")
                        .priceRateList(List.of(
                                PriceRate.builder()
                                        .id(1L)
                                        .price(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build())
                .draft(true)
                .isActive(true)
                .totalPrice(BigDecimal.ONE)
                .payed(BigDecimal.ZERO)
                .bankBook(BankBook.builder()
                        .id(1L)
                        .number("01234-56789")
                        .bankBookStatus(BankBookStatus.ACTIVE)
                        .totalPrice(BigDecimal.ZERO)
                        .apartment(Apartment.builder()
                                .id(1L)
                                .number(1234)
                                .house(House.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .section(Section.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .user(User.builder()
                                        .id(1L)
                                        .firstname("Test")
                                        .surname("Test")
                                        .lastname("Test")
                                        .number("0123456789")
                                        .build())
                                .build())
                        .build())
                .periodOf(LocalDateTime.now())
                .periodTo(LocalDateTime.now())
                .serviceBillList(List.of(ServiceBill.builder()
                        .id(1L)
                        .count(123.4)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L)
                                .name("Test")
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()))
                .build();
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));
        InputStreamResource excel = billService.getExcel(1L);
        assertNotNull(excel);
    }

    @Test
    public void testGetExcel() throws IOException {
        Page<Bill> bills = new PageImpl<>(
                List.of(
                        Bill.builder()
                                .id(1L)
                                .number("0123456789")
                                .createAt(LocalDateTime.now())
                                .status(BillStatus.UNPAID)
                                .rate(Rate.builder()
                                        .id(1L)
                                        .name("Test")
                                        .priceRateList(List.of(
                                                PriceRate.builder()
                                                        .id(1L)
                                                        .price(BigDecimal.ZERO)
                                                        .service(Service.builder()
                                                                .id(1L)
                                                                .name("Test")
                                                                .unit(Unit.builder()
                                                                        .id(1L)
                                                                        .name("Test")
                                                                        .build())
                                                                .build())
                                                        .build()
                                        ))
                                        .build())
                                .draft(true)
                                .isActive(true)
                                .totalPrice(BigDecimal.ONE)
                                .payed(BigDecimal.ZERO)
                                .bankBook(BankBook.builder()
                                        .id(1L)
                                        .number("01234-56789")
                                        .bankBookStatus(BankBookStatus.ACTIVE)
                                        .totalPrice(BigDecimal.ZERO)
                                        .apartment(Apartment.builder()
                                                .id(1L)
                                                .number(1234)
                                                .house(House.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .section(Section.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .user(User.builder()
                                                        .id(1L)
                                                        .firstname("Test")
                                                        .surname("Test")
                                                        .lastname("Test")
                                                        .number("0123456789")
                                                        .build())
                                                .build())
                                        .build())
                                .periodOf(LocalDateTime.now())
                                .periodTo(LocalDateTime.now())
                                .serviceBillList(List.of(ServiceBill.builder()
                                        .id(1L)
                                        .count(123.4)
                                        .price(BigDecimal.ZERO)
                                        .totalPrice(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()))
                                .build(),
                        Bill.builder()
                                .id(1L)
                                .number("0123456789")
                                .createAt(LocalDateTime.now())
                                .status(BillStatus.UNPAID)
                                .rate(Rate.builder()
                                        .id(1L)
                                        .name("Test")
                                        .priceRateList(List.of(
                                                PriceRate.builder()
                                                        .id(1L)
                                                        .price(BigDecimal.ZERO)
                                                        .service(Service.builder()
                                                                .id(1L)
                                                                .name("Test")
                                                                .unit(Unit.builder()
                                                                        .id(1L)
                                                                        .name("Test")
                                                                        .build())
                                                                .build())
                                                        .build()
                                        ))
                                        .build())
                                .draft(true)
                                .isActive(true)
                                .totalPrice(BigDecimal.ONE)
                                .payed(BigDecimal.ZERO)
                                .bankBook(BankBook.builder()
                                        .id(1L)
                                        .number("01234-56789")
                                        .bankBookStatus(BankBookStatus.ACTIVE)
                                        .totalPrice(BigDecimal.ZERO)
                                        .apartment(Apartment.builder()
                                                .id(1L)
                                                .number(1234)
                                                .house(House.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .section(Section.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .user(User.builder()
                                                        .id(1L)
                                                        .firstname("Test")
                                                        .surname("Test")
                                                        .lastname("Test")
                                                        .number("0123456789")
                                                        .build())
                                                .build())
                                        .build())
                                .periodOf(LocalDateTime.now())
                                .periodTo(LocalDateTime.now())
                                .serviceBillList(List.of(ServiceBill.builder()
                                        .id(1L)
                                        .count(123.4)
                                        .price(BigDecimal.ZERO)
                                        .totalPrice(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()))
                                .build()
                )
        );
        when(billRepository.findAll((Specification<Bill>) any(), any(PageRequest.class))).thenReturn(bills);
        BillRequest request = BillRequest.builder().pageIndex(1).build();
        InputStreamResource excel = billService.getExcel(request);
        assertNotNull(excel);
    }

    @Test
    public void testGetExcelWithIOException() throws Exception {
        Page<Bill> bills = new PageImpl<>(
                List.of(
                        Bill.builder()
                                .id(1L)
                                .number("0123456789")
                                .createAt(LocalDateTime.now())
                                .status(BillStatus.UNPAID)
                                .rate(Rate.builder()
                                        .id(1L)
                                        .name("Test")
                                        .priceRateList(List.of(
                                                PriceRate.builder()
                                                        .id(1L)
                                                        .price(BigDecimal.ZERO)
                                                        .service(Service.builder()
                                                                .id(1L)
                                                                .name("Test")
                                                                .unit(Unit.builder()
                                                                        .id(1L)
                                                                        .name("Test")
                                                                        .build())
                                                                .build())
                                                        .build()
                                        ))
                                        .build())
                                .draft(true)
                                .isActive(true)
                                .totalPrice(BigDecimal.ONE)
                                .payed(BigDecimal.ZERO)
                                .bankBook(BankBook.builder()
                                        .id(1L)
                                        .number("01234-56789")
                                        .bankBookStatus(BankBookStatus.ACTIVE)
                                        .totalPrice(BigDecimal.ZERO)
                                        .apartment(Apartment.builder()
                                                .id(1L)
                                                .number(1234)
                                                .house(House.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .section(Section.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .user(User.builder()
                                                        .id(1L)
                                                        .firstname("Test")
                                                        .surname("Test")
                                                        .lastname("Test")
                                                        .number("0123456789")
                                                        .build())
                                                .build())
                                        .build())
                                .periodOf(LocalDateTime.now())
                                .periodTo(LocalDateTime.now())
                                .serviceBillList(List.of(ServiceBill.builder()
                                        .id(1L)
                                        .count(123.4)
                                        .price(BigDecimal.ZERO)
                                        .totalPrice(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()))
                                .build(),
                        Bill.builder()
                                .id(1L)
                                .number("0123456789")
                                .createAt(LocalDateTime.now())
                                .status(BillStatus.UNPAID)
                                .rate(Rate.builder()
                                        .id(1L)
                                        .name("Test")
                                        .priceRateList(List.of(
                                                PriceRate.builder()
                                                        .id(1L)
                                                        .price(BigDecimal.ZERO)
                                                        .service(Service.builder()
                                                                .id(1L)
                                                                .name("Test")
                                                                .unit(Unit.builder()
                                                                        .id(1L)
                                                                        .name("Test")
                                                                        .build())
                                                                .build())
                                                        .build()
                                        ))
                                        .build())
                                .draft(true)
                                .isActive(true)
                                .totalPrice(BigDecimal.ONE)
                                .payed(BigDecimal.ZERO)
                                .bankBook(BankBook.builder()
                                        .id(1L)
                                        .number("01234-56789")
                                        .bankBookStatus(BankBookStatus.ACTIVE)
                                        .totalPrice(BigDecimal.ZERO)
                                        .apartment(Apartment.builder()
                                                .id(1L)
                                                .number(1234)
                                                .house(House.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .section(Section.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .user(User.builder()
                                                        .id(1L)
                                                        .firstname("Test")
                                                        .surname("Test")
                                                        .lastname("Test")
                                                        .number("0123456789")
                                                        .build())
                                                .build())
                                        .build())
                                .periodOf(LocalDateTime.now())
                                .periodTo(LocalDateTime.now())
                                .serviceBillList(List.of(ServiceBill.builder()
                                        .id(1L)
                                        .count(123.4)
                                        .price(BigDecimal.ZERO)
                                        .totalPrice(BigDecimal.ZERO)
                                        .service(Service.builder()
                                                .id(1L)
                                                .name("Test")
                                                .unit(Unit.builder()
                                                        .id(1L)
                                                        .name("Test")
                                                        .build())
                                                .build())
                                        .build()))
                                .build()
                )
        );
        when(billRepository.findAll((Specification<Bill>) any(), any(PageRequest.class))).thenReturn(bills);
//        Workbook workbook = mock(Workbook.class);
//
//        doThrow(new IOException("Test IO Exception")).when(workbook).write(any());
        BillRequest request = BillRequest.builder().pageIndex(1).build();
//        assertThrows(IOException.class, () -> billService.getExcel(request));
        billService.getExcel(request);
        verify(billRepository, times(1)).findAll((Specification<Bill>) any(), any(PageRequest.class));
    }

    @Test
    void getNewBillResponse() {
        when(billRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(List.of(
                Bill.builder().number("0000000005").build(),
                Bill.builder().number("0000000004").build(),
                Bill.builder().number("0000000003").build(),
                Bill.builder().number("0000000002").build(),
                Bill.builder().number("0000000001").build()
        ));
        assertEquals("0000000006", billService.getNewBillResponse().number());
        verify(billRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Test
    void getNewBillResponseEmptyList() {
        when(billRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(List.of());
        assertEquals("0000000001", billService.getNewBillResponse().number());
        verify(billRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Test
    void sumOfAllBills() {
        when(billRepository.findAll()).thenReturn(List.of(
                        Bill.builder().createAt(LocalDateTime.of(2023, 1, 1, 14, 10)).totalPrice(BigDecimal.ONE).build(),
                        Bill.builder().createAt(LocalDateTime.of(2023, 1, 1, 14, 10)).totalPrice(BigDecimal.ONE).build(),
                        Bill.builder().createAt(LocalDateTime.of(2023, 2, 1, 14, 10)).totalPrice(BigDecimal.ONE).build()
                )
        );
        BigDecimal number = billService.sumOfAllBills();
        verify(billRepository, times(1)).findAll();
        assertEquals("3", number.toString());
    }

    @Test
    void sumOffAllBillsByMonths() {
        when(billRepository.findAll()).thenReturn(List.of(
                        Bill.builder().createAt(LocalDateTime.of(2023, 1, 1, 14, 10)).totalPrice(BigDecimal.ONE).build(),
                        Bill.builder().createAt(LocalDateTime.of(2023, 1, 1, 14, 10)).totalPrice(BigDecimal.ONE).build(),
                        Bill.builder().createAt(LocalDateTime.of(2023, 2, 1, 14, 10)).totalPrice(BigDecimal.ONE).build()
                )
        );
        List<BigDecimal> list = billService.sumOffAllBillsByMonths();
        verify(billRepository, times(1)).findAll();
        assertEquals("2", list.get(0).toString());
        assertEquals("1", list.get(1).toString());
        assertEquals("0", list.get(2).toString());
    }

    @Test
    void sumOffAllPaidBillsByMonths() {
        when(billRepository.findAllByStatus(BillStatus.PAID)).thenReturn(List.of(
                        Bill.builder().createAt(LocalDateTime.of(2023, 1, 1, 14, 10)).totalPrice(BigDecimal.ONE).build(),
                        Bill.builder().createAt(LocalDateTime.of(2023, 1, 1, 14, 10)).totalPrice(BigDecimal.ONE).build(),
                        Bill.builder().createAt(LocalDateTime.of(2023, 2, 1, 14, 10)).totalPrice(BigDecimal.ONE).build()
                )
        );
        List<BigDecimal> list = billService.sumOffAllPaidBillsByMonths();
        verify(billRepository, times(1)).findAllByStatus(BillStatus.PAID);
        assertEquals("2", list.get(0).toString());
        assertEquals("1", list.get(1).toString());
        assertEquals("0", list.get(2).toString());
    }
}
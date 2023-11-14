package lab.space.my_house_24.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lab.space.my_house_24.model.apartment.ApartmentResponseForBankBook;
import lab.space.my_house_24.model.apartment.ApartmentResponseForBill;
import lab.space.my_house_24.model.bankBook.BankBookResponse;
import lab.space.my_house_24.model.bill.BillDeleteRequest;
import lab.space.my_house_24.model.bill.BillRequest;
import lab.space.my_house_24.model.bill.BillSaveRequest;
import lab.space.my_house_24.model.bill.BillUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.meterReading.MeterReadingRequestForBill;
import lab.space.my_house_24.model.meterReading.MeterReadingResponseForBill;
import lab.space.my_house_24.model.rate.RateResponse;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.service.ServiceResponse;
import lab.space.my_house_24.model.user.UserResponseForTable;
import lab.space.my_house_24.service.*;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.BillValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;
    private final BankBookService bankBookService;
    private final RateService rateService;
    private final MeterReadingService meterReadingService;
    private final ApartmentService apartmentService;
    private final HouseService houseService;
    private final SectionService sectionService;
    private final ServiceService serviceService;
    private final ServiceBillService serviceBillService;
    private final BillValidator billValidator;
    private final UserService userService;

    @GetMapping({"/", ""})
    public ModelAndView showBillPage() {
        return new ModelAndView("admin/pages/bill/bill");
    }

    @GetMapping("/card-{id}")
    public ModelAndView showBillCardById(@PathVariable("id") Long id) {
        return new ModelAndView("admin/pages/bill/bill-card");
    }

    @GetMapping("/add")
    public ModelAndView showBillSavePage() {
        return new ModelAndView("admin/pages/bill/bill-save");
    }

    @GetMapping("/add-copy-bank-book-{id}")
    public ModelAndView showRateCopySavePage(@PathVariable("id") Long id) {
        return new ModelAndView("admin/pages/bill/bill-save");
    }

    @GetMapping("/add-copy-{id}")
    public ModelAndView showBillCopySavePage(@PathVariable("id") Long id) {
        return new ModelAndView("admin/pages/bill/bill-save");
    }

    @GetMapping("/update-{id}")
    public ModelAndView showBillUpdatePage(@PathVariable Long id) {
        return new ModelAndView("admin/pages/bill/bill-save");
    }

    @GetMapping("/get-all-owner")
    public ResponseEntity<Page<UserResponseForTable>> getAllUser(
            @RequestParam(required = false) Integer pageIndex,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(userService.userResponseForSelect(pageIndex, search));
    }

    @GetMapping("/get-all-draft")
    public ResponseEntity<List<EnumResponse>> getAllDraft() {
        return ResponseEntity.ok(billService.getDraft());
    }

    @PostMapping("/get-all-bills")
    public ResponseEntity<?> getAllBillResponse(@Valid @RequestBody BillRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        return ResponseEntity.ok(billService.getAllBillResponse(request));
    }

    @PostMapping("/get-excel-bills")
    public ResponseEntity<?> getExcel(@RequestBody BillRequest request) {
        try {
            InputStreamResource file = billService.getExcel(request);

            String filename = "bills" + new SimpleDateFormat("-dd-MM-yyyy HH:mm").format(new Date()) + ".xlsx";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(e.getMessage());
        }
    }

    @GetMapping("/get-excel-bill/{id}")
    public ResponseEntity<?> getExcel(@PathVariable Long id) {
        try {
            InputStreamResource file = billService.getExcel(id);

            String filename = "bill" + new SimpleDateFormat("-dd-MM-yyyy HH:mm").format(new Date()) + ".xlsx";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file);
        } catch (EntityNotFoundException | IOException e) {
            if (e instanceof EntityNotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(e.getMessage());
        }
    }

    @GetMapping("/get-bank-book-{id}")
    public ResponseEntity<?> getBankBookResponse(@PathVariable Long id) {
        try {
            BankBookResponse bankBookResponse = bankBookService.getBankBookResponseById(id);
            if (bankBookResponse.apartment() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank book without Apartment");
            }
            return ResponseEntity.ok(bankBookResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-bill-{id}")
    public ResponseEntity<?> getBillResponse(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(billService.getBillResponseById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-rate-{id}")
    public ResponseEntity<?> getRateResponse(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(rateService.getRateByIdResponseForBill(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-new-bill")
    public ResponseEntity<?> getNewBill() {
        try {
            return ResponseEntity.ok(billService.getNewBillResponse());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get-all-rates")
    public ResponseEntity<Page<RateResponse>> getAllRates(@RequestParam Integer pageIndex, @RequestParam String search) {
        return ResponseEntity.ok(rateService.getAllRatesForBill(pageIndex, search));
    }

    @GetMapping("/get-all-services")
    public ResponseEntity<Page<ServiceResponse>> getAllService(@RequestParam Integer page, @RequestParam String search) {
        return ResponseEntity.ok(serviceService.getAllServicesByIsActiveDto(page, search));
    }

    @GetMapping("/get-all-status")
    public ResponseEntity<List<EnumResponse>> getAllStatus() {
        return ResponseEntity.ok(billService.getAllBillStatus());
    }

    @PostMapping("/get-all-meter-reading")
    public ResponseEntity<Page<MeterReadingResponseForBill>> getAllMeterReadingByRequest(@RequestBody MeterReadingRequestForBill request) {
        return ResponseEntity.ok(meterReadingService.getAllMeterReadingResponseByRequest(request));
    }

    @GetMapping("/get-all-house")
    public ResponseEntity<Page<HouseResponseForTable>> getAllHouse(@RequestParam(required = false) Integer pageIndex,
                                                                   @RequestParam(required = false) String search) {
        return ResponseEntity.ok(houseService.houseListForTable(pageIndex, search));
    }

    @GetMapping("/get-section/{houseId}")
    public ResponseEntity<Page<SectionResponseForTable>> getSectionByHouse(@PathVariable Long houseId,
                                                                           @RequestParam(required = false) Integer pageIndex,
                                                                           @RequestParam(required = false) String search) {
        return ResponseEntity.ok(sectionService.sectionByHouse(houseId, pageIndex, search));
    }

    @GetMapping("/get-all-apartment")
    public ResponseEntity<Page<ApartmentResponseForBill>> getAllApartment(
            @RequestParam(required = false) Integer pageIndex,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long houseId,
            @RequestParam(required = false) Long sectionId
    ) {
        return ResponseEntity.ok(apartmentService.getAllApartmentResponse(pageIndex, search, houseId, sectionId));
    }

    @GetMapping("/get-all-apartment-{houseId}")
    public ResponseEntity<List<ApartmentResponseForBill>> getAllApartmentByHouse(@PathVariable Long houseId) {
        return ResponseEntity.ok(apartmentService.getAllApartmentResponseByHouseIdForBill(houseId));
    }

    @GetMapping("/get-all-apartment-{houseId}/{sectionId}")
    public ResponseEntity<List<ApartmentResponseForBill>> getAllApartmentByHouseAndSection(@PathVariable("houseId") Long houseId,
                                                                                           @PathVariable("sectionId") Long sectionId) {
        return ResponseEntity.ok(apartmentService.getAllApartmentResponseByHouseIdAndSectionIdForBill(houseId, sectionId));
    }

    @PutMapping("/update-bill")
    public ResponseEntity<?> updateBill(@Valid @RequestBody BillUpdateRequest request,
                                        BindingResult bindingResult) {
        billValidator.isNumberUniqueValidationWithId(request.id(), request.number(), bindingResult,
                "BillUpdateRequest", LocaleContextHolder.getLocale());
        billValidator.isPayedAndStatusEqualValidation(request.payed(), request.totalPrice(), request.status(), bindingResult,
                "BillUpdateRequest", LocaleContextHolder.getLocale());
        billValidator.isPayedCashBoxAndPayedValidationWithId(request.id(), request.payed(), request.totalPrice(), bindingResult,
                "BillUpdateRequest", LocaleContextHolder.getLocale());
        billValidator.checkUniqueServiceId(request.serviceBillList(), bindingResult,
                "BillUpdateRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            billService.updateBillByRequest(request);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save-bill")
    public ResponseEntity<?> saveBill(@Valid @RequestBody BillSaveRequest request,
                                      BindingResult bindingResult) {
        billValidator.isNumberUniqueValidation(request.number(), bindingResult,
                "BillSaveRequest", LocaleContextHolder.getLocale());
        billValidator.isPayedAndStatusEqualValidation(request.payed(), request.totalPrice(), request.status(), bindingResult,
                "BillSaveRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            billService.saveBillByRequest(request);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-service-bill/{id}")
    public ResponseEntity<?> deleteServiceBillById(@PathVariable Long id) {
        try {
            serviceBillService.deleteServiceBillById(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-bills")
    public ResponseEntity<?> deleteBillByRequest(@RequestBody List<BillDeleteRequest> bills) {
        try {
            billService.deleteBillByRequest(bills);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            if (e instanceof EntityNotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-bill/{id}")
    public ResponseEntity<?> deleteBillById(@PathVariable Long id) {
        try {
            billService.deleteBillById(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            if (e instanceof EntityNotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get-sum-of-all-bills")
    public ResponseEntity<?> getSumOfAllBills() {
        return ResponseEntity.ok(billService.sumOffAllBillsByMonths());
    }

    @GetMapping("/get-sum-of-all-paid-bills")
    public ResponseEntity<?> getSumOfAllPaidBills() {
        return ResponseEntity.ok(billService.sumOffAllPaidBillsByMonths());
    }
}

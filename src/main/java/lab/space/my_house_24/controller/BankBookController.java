package lab.space.my_house_24.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lab.space.my_house_24.model.apartment.ApartmentResponseForBankBook;
import lab.space.my_house_24.model.bankBook.BankBookRequest;
import lab.space.my_house_24.model.bankBook.BankBookResponse;
import lab.space.my_house_24.model.bankBook.BankBookSaveRequest;
import lab.space.my_house_24.model.bankBook.BankBookUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.user.UserResponseForTable;
import lab.space.my_house_24.service.*;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.BankBookValidator;
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
@RequestMapping("bank-books")
@RequiredArgsConstructor
public class BankBookController {

    private final BankBookService bankBookService;
    private final BankBookValidator bankBookValidator;
    private final ApartmentService apartmentService;
    private final SectionService sectionService;
    private final HouseService houseService;
    private final UserService userService;

    @GetMapping({"/", ""})
    public ModelAndView showBankBookPage() {
        return new ModelAndView("admin/pages/bank_book/bank-book");
    }

    @GetMapping("/card-{id}")
    public ModelAndView showBankBookCardById(@PathVariable("id") Long id) {
        return new ModelAndView("admin/pages/bank_book/bank-book-card");
    }

    @GetMapping("/add")
    public ModelAndView showBankBookSavePage() {
        return new ModelAndView("admin/pages/bank_book/bank-book-save");
    }

    @GetMapping("/update-{id}")
    public ModelAndView showBankBookUpdatePage(@PathVariable Long id) {
        return new ModelAndView("admin/pages/bank_book/bank-book-save");
    }

    @GetMapping("/get-all-balance-status")
    public ResponseEntity<List<EnumResponse>> getAllBalanceStatus() {
        return ResponseEntity.ok(bankBookService.getAllBalanceStatus());
    }

    @GetMapping("/get-all-owner")
    public ResponseEntity<List<UserResponseForTable>> getAllUser() {
        return ResponseEntity.ok(userService.userListForTable());
    }

    @GetMapping("/get-all-section")
    public ResponseEntity<List<SectionResponseForTable>> getAllSection() {
        return ResponseEntity.ok(sectionService.sectionListForTable());
    }

    @GetMapping("/get-all-status")
    public ResponseEntity<List<EnumResponse>> getAllBankBookStatus() {
        return ResponseEntity.ok(bankBookService.getAllBankBookStatus());
    }

    @GetMapping("/get-all-house")
    public ResponseEntity<List<HouseResponseForTable>> getAllHouse() {
        return ResponseEntity.ok(houseService.houseListForTable());
    }

    @GetMapping("/get-section/{houseId}")
    public ResponseEntity<List<SectionResponseForTable>> getSectionByHouse(@PathVariable Long houseId) {
        return ResponseEntity.ok(sectionService.sectionByHouse(houseId));

    }

    @GetMapping("/get-all-apartment-{houseId}")
    public ResponseEntity<List<ApartmentResponseForBankBook>> getAllApartmentByHouse(@PathVariable Long houseId) {
        return ResponseEntity.ok(apartmentService.getAllApartmentResponseByHouseId(houseId));
    }

    @GetMapping("/get-all-apartment-{houseId}/{sectionId}")
    public ResponseEntity<List<ApartmentResponseForBankBook>> getAllApartmentByHouseAndSection(@PathVariable("houseId") Long houseId,
                                                                                               @PathVariable("sectionId") Long sectionId) {
        return ResponseEntity.ok(apartmentService.getAllApartmentResponseByHouseIdAndSectionId(houseId, sectionId));
    }

    @GetMapping("/get-all-apartment")
    public ResponseEntity<List<ApartmentResponseForBankBook>> getAllApartment() {
        return ResponseEntity.ok(apartmentService.getAllApartmentResponse());
    }

    @GetMapping("/get-bank-book-{id}")
    public ResponseEntity<?> getBankBookResponse(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bankBookService.getBankBookResponseById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/get-all-bank-book")
    public ResponseEntity<Page<BankBookResponse>> getAllBankBookResponse(@RequestBody BankBookRequest request) {
        return ResponseEntity.ok(bankBookService.getAllBankBookResponse(request));
    }

    @PostMapping("/get-excel-bank-books")
    public ResponseEntity<?> getExcel(@RequestBody BankBookRequest request) {
        try {
            InputStreamResource file = bankBookService.getExcel(request);

            String filename = "bank-books" + new SimpleDateFormat("-dd-MM-yyyy HH:mm").format(new Date()) + ".xlsx";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(e.getMessage());
        }
    }

    @PutMapping("/update-bank-book")
    public ResponseEntity<?> updateBankBook(@Valid @RequestBody BankBookUpdateRequest request,
                                            BindingResult bindingResult) {
        bankBookValidator.isNumberUniqueValidationWithId(request.id(), request.number(), bindingResult,
                "BankBookUpdateRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            bankBookService.updateBankBookByRequest(request);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            if (e instanceof EntityNotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save-bank-book")
    public ResponseEntity<?> saveBankBook(@Valid @RequestBody BankBookSaveRequest request,
                                          BindingResult bindingResult) {
        bankBookValidator.isNumberUniqueValidation(request.number(), bindingResult,
                "BankBookSaveRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            bankBookService.saveBankBookByRequest(request);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            if (e instanceof EntityNotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-bank-book/{id}")
    public ResponseEntity<?> deleteBankBookById(@PathVariable Long id) {
        try {
            bankBookService.deleteBankBookById(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            if (e instanceof EntityNotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

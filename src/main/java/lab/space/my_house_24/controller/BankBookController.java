package lab.space.my_house_24.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lab.space.my_house_24.model.apartment.ApartmentResponseForBankBook;
import lab.space.my_house_24.model.bankBook.BankBookSaveRequest;
import lab.space.my_house_24.model.bankBook.BankBookUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.service.ApartmentService;
import lab.space.my_house_24.service.BankBookService;
import lab.space.my_house_24.service.HouseService;
import lab.space.my_house_24.service.SectionService;
import lab.space.my_house_24.util.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("bank-books")
@RequiredArgsConstructor
public class BankBookController {

    private final BankBookService bankBookService;
    private final ApartmentService apartmentService;
    private final SectionService sectionService;
    private final HouseService houseService;

    @GetMapping({"/", ""})
    public String showBankBookPage() {
        return "admin/pages/bank_book/bank-book";
    }

    @GetMapping("/card-{id}")
    public String showBankBookCardById(@PathVariable("id") Long id) {
        return "admin/pages/bank_book/bank-book-card";
    }

    @GetMapping("/add")
    public String showBankBookSavePage() {
        return "admin/pages/bank_book/bank-book-save";
    }

    @GetMapping("/update-{id}")
    public String showBankBookUpdatePage(@PathVariable Long id) {
        return "admin/pages/bank_book/bank-book-save";
    }

    @GetMapping("/get-all-status")
    public ResponseEntity<List<EnumResponse>> getAllBankBookStatus(){
        return ResponseEntity.ok(bankBookService.getAllBankBookStatus());
    }

    @GetMapping("/get-all-house")
    public ResponseEntity<List<HouseResponseForTable>> getAllHouse(){
        return ResponseEntity.ok(houseService.houseListForTable());
    }

    @GetMapping("/get-section/{houseId}")
    public ResponseEntity<List<SectionResponseForTable>> getSectionByHouse(@PathVariable Long houseId){
        return ResponseEntity.ok(sectionService.sectionByHouse(houseId));

    }

    @GetMapping("/get-all-apartment-{houseId}")
    public ResponseEntity<List<ApartmentResponseForBankBook>> getAllApartmentByHouse(@PathVariable Long houseId){
        return ResponseEntity.ok(apartmentService.getAllApartmentResponseByHouseId(houseId));
    }

    @GetMapping("/get-all-apartment-{houseId}/{sectionId}")
    public ResponseEntity<List<ApartmentResponseForBankBook>> getAllApartmentByHouseAndSection(@PathVariable("houseId") Long houseId,
                                                                                               @PathVariable("sectionId") Long sectionId){
        return ResponseEntity.ok(apartmentService.getAllApartmentResponseByHouseIdAndSectionId(houseId, sectionId));
    }
    @GetMapping("/get-all-apartment")
    public ResponseEntity<List<ApartmentResponseForBankBook>> getAllApartment(){
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

    @PutMapping("/update-bank-book")
    public ResponseEntity<?> updateBankBook(@Valid @RequestBody BankBookUpdateRequest request,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            bankBookService.updateBankBookByRequest(request);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save-bank-book")
    public ResponseEntity<?> saveBankBook(@Valid @RequestBody BankBookSaveRequest request,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            bankBookService.saveBankBookByRequest(request);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-bank-book/{id}")
    public ResponseEntity<?> deleteBankBookById(@PathVariable Long id) {
        try {
            bankBookService.deleteBankBookById(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

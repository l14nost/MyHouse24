package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.apartment.ApartmentAddRequest;
import lab.space.my_house_24.model.apartment.ApartmentRequestForMainPage;
import lab.space.my_house_24.model.apartment.ApartmentResponseForEdit;
import lab.space.my_house_24.model.apartment.ApartmentResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.service.ApartmentService;
import lab.space.my_house_24.service.BankBookService;
import lab.space.my_house_24.service.HouseService;
import lab.space.my_house_24.service.RateService;
import lab.space.my_house_24.service.impl.*;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.ApartmentValidator;
import lab.space.my_house_24.validator.BankBookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("apartments")
@RequiredArgsConstructor
public class ApartmentController {

    private final HouseService houseService;
    private final ApartmentService apartmentService;
    private final RateService rateService;
    private final BankBookService bankBookService;
    private final BankBookValidator bankBookValidator;
    private final ApartmentValidator apartmentValidator;

    @GetMapping({"/",""})
    public String apartmentMain(Model model){
        model.addAttribute("houseList", houseService.houseListForTable());
        return "admin/pages/apartment/apartment-main";
    }

    @PostMapping("/get-all-apartments")
    public ResponseEntity allApartment(@RequestBody ApartmentRequestForMainPage apartmentRequestForMainPage){
        return ResponseEntity.ok().body(apartmentService.findAllForMainPage(apartmentRequestForMainPage));
    }

    @DeleteMapping("/delete-apartment/{id}")
    public ResponseEntity deleteApartment(@PathVariable Long id){
        apartmentService.deleteApartment(id);
        return ResponseEntity.ok().build();

    }


    @GetMapping("/apartment-card/{id}")
    public String apartmentCard(@PathVariable Long id, Model model){
        model.addAttribute("apartment", apartmentService.findByIdForCard(id));
        model.addAttribute("id", id);
        return "admin/pages/apartment/apartment-card";
    }


    @GetMapping("/get-apartment-by-id/{id}")
    public ResponseEntity getApartmentById(@PathVariable Long id){
        return ResponseEntity.ok(apartmentService.findByIdForCard(id));
    }

    @GetMapping("/add-apartment")
    public String addApartmentPage(Model model){
        model.addAttribute("houseList", houseService.houseListForTable());
        model.addAttribute("rateList", rateService.rateListForTable());
        model.addAttribute("bankBookList", bankBookService.bankBookListForTable());
        return "/admin/pages/apartment/apartment-add";
    }


    @PostMapping("/add-apartment-save")
    public ResponseEntity addApartment(@RequestBody @Valid ApartmentAddRequest apartmentAddRequest, BindingResult result){
        if (apartmentAddRequest.bankBook()!=null) {
            bankBookValidator.busyBankBook(apartmentAddRequest.bankBook(), result, "ApartmentAddRequest","add",0L);
        }
        if (apartmentAddRequest.number()!=null && apartmentAddRequest.house()!=null){
            apartmentValidator.checkUniqueApartmentNumber(apartmentAddRequest.number(),"add",0L,apartmentAddRequest.house(),"ApartmentAddRequest",result);
        }
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        apartmentService.saveApartment(apartmentAddRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/edit-apartment/{id}")
    public String editApartmentPage(@PathVariable Long id,Model model){
        model.addAttribute("apartment", apartmentService.findByIdApartment(id));
        model.addAttribute("houseList", houseService.houseListForTable());
        model.addAttribute("rateList", rateService.rateListForTable());
        model.addAttribute("bankBookList", bankBookService.bankBookListForTable());
        model.addAttribute("id", id);
        return "/admin/pages/apartment/apartment-edit";
    }

    @PostMapping("/edit-apartment/{id}")
    public ResponseEntity editApartment(@PathVariable Long id, @RequestBody @Valid ApartmentAddRequest apartmentAddRequest, BindingResult result){
        if (apartmentAddRequest.bankBook()!=null) {
            bankBookValidator.busyBankBook(apartmentAddRequest.bankBook(), result, "ApartmentAddRequest","update",id);
        }
        if (apartmentAddRequest.number()!=null && apartmentAddRequest.house()!=null){
            apartmentValidator.checkUniqueApartmentNumber(apartmentAddRequest.number(),"update",id,apartmentAddRequest.house(),"ApartmentAddRequest",result);
        }
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        apartmentService.updateApartment(id,apartmentAddRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/edit-add-apartment/{id}")
    public String editAddApartmentPage(@PathVariable Long id,Model model){
        ApartmentResponseForEdit apartmentResponseForEdit = apartmentService.findByIdApartment(id);
        ApartmentResponseForEdit apartmentResponseForEdit1 = ApartmentResponseForEdit.builder()
                .number(apartmentResponseForEdit.number()+1)
                .rate(apartmentResponseForEdit.rate())
                .floor(apartmentResponseForEdit.floor())
                .area(apartmentResponseForEdit.area())
                .section(apartmentResponseForEdit.section())
                .bankBook(apartmentResponseForEdit.bankBook())
                .section(apartmentResponseForEdit.section())
                .house(apartmentResponseForEdit.house())
                .build();
        model.addAttribute("apartment", apartmentResponseForEdit1);
        model.addAttribute("houseList", houseService.houseListForTable());
        model.addAttribute("rateList", rateService.rateListForTable());
        model.addAttribute("bankBookList", bankBookService.bankBookListForTable());
        model.addAttribute("id", 0);
        return "/admin/pages/apartment/apartment-edit";
    }

    @GetMapping("/get-apartment")
    @ResponseBody
    public List<ApartmentResponseForTable> getApartmentByHouse(@RequestParam Long idHouse,@RequestParam(required = false) Long idSection,@RequestParam(required = false) Long idFloor){
        return apartmentService.apartmentForSelect(idHouse,idSection,idFloor);

    }
}

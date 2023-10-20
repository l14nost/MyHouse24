package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.apartment.ApartmentAddRequest;
import lab.space.my_house_24.model.apartment.ApartmentRequestForMainPage;
import lab.space.my_house_24.model.apartment.ApartmentResponseForEdit;
import lab.space.my_house_24.model.apartment.ApartmentResponseForTable;
import lab.space.my_house_24.service.ApartmentService;
import lab.space.my_house_24.service.BankBookService;
import lab.space.my_house_24.service.HouseService;
import lab.space.my_house_24.service.RateService;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.ApartmentValidator;
import lab.space.my_house_24.validator.BankBookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping({"/", ""})
    public ModelAndView apartmentMain() {
        return new ModelAndView("admin/pages/apartment/apartment-main");
    }

    @PostMapping("/get-all-apartments")
    public ResponseEntity allApartment(@RequestBody @Valid ApartmentRequestForMainPage apartmentRequestForMainPage) {
        return ResponseEntity.ok().body(apartmentService.findAllForMainPage(apartmentRequestForMainPage));
    }

    @DeleteMapping("/delete-apartment/{id}")
    public ResponseEntity deleteApartment(@PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.ok().build();

    }


    @GetMapping("/apartment-card/{id}")
    public String apartmentCard(@PathVariable Long id, Model model){
        model.addAttribute("id", id);
        return "admin/pages/apartment/apartment-card";
    }


    @GetMapping("/get-apartment-by-id/{id}")
    public ResponseEntity getApartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(apartmentService.findByIdForCard(id));
    }

    @GetMapping("/add-apartment")
    public String addApartmentPage(Model model) {
        model.addAttribute("rateList", rateService.rateListForTable());
        model.addAttribute("bankBookList", bankBookService.bankBookListForTable());
        return "admin/pages/apartment/apartment-add";
    }


    @PostMapping("/add-apartment-save")
    public ResponseEntity addApartment(@RequestBody @Valid ApartmentAddRequest apartmentAddRequest, BindingResult result) {
        if (apartmentAddRequest.bankBook() != null) {
            bankBookValidator.busyBankBook(apartmentAddRequest.bankBook(), result, "ApartmentAddRequest", "add", 0L);
        }
        if (apartmentAddRequest.number() != null && apartmentAddRequest.house() != null) {
            apartmentValidator.checkUniqueApartmentNumber(apartmentAddRequest.number(), "add", 0L, apartmentAddRequest.house(), "ApartmentAddRequest", result);
        }
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        apartmentService.saveApartment(apartmentAddRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/edit-apartment/{id}")
    public String editApartmentPage(@PathVariable Long id, Model model) {
        model.addAttribute("apartment", apartmentService.findByIdApartment(id));
        model.addAttribute("rateList", rateService.rateListForTable());
        model.addAttribute("bankBookList", bankBookService.bankBookListForTable());
        model.addAttribute("id", id);
        return "admin/pages/apartment/apartment-edit";
    }

    @PostMapping("/edit-apartment/{id}")
    public ResponseEntity editApartment(@PathVariable Long id, @RequestBody @Valid ApartmentAddRequest apartmentAddRequest, BindingResult result) {
        if (apartmentAddRequest.bankBook() != null) {
            bankBookValidator.busyBankBook(apartmentAddRequest.bankBook(), result, "ApartmentAddRequest", "update", id);
        }
        if (apartmentAddRequest.number() != null && apartmentAddRequest.house() != null) {
            apartmentValidator.checkUniqueApartmentNumber(apartmentAddRequest.number(), "update", id, apartmentAddRequest.house(), "ApartmentAddRequest", result);
        }
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        apartmentService.updateApartment(id, apartmentAddRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-apartment")
    @ResponseBody
    public Page<ApartmentResponseForTable> getApartmentByHouse(@RequestParam Long idHouse, @RequestParam(required = false) Long idSection, @RequestParam(required = false) Long idFloor, @RequestParam(required = false) Boolean duty, @RequestParam Integer page){
        return apartmentService.apartmentForSelectPagination(idHouse,idSection,idFloor,duty, page);

    }
}

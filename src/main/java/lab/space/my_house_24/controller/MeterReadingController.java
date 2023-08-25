package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.meterReading.MeterReadingRequestForAdd;
import lab.space.my_house_24.model.meterReading.MeterReadingRequestForApartmentPage;
import lab.space.my_house_24.model.meterReading.MeterReadingRequestForEdit;
import lab.space.my_house_24.model.meterReading.MeterReadingRequestForMainPage;
import lab.space.my_house_24.service.*;
import lab.space.my_house_24.util.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("meter-readings")
@RequiredArgsConstructor
public class MeterReadingController {
    private final MeterReadingService meterReadingService;
    private final ApartmentService apartmentService;
    private final HouseService houseService;
    private final SectionService sectionService;
    private final ServiceService serviceService;

    @GetMapping({"/",""})
    public String meterReadingPage(){
        return "/admin/pages/meterReading/meter-reading-main";
    }

    @PostMapping("/get-all-meter-reading")
    public ResponseEntity getAllMeterReading(@RequestBody MeterReadingRequestForMainPage meterReadingRequestForMainPage){
        return ResponseEntity.ok().body(meterReadingService.findAllForMain(meterReadingRequestForMainPage));
    }

    @GetMapping("/add-meter-reading")
    public String addMeterReadingPage(Model model){
        model.addAttribute("number",  String.format("%09d", meterReadingService.count()+1));
        return "/admin/pages/meterReading/meter-reading-add";
    }

    @PostMapping("/add-meter-reading")
    public ResponseEntity addMeterReading(@RequestBody @Valid MeterReadingRequestForAdd meterReadingRequestForAdd, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        meterReadingService.save(meterReadingRequestForAdd);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/edit-meter-reading/{id}")
    public String editMeterReadingPage(@PathVariable Long id, Model model){
        model.addAttribute("meterReading", meterReadingService.findByIdEdit(id));
        model.addAttribute("houseList", houseService.houseListForTable());
        model.addAttribute("apartmentList", apartmentService.apartmentListForSelect());
        model.addAttribute("sectionList", sectionService.sectionListForTable());
        model.addAttribute("serviceList", serviceService.serviceListForTable());

        return "/admin/pages/meterReading/meter-reading-edit";
    }
    @PostMapping("/edit-meter-reading/{id}")
    public ResponseEntity addMeterReading(@PathVariable Long id, @RequestBody @Valid MeterReadingRequestForEdit meterReadingRequestForEdit, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        meterReadingService.update(meterReadingRequestForEdit, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/meter-readings-by-apartment")
    public String meterReadingByApartmentPage(@RequestParam Long idApartment,@RequestParam Long idService, Model model){
        model.addAttribute("idApartment", idApartment);
        model.addAttribute("apartment", apartmentService.findById(idApartment).getNumber());
        model.addAttribute("idService", idService);

        return "/admin/pages/meterReading/meter-reading-apartment";
    }

    @PostMapping("/get-all-meter-reading-by-apartment")
    public ResponseEntity getAllMeterReadingByApartment(@RequestBody MeterReadingRequestForApartmentPage meterReadingRequestForApartmentPage){
        return ResponseEntity.ok().body(meterReadingService.findAllForApartment(meterReadingRequestForApartmentPage));
    }


    @DeleteMapping("/delete-meter-reading/{id}")
    public ResponseEntity deleteMeterReading(@PathVariable Long id){
        meterReadingService.delete(id);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/add-meter-reading-apartment")
    public String meterReadingByApartmentAddPage(@RequestParam Long idApartment,@RequestParam Long idService, Model model){
        model.addAttribute("number",  String.format("%09d", meterReadingService.count()+1));
        if (meterReadingService.findByIdForApartmentAdd(idApartment, idService)==null){
            return "/admin/pages/meterReading/meter-reading-add";
        }
        model.addAttribute("meterReading", meterReadingService.findByIdForApartmentAdd(idApartment, idService));
        model.addAttribute("houseList", houseService.houseListForTable());
        model.addAttribute("apartmentList", apartmentService.apartmentListForSelect());
        model.addAttribute("sectionList", sectionService.sectionListForTable());
        model.addAttribute("serviceList", serviceService.serviceListForTable());
        return "/admin/pages/meterReading/meter-reading-add-apartment";
    }
}

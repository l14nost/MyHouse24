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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@Controller
@RequestMapping("meter-readings")
@RequiredArgsConstructor
public class MeterReadingController {
    private final MeterReadingService meterReadingService;
    private final ApartmentService apartmentService;
    private final HouseService houseService;
    private final SectionService sectionService;
    private final ServiceService serviceService;

    @GetMapping({"/", ""})
    public ModelAndView meterReadingPage(){
        return new ModelAndView("admin/pages/meterReading/meter-reading-main");
    }

    @PostMapping("/get-all-meter-reading")
    public ResponseEntity getAllMeterReading(@RequestBody MeterReadingRequestForMainPage meterReadingRequestForMainPage) {
        return ResponseEntity.ok().body(meterReadingService.findAllForMain(meterReadingRequestForMainPage));
    }

    @GetMapping("/add-meter-reading")
    public ModelAndView addMeterReadingPage() {
        ModelAndView modelAndView = new ModelAndView("admin/pages/meterReading/meter-reading-add");
        modelAndView.addObject("number", String.format("%09d", meterReadingService.count() + 1));
        return modelAndView;
    }

    @PostMapping("/add-meter-reading")
    public ResponseEntity addMeterReading(@RequestBody @Valid MeterReadingRequestForAdd meterReadingRequestForAdd, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        meterReadingService.save(meterReadingRequestForAdd);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/edit-meter-reading/{id}")
    public ModelAndView editMeterReadingPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/pages/meterReading/meter-reading-edit");
        modelAndView.addObject("meterReading", meterReadingService.findByIdEdit(id));
        modelAndView.addObject("houseList", houseService.houseListForTable());
        modelAndView.addObject("apartmentList", apartmentService.apartmentListForSelect());
        modelAndView.addObject("sectionList", sectionService.sectionListForTable());
        modelAndView.addObject("serviceList", serviceService.serviceListForTable());
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    @PostMapping("/edit-meter-reading/{id}")
    public ResponseEntity editMeterReading(@PathVariable Long id, @RequestBody @Valid MeterReadingRequestForEdit meterReadingRequestForEdit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        meterReadingService.update(meterReadingRequestForEdit, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/meter-readings-by-apartment/{idApartment}/{idService}")
    public ModelAndView meterReadingByApartmentPage(@PathVariable Long idApartment, @PathVariable Long idService) {
        ModelAndView modelAndView = new ModelAndView("admin/pages/meterReading/meter-reading-apartment");
        modelAndView.addObject("idApartment", idApartment);
        modelAndView.addObject("apartment", apartmentService.findById(idApartment).getNumber());
        modelAndView.addObject("idService", idService);

        return modelAndView;
    }

    @PostMapping("/get-all-meter-reading-by-apartment")
    public ResponseEntity getAllMeterReadingByApartment(@RequestBody MeterReadingRequestForApartmentPage meterReadingRequestForApartmentPage) {
        return ResponseEntity.ok().body(meterReadingService.findAllForApartment(meterReadingRequestForApartmentPage));
    }


    @DeleteMapping("/delete-meter-reading/{id}")
    public ResponseEntity deleteMeterReading(@PathVariable Long id) {
        meterReadingService.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/add-meter-reading-apartment")
    public ModelAndView meterReadingByApartmentAddPage(@RequestParam Long idApartment,@RequestParam Long idService) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("number", String.format("%09d", meterReadingService.count()+1));
        if (meterReadingService.findByIdForApartmentAdd(idApartment, idService)==null){
            modelAndView.setViewName("admin/pages/meterReading/meter-reading-add");
            return modelAndView;
        }
        modelAndView.addObject("meterReading", meterReadingService.findByIdForApartmentAdd(idApartment, idService));
        modelAndView.addObject("houseList", houseService.houseListForTable());
        modelAndView.addObject("apartmentList", apartmentService.apartmentListForSelect());
        modelAndView.addObject("sectionList", sectionService.sectionListForTable());
        modelAndView.addObject("serviceList", serviceService.serviceListForTable());
        modelAndView.setViewName("admin/pages/meterReading/meter-reading-add-apartment");
        return modelAndView;
    }
}

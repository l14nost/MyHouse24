package lab.space.my_house_24.controller;

import lab.space.my_house_24.model.apartment.ApartmentRequestForMainPage;
import lab.space.my_house_24.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ApartmentController {

    private final HouseServiceImpl houseService;
    private final SectionServiceImpl sectionService;
    private final FloorServiceImpl floorService;
    private final UserServiceImpl userService;
    private final ApartmentServiceImpl apartmentService;

    @GetMapping("/apartments")
    public String apartmentMain(Model model){
        model.addAttribute("houseList", houseService.houseListForTable());
        model.addAttribute("sectionList", sectionService.sectionListForTable());
        model.addAttribute("floorList", floorService.floorListForTable());
        model.addAttribute("ownerList", userService.userListForTable());
        return "admin/pages/apartment/apartment-main";
    }

    @PostMapping("/get-all-apartments")
    public ResponseEntity allApartment(@RequestBody ApartmentRequestForMainPage apartmentRequestForMainPage){
        System.out.println(apartmentRequestForMainPage.toString());
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
        return "admin/pages/apartment/apartment-card";
    }
}

package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.house.HouseRequestForAddPage;
import lab.space.my_house_24.model.house.HouseRequestForMainPage;
import lab.space.my_house_24.service.HouseService;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.HouseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HouseController {
    private final HouseService houseService;
    private final HouseValidator houseValidator;
    @GetMapping("/houses")
    public String mainPage(){
        return "/admin/pages/houses/house-main";
    }

    @PostMapping("/get-all-house")
    public ResponseEntity getAllHouse(@RequestBody HouseRequestForMainPage houseRequestForMainPage){
        return ResponseEntity.ok().body(houseService.finaAllForMain(houseRequestForMainPage));
    }

    @DeleteMapping("/delete-house/{id}")
    public ResponseEntity deleteHouse(@PathVariable Long id){
        houseService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/house-card/{id}")
    public String houseCard(@PathVariable Long id, Model model){
        model.addAttribute("id", id);
        return "/admin/pages/houses/house-card";
    }

    @GetMapping("/get-house-by-id/{id}")
    public ResponseEntity getHouseById(@PathVariable Long id){
        return ResponseEntity.ok().body(houseService.findByIdForCard(id));
    }

    @GetMapping("/add-house")
    public String addHousePage(){
        return "/admin/pages/houses/house-add";
    }

    @PostMapping("/add-house")
    public ResponseEntity addHouse(@ModelAttribute @Valid HouseRequestForAddPage houseRequestForAddPage, BindingResult result){
        if (!houseRequestForAddPage.userList().isEmpty()){
            houseValidator.uniqueStaffForHouse(houseRequestForAddPage.userList(),result);
        }
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        houseService.save(houseRequestForAddPage);
        return ResponseEntity.ok().build();
    }
}

package lab.space.my_house_24.controller;

import lab.space.my_house_24.model.house.HouseRequestForMainPage;
import lab.space.my_house_24.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HouseController {
    private final HouseService houseService;
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
}

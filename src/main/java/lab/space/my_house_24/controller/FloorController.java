package lab.space.my_house_24.controller;

import lab.space.my_house_24.model.floor.FloorResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.service.FloorService;
import lab.space.my_house_24.service.impl.FloorServiceImpl;
import lab.space.my_house_24.service.impl.SectionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FloorController {
    private final FloorService floorService;

    @GetMapping("/get-floor/{houseId}")
    @ResponseBody
    public Page<FloorResponseForTable> getSectionByHouse(@PathVariable Long houseId, @RequestParam Integer page, @RequestParam String search){
        return floorService.floorByHouse(houseId, page, search);
    }

}

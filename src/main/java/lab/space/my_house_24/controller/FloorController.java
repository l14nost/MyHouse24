package lab.space.my_house_24.controller;

import lab.space.my_house_24.model.floor.FloorResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.service.FloorService;
import lab.space.my_house_24.service.impl.FloorServiceImpl;
import lab.space.my_house_24.service.impl.SectionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FloorController {
    private final FloorService floorService;

    @GetMapping("/get-floor/{houseId}")
    @ResponseBody
    public List<FloorResponseForTable> getSectionByHouse(@PathVariable Long houseId){
        return floorService.floorByHouse(houseId);

    }

}

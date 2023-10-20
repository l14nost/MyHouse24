package lab.space.my_house_24.controller;

import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.service.SectionService;
import lab.space.my_house_24.service.impl.SectionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @GetMapping("/get-section/{houseId}")
    @ResponseBody
    public Page<SectionResponseForTable> getSectionByHouse(@PathVariable Long houseId, @RequestParam Integer page, @RequestParam String search){
        return sectionService.sectionByHouse(houseId, page, search);
    }

}

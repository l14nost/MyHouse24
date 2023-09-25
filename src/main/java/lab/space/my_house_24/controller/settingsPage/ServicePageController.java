package lab.space.my_house_24.controller.settingsPage;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.settingsPage.mainPage.MainPageRequest;
import lab.space.my_house_24.model.settingsPage.service.ServicePageRequest;
import lab.space.my_house_24.service.ServicePageService;
import lab.space.my_house_24.service.ServiceService;
import lab.space.my_house_24.util.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("site")
@RequiredArgsConstructor
public class ServicePageController {
    private final ServicePageService servicePageService;

    @GetMapping("/service-page")
    public String siteServicePage(Model model){
        model.addAttribute("service", servicePageService.findByIdResponse(1L));
        return "admin/pages/settingsPage/service/service-page";
    }
    @PutMapping("/service-page-save")
    public ResponseEntity saveServicePage(@ModelAttribute @Valid ServicePageRequest servicePageRequest, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        servicePageService.update(servicePageRequest);
        return ResponseEntity.ok().build();
    }
}

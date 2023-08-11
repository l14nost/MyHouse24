package lab.space.my_house_24.controller.settingsPage;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.settingsPage.mainPage.MainPageRequest;
import lab.space.my_house_24.service.MainPageService;
import lab.space.my_house_24.util.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final MainPageService mainPageService;

    @GetMapping("/site-main-page")
    public String mainPage(Model model){
        model.addAttribute("mainPage",mainPageService.findByIdResponse(1L));
        return "/admin/pages/settingsPage/mainPage/main-page";
    }

    @PutMapping("/site-main-page-save")
    public ResponseEntity saveMainPage(@ModelAttribute @Valid MainPageRequest mainPageRequest, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        mainPageService.update(mainPageRequest);
        return ResponseEntity.ok().build();
    }


}

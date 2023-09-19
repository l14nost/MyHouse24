package lab.space.my_house_24.controller.settingsPage;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.settingsPage.about.AboutRequest;
import lab.space.my_house_24.service.AboutService;
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
public class AboutPageController {
    private final AboutService aboutService;

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("about", aboutService.findByIdResponse(1L));
        return "/admin/pages/settingsPage/about/about-page";
    }

    @PutMapping("/about-save")
    public ResponseEntity saveAbout(@ModelAttribute @Valid AboutRequest aboutRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        aboutService.update(aboutRequest);
        return ResponseEntity.ok().build();
    }
}

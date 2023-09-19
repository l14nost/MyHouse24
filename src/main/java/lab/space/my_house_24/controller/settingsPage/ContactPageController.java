package lab.space.my_house_24.controller.settingsPage;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.settingsPage.contact.ContactRequest;
import lab.space.my_house_24.service.ContactService;
import lab.space.my_house_24.util.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("site")
@RequiredArgsConstructor
public class ContactPageController {
    private final ContactService contactService;

    @GetMapping("/contact-page")
    public String contactPage(Model model) {
        model.addAttribute("contactPage", contactService.findByIdResponse(1L));
        return "/admin/pages/settingsPage/contact/contact-page";
    }

    @PutMapping("/contact-page-save")
    public ResponseEntity saveMainPage(@RequestBody @Valid ContactRequest contactRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        contactService.update(contactRequest);
        return ResponseEntity.ok().build();
    }


}

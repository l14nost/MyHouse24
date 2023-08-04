package lab.space.my_house_24.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocaleChangeController {


    @GetMapping("/get-locale")
    public ResponseEntity getLocale(){
        return ResponseEntity.ok(LocaleContextHolder.getLocale());
    }
}

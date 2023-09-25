package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.requites.RequisitesRequest;
import lab.space.my_house_24.service.RequisitesService;
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
@RequestMapping("payment-details")
@RequiredArgsConstructor
public class RequisitesController {
    private final RequisitesService requisitesService;

    @GetMapping({"/",""})
    public String requisitesPage(Model model){
        model.addAttribute("requisites", requisitesService.findByIdResponse(1L));
        return "admin/pages/requisites/requisites-page";
    }

    @PutMapping("/payment-details-save")
    public ResponseEntity requisitesSave(@RequestBody @Valid RequisitesRequest requisitesRequest, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        requisitesService.update(requisitesRequest);
        return ResponseEntity.ok().build();
    }
}

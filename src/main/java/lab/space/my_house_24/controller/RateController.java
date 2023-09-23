package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.rate.RateRequest;
import lab.space.my_house_24.model.rate.RateResponse;
import lab.space.my_house_24.model.rate.RateSaveRequest;
import lab.space.my_house_24.model.rate.RateUpdateRequest;
import lab.space.my_house_24.model.service.ServiceResponse;
import lab.space.my_house_24.service.PriceRateService;
import lab.space.my_house_24.service.RateService;
import lab.space.my_house_24.service.ServiceService;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.RateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("rates")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;
    private final RateValidator rateValidator;
    private final PriceRateService priceRateService;
    private final ServiceService serviceService;

    @GetMapping({"/", ""})
    public ModelAndView showRatesPage() {
        return new ModelAndView("admin/pages/rate/rates");
    }

    @GetMapping("/card-{id}")
    public ModelAndView showRateCardById(@PathVariable("id") Long id) {
        return new ModelAndView("admin/pages/rate/rate-card");
    }

    @GetMapping("/add")
    public ModelAndView showRateSavePage() {
        return new ModelAndView("admin/pages/rate/rate-save");
    }

    @GetMapping("/add-copy-{id}")
    public ModelAndView showRateCopySavePage(@PathVariable("id") Long id) {
        return new ModelAndView("admin/pages/rate/rate-save");
    }

    @GetMapping("/update-{id}")
    public ModelAndView showRateUpdatePage(@PathVariable Long id) {
        return new ModelAndView("admin/pages/rate/rate-save");
    }

    @GetMapping("/get-rate-{id}")
    public ResponseEntity<?> getRateDto(@PathVariable Long id) {
        return rateService.getRateByIdDto(id);
    }

    @GetMapping("/get-rate-card-{id}")
    public ResponseEntity<?> getRateDtoWithId(@PathVariable Long id) {
        return rateService.getRateByIdWithUpdateAt(id);
    }

    @PostMapping("/get-all-rates")
    public ResponseEntity<Page<RateResponse>> getAllRatesDto(@RequestBody RateRequest request) {
        return ResponseEntity.ok(rateService.getAllRatesResponse(request));
    }

    @GetMapping("/get-all-services")
    public ResponseEntity<List<ServiceResponse>> getAllServicesDto() {
        return ResponseEntity.ok(serviceService.getAllServicesByIsActiveDto());
    }

    @PutMapping("/update-rate")
    public ResponseEntity<?> updateRate(@Valid @RequestBody RateUpdateRequest rateUpdateRequest,
                                        BindingResult bindingResult) {
        rateValidator.isNameUniqueValidationWithIdRate(rateUpdateRequest.id(), rateUpdateRequest.name(), bindingResult,
                "RateUpdateRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        return rateService.updateRateByRequest(rateUpdateRequest);
    }

    @PostMapping("/save-rate")
    public ResponseEntity<?> saveRate(@Valid @RequestBody RateSaveRequest rateSaveRequest,
                                      BindingResult bindingResult) {
        rateValidator.isNameUniqueValidationRate(rateSaveRequest.name(), bindingResult,
                "RateSaveRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        return rateService.saveRateByRequest(rateSaveRequest);
    }

    @DeleteMapping("/delete-price-rate/{id}")
    public ResponseEntity<?> deletePriceRateById(@PathVariable Long id) {
        return priceRateService.deletePriceRateById(id);
    }

    @DeleteMapping("/delete-rate/{id}")
    public ResponseEntity<?> deleteRateById(@PathVariable Long id) {
        return rateService.deleteRateById(id);
    }
}

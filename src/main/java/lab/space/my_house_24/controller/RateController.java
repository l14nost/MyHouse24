package lab.space.my_house_24.controller;

import jakarta.persistence.EntityNotFoundException;
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
import lab.space.my_house_24.validator.PriceRateValidator;
import lab.space.my_house_24.validator.RateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    private final PriceRateValidator priceRateValidator;
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
        try {
            return ResponseEntity.ok(rateService.getRateByIdDto(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/get-rate-card-{id}")
    public ResponseEntity<?> getRateDtoWithId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(rateService.getRateByIdWithUpdateAt(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
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
    public ResponseEntity<?> updateRate(@Valid @RequestBody RateUpdateRequest request,
                                        BindingResult bindingResult) {
        rateValidator.isNameUniqueValidationWithIdRate(request.id(), request.name(), bindingResult,
                "RateUpdateRequest", LocaleContextHolder.getLocale());
        priceRateValidator.checkUniqueServiceId(request.priceRate(), bindingResult,
                "RateUpdateRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            rateService.updateRateByRequest(request);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping("/save-rate")
    public ResponseEntity<?> saveRate(@Valid @RequestBody RateSaveRequest rateSaveRequest,
                                      BindingResult bindingResult) {
        rateValidator.isNameUniqueValidationRate(rateSaveRequest.name(), bindingResult,
                "RateSaveRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        rateService.saveRateByRequest(rateSaveRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-price-rate/{id}")
    public ResponseEntity<?> deletePriceRateById(@PathVariable Long id) {
        try {
            priceRateService.deletePriceRateById(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @DeleteMapping("/delete-rate/{id}")
    public ResponseEntity<?> deleteRateById(@PathVariable Long id) {
        try {
            rateService.deleteRateById(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            if (e instanceof EntityNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
}

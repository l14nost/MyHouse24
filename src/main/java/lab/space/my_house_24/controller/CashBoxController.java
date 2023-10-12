package lab.space.my_house_24.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lab.space.my_house_24.model.article.ArticleResponse;
import lab.space.my_house_24.model.bankBook.BankBookResponseForCashBox;
import lab.space.my_house_24.model.cash_box.CashBoxRequest;
import lab.space.my_house_24.model.cash_box.CashBoxSaveRequest;
import lab.space.my_house_24.model.cash_box.CashBoxUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.staff.StaffResponse;
import lab.space.my_house_24.model.user.UserResponseForTable;
import lab.space.my_house_24.service.*;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.CashBoxValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("cash-box")
@RequiredArgsConstructor
public class CashBoxController {
    private final UserService userService;
    private final StaffService staffService;
    private final CashBoxService cashBoxService;
    private final BankBookService bankBookService;
    private final ArticleService articleService;
    private final CashBoxValidator cashBoxValidator;

    @GetMapping({"/", ""})
    public String showCashBoxPage() {
        return "admin/pages/cash_box/cash-box";
    }

    @GetMapping("/card-{id}")
    public String showCashBoxCardById(@PathVariable("id") Long id) {
        return "admin/pages/cash_box/cash-box-card";
    }

    @GetMapping("/add-income")
    public String showCashBoxIncomeSavePage() {
        return "admin/pages/cash_box/cash-box-save";
    }

    @GetMapping("/add-expensive")
    public String showCashBoxExpensiveSavePage() {
        return "admin/pages/cash_box/cash-box-save";
    }

    @GetMapping("/add-copy-{id}")
    public String showCashBoxCopySavePage(@PathVariable Long id) {
        return "admin/pages/cash_box/cash-box-save";
    }

    @GetMapping("/update-{id}")
    public String showCashBoxUpdatePage(@PathVariable Long id) {
        return "admin/pages/cash_box/cash-box-save";
    }

    @PostMapping("/get-all-cash-box")
    public ResponseEntity<?> getAllCashBox(@Valid @RequestBody CashBoxRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        return ResponseEntity.ok(cashBoxService.getAllCashBoxResponse(request));
    }

    @GetMapping("/get-all-owner")
    public ResponseEntity<List<UserResponseForTable>> getAllUser() {
        return ResponseEntity.ok(userService.userListForTable());
    }

    @PostMapping("/get-excel-cash-boxes")
    public ResponseEntity<?> getExcel(@RequestBody CashBoxRequest request) {
        try {
            InputStreamResource file = cashBoxService.getExcel(request);

            String filename = "cash-boxes" + new SimpleDateFormat("-dd-MM-yyyy HH:mm").format(new Date()) + ".xlsx";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(e.getMessage());
        }
    }

    @GetMapping("/get-excel-cash-box/{id}")
    public ResponseEntity<?> getExcel(@PathVariable Long id) {
        try {
            InputStreamResource file = cashBoxService.getExcel(id);

            String filename = "cash-box" + new SimpleDateFormat("-dd-MM-yyyy HH:mm").format(new Date()) + ".xlsx";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file);
        } catch (EntityNotFoundException | IOException e) {
            if (e instanceof EntityNotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(e.getMessage());
        }
    }

    @GetMapping("/get-all-draft")
    public ResponseEntity<List<EnumResponse>> getAllDraft() {
        return ResponseEntity.ok(cashBoxService.getDraft());
    }

    @GetMapping("/get-all-staff")
    public ResponseEntity<List<StaffResponse>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaffManager());
    }

    @GetMapping("/get-all-bank-book")
    public ResponseEntity<List<BankBookResponseForCashBox>> getAllBankBook(@RequestParam(name = "id", required = false) Long id) {
        return ResponseEntity.ok(bankBookService.getBankBookListForCashBoxByUserId(id));
    }

    @GetMapping("/get-all-statement-type")
    public ResponseEntity<List<EnumResponse>> getAllArticleType() {
        return ResponseEntity.ok(articleService.getAllType());
    }

    @GetMapping("/get-cash-box-{id}")
    public ResponseEntity<?> getCashBoxResponse(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cashBoxService.getCashBoxResponseById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-all-article")
    public ResponseEntity<List<ArticleResponse>> getAllArticle(@RequestParam(name = "type", required = false) Boolean type) {
        return ResponseEntity.ok(articleService.getAllArticleResponseByType(type));
    }

    @PostMapping("/get-new-cash-box")
    public ResponseEntity<?> getNewCashBox(@RequestBody Boolean type) {
        try {
            return ResponseEntity.ok(cashBoxService.getNewCashBoxResponse(type));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update-cash-box")
    public ResponseEntity<?> updateCashBox(@Valid @RequestBody CashBoxUpdateRequest request,
                                           BindingResult bindingResult) {
        cashBoxValidator.isBankBookValidation(request.bankBookId(), bindingResult, request.type(),
                "CashBoxUpdateRequest", LocaleContextHolder.getLocale());
        cashBoxValidator.isNumberUniqueValidationWithId(request.id(), request.number(),
                bindingResult, request.type(), "CashBoxUpdateRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            cashBoxService.updateCashBoxByRequest(request);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save-cash-box")
    public ResponseEntity<?> saveCashBox(@Valid @RequestBody CashBoxSaveRequest request,
                                         BindingResult bindingResult) {
        cashBoxValidator.isBankBookValidation(request.bankBookId(), bindingResult, request.type(),
                "CashBoxSaveRequest", LocaleContextHolder.getLocale());
        cashBoxValidator.isNumberUniqueValidation(request.number(),
                bindingResult, request.type(), "CashBoxSaveRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            cashBoxService.saveCashBoxByRequest(request);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-cash-box/{id}")
    public ResponseEntity<?> deleteCashBoxById(@PathVariable Long id) {
        try {
            cashBoxService.deleteCashBoxById(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            if (e instanceof EntityNotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get-statistics-for-coming")
    public ResponseEntity<List<BigDecimal>> getStatisticsForComing() {
        return ResponseEntity.ok(cashBoxService.statisticSumByType(true));
    }

    @GetMapping("/get-statistics-for-costs")
    public ResponseEntity<List<BigDecimal>> getStatisticsForCosts() {
        return ResponseEntity.ok(cashBoxService.statisticSumByType(false));
    }
}

package lab.space.my_house_24.controller;

import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.util.RedirectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminPanelController {
    private final StaffService staffService;

    @GetMapping({"/", ""})
    public ModelAndView redirectAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, String> redirectMap = RedirectUtil.getRedirectMap();
        for (Map.Entry<String, String> map : redirectMap.entrySet()) {
            String key = map.getKey();
            String pagePath = map.getValue();

            if (authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals(key))) {
                return new ModelAndView("redirect:" + pagePath);
            }
        }
        return new ModelAndView("redirect:/payment-items");
    }

    @GetMapping("/get-current-staff-for-header")
    public ResponseEntity<?> getCurrentStaffForHeader() {
        return ResponseEntity.ok().body(staffService.getCurrentStaffForHeader());
    }
}

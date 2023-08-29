package lab.space.my_house_24.controller;

import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ThemeChangeController {
    private final StaffService staffService;

    @GetMapping("/get-theme")
    public ResponseEntity getTheme(){
        Staff staff = staffService.getStaffById(staffService.getCurrentStaff());
        return ResponseEntity.ok(staff.getTheme());
    }

    @PostMapping("/change-theme")
    public ResponseEntity changeTheme(@RequestParam Boolean theme){
        staffService.changeTheme(theme);
        return ResponseEntity.ok().build();
    }
}

package lab.space.my_house_24;

import lab.space.my_house_24.entity.Role;
import lab.space.my_house_24.entity.SecurityLevel;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.enums.Page;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.service.RoleService;
import lab.space.my_house_24.service.SecurityLevelService;
import lab.space.my_house_24.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Init implements CommandLineRunner {
    private final StaffService staffService;
    private final SecurityLevelService securityLevelService;
    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        log.info("################## START OF INITIALIZATION ##################");
        log.info("Try to find securityLevel");
        if (securityLevelService.getAllSecurityLevel().isEmpty()) {
            log.warn("Create custom SecurityLevel");
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.STATISTICS)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.CASH_BOX)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.BILLS)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.BANK_BOOKS)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.APARTMENTS)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.APARTMENTS_OWNERS)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.HOUSES)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.MESSAGES)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.MASTERS_APPLICATIONS)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.METER_READING)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.SETTINGS_PAGE)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.SERVICES)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.RATES)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.STAFF)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.ROLES)
            );
            securityLevelService.saveSecurityLevel(
                    new SecurityLevel()
                            .setPage(Page.REQUISITES)
            );
        } else log.info("SecurityLevel found");

        log.info("Try to find Role");
        if (roleService.getAllRole().isEmpty()) {
            log.warn("Create custom Role");
            roleService.saveRole(
                    new Role()
                            .setJobTitle(JobTitle.DIRECTOR)
                            .setSecurityLevelList(securityLevelService.getAllSecurityLevel())
            );
            roleService.saveRole(
                    new Role()
                            .setJobTitle(JobTitle.ACCOUNTANT)
                            .setSecurityLevelList(securityLevelService.getAllSecurityLevel())
            );
            roleService.saveRole(
                    new Role()
                            .setJobTitle(JobTitle.MANAGER)
                            .setSecurityLevelList(securityLevelService.getAllSecurityLevel())
            );
            roleService.saveRole(
                    new Role()
                            .setJobTitle(JobTitle.ELECTRIC)
                            .setSecurityLevelList(securityLevelService.getAllSecurityLevel())
            );
            roleService.saveRole(
                    new Role()
                            .setJobTitle(JobTitle.PLUMBER)
                            .setSecurityLevelList(securityLevelService.getAllSecurityLevel())
            );
        } else log.info("Role found");

        log.info("Try to find staff");
        if (staffService.getAllStaff().isEmpty()) {
            log.warn("Create custom staff");
            staffService.saveStaff(
                    new Staff()
                            .setEmail("admin@gmail.com")
                            .setPassword("$2a$12$ltMYSFmijVGzBuDXVxVRh.s2Z82aVhyuW4X1Jm9QP.tq0JAPys30K")
                            .setFirstname("Admin")
                            .setLastname("Admin")
                            .setPhone("123123123")
                            .setStaffStatus(UserStatus.ACTIVE)
                            .setRole(roleService.getRoleByJobTitle(JobTitle.DIRECTOR))
            );
        } else log.info("Staff found");
        log.info("################## FINISH OF INITIALIZATION ##################");
    }

}

package lab.space.my_house_24;

import lab.space.my_house_24.entity.*;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Role;
import lab.space.my_house_24.entity.SecurityLevel;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.entity.settingsPage.*;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.enums.Page;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.service.*;
import lab.space.my_house_24.service.impl.AboutServiceImpl;
import lab.space.my_house_24.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Init implements CommandLineRunner {
    private final StaffService staffService;
    private final MainPageService mainPageService;
    private final AboutService aboutService;
    private final ContactService contactService;
    private final ServicePageService servicePageService;
    private final SecurityLevelService securityLevelService;
    private final RoleService roleService;
    private final BankBookService bankBookService;
    private final StatisticService statisticService;
    private final CashBoxService cashBoxService;

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

        log.info("Try to find statistic");
        if (statisticService.getAllStatistics().isEmpty()) {
            log.warn("Create custom statistic");
            List<CashBox> cashBoxes = cashBoxService.getAllCashBoxIsActive();
            List<BankBook> bankBooks = bankBookService.getAllBankBookIsActive();
            Statistic statistic = new Statistic()
                    .setCashBoxState(BigDecimal.ZERO)
                    .setBankBookBalance(BigDecimal.ZERO)
                    .setBankBookExpense(BigDecimal.ZERO);

            if (!cashBoxes.isEmpty()){
                statistic.setCashBoxState(cashBoxes.stream().map(CashBox::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
            }
            if (!bankBooks.isEmpty()){
                statistic.setBankBookBalance(bankBooks.stream().map(BankBook::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
                statistic.setBankBookExpense(bankBooks.stream().map(BankBook::getTotalPrice).filter(totalPrice -> totalPrice.compareTo(BigDecimal.ZERO) < 0).reduce(BigDecimal.ZERO, BigDecimal::add));
            }
            statisticService.saveStatistic(statistic);
        } else log.info("Statistic found");



        try{
            log.info("Try to find main page");
            mainPageService.findById(1L);
        }
        catch (EntityNotFoundException e){
            log.warn("Create custom main page");
            mainPageService.save(
                    MainPage.builder()
                            .id(1L)
                            .title("title")
                            .description("description")
                            .seo(Seo.builder().description("description").title("title").keyWords("keyWords").build())
                            .links(true)
                            .bannerList(new ArrayList<>(List.of(
                                    Banner.builder().name("name1").image("").description("descr1").build(),
                                    Banner.builder().name("name2").image("").description("descr2").build(),
                                    Banner.builder().name("name3").image("").description("descr3").build(),
                                    Banner.builder().name("name4").image("").description("descr4").build(),
                                    Banner.builder().name("name5").image("").description("descr5").build(),
                                    Banner.builder().name("name6").image("").description("descr6").build()
                            )))
                            .slide1("")
                            .slide2("")
                            .slide3("")
                            .build()
            );
        }

        try{
            log.info("Try to find about page");
            aboutService.findById(1L);
        }
        catch (EntityNotFoundException e){
            log.warn("Create custom about page");
            aboutService.save(
                    About.builder()
                            .id(1L)
                            .title("title")
                            .titleAdd("titleAdd")
                            .description("description")
                            .descriptionAdd("descriptionAdd")
                            .documentList(new ArrayList<>())
                            .photoList(new ArrayList<>())
                            .imageDirector("")
                            .seo(Seo.builder().description("description").title("title").keyWords("keyWords").build())
                            .build()
            );
        }

        try{
            log.info("Try to find contact page");
            contactService.findById(1L);
        }
        catch (EntityNotFoundException e){
            log.warn("Create custom contact page");
            contactService.save(
                    Contact.builder()
                            .id(1L)
                            .title("title")
                            .description("description")
                            .fullName("")
                            .url("")
                            .email("")
                            .codeMap("")
                            .location("")
                            .address("")
                            .seo(Seo.builder().description("description").title("title").keyWords("keyWords").build())
                            .build()
            );
        }

        try{
            log.info("Try to find service page");
            servicePageService.findById(1L);
        }
        catch (EntityNotFoundException e){
            log.warn("Create custom service page");
            servicePageService.save(
                    ServicePage.builder()
                            .id(1L)
                            .bannerList(new ArrayList<>())
                            .seo(Seo.builder().description("description").title("title").keyWords("keyWords").build())
                            .build()
            );
        }


        log.info("################## FINISH OF INITIALIZATION ##################");
    }

}

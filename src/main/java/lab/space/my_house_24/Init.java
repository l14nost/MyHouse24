package lab.space.my_house_24;

import lab.space.my_house_24.entity.*;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Role;
import lab.space.my_house_24.entity.SecurityLevel;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.entity.settingsPage.*;
import lab.space.my_house_24.enums.BankBookStatus;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.enums.Page;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.service.*;
import lab.space.my_house_24.service.impl.AboutServiceImpl;
import lab.space.my_house_24.service.*;
import lab.space.my_house_24.service.impl.HouseServiceImpl;
import lab.space.my_house_24.service.impl.UnitServiceImpl;
import lab.space.my_house_24.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
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
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RequisitesService requisitesService;
    private final UnitService unitService;
    private final ServiceService serviceService;
    private final HouseService houseService;
    private final RateService rateService;
    private final ArticleService articleService;

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
                            .setTheme(true)
                            .setStaffStatus(UserStatus.ACTIVE)
                            .setRole(roleService.getRoleByJobTitle(JobTitle.DIRECTOR))
                            .setHouseList(new HashSet<>())
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
            MainPage mainPage =  MainPage.builder()
                    .id(1L)
                    .title("title")
                    .description("description")
                    .seo(Seo.builder().description("description").title("title").keyWords("keyWords").build())
                    .links(true)
                    .bannerList(new ArrayList<>())
                    .slide1("")
                    .slide2("")
                    .slide3("")
                    .build();
            mainPage.addBanner(Banner.builder().name("name1").image("").description("descr1").mainPage(MainPage.builder().id(1L).build()).build());
            mainPage.addBanner(Banner.builder().name("name2").image("").description("descr2").mainPage(MainPage.builder().id(1L).build()).build());
            mainPage.addBanner(Banner.builder().name("name3").image("").description("descr3").mainPage(MainPage.builder().id(1L).build()).build());
            mainPage.addBanner(Banner.builder().name("name4").image("").description("descr4").mainPage(MainPage.builder().id(1L).build()).build());
            mainPage.addBanner(Banner.builder().name("name5").image("").description("descr5").mainPage(MainPage.builder().id(1L).build()).build());
            mainPage.addBanner(Banner.builder().name("name6").image("").description("descr6").mainPage(MainPage.builder().id(1L).build()).build());
            mainPageService.save(mainPage);
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
                            .number("")
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
            ServicePage servicePage =  ServicePage.builder()
                    .id(1L)
                    .bannerList(new ArrayList<>())
                    .seo(Seo.builder().description("description").title("title").keyWords("keyWords").build())
                    .build();
            servicePage.addBanner(Banner.builder().name("name1").image("").description("descr1").servicePage(ServicePage.builder().id(1L).build()).build());
            servicePageService.save(
                   servicePage
            );
        }
        try{
            log.info("Try to find requisites");
            requisitesService.findById(1L);
        }
        catch (EntityNotFoundException e){
            log.warn("Create custom requisites");
           requisitesService.save(Requisites.builder()
                  .info("Info")
                  .name("Name")
                  .id(1L).build());
        }
        log.info("Try to find user");
        if (userService.findAll().isEmpty()){
            log.info("Create custom user");
            User user = User.builder()
                    .userStatus(UserStatus.ACTIVE)
                    .firstname("Bob")
                    .lastname("Sponge")
                    .surname("Squarepants")
                    .password(passwordEncoder.encode("pass"))
                    .filename("")
                    .number("0633333333")
                    .telegram("0633333333")
                    .viber("0633333333")
                    .date(LocalDate.of(2005, 12,12).atStartOfDay(ZoneId.systemDefault()).toInstant())
                    .addDate(Instant.now())
                    .email("testUser@gmail.com")
                    .applicationList(new ArrayList<>())
                    .messageList(new ArrayList<>())
                    .apartmentList(new ArrayList<>())
                    .build();
            userService.save(user);
        }
        log.info("Try to find unit");
        if (unitService.getAllUnitDto().isEmpty()){
            log.info("Create custom unit");
            unitService.saveUnit(Unit.builder()
                            .name("L")
                            .serviceList(new ArrayList<>())
                            .id(1L)
                    .build());
        }

        log.info("Try to find service");
        if (serviceService.getAllService().isEmpty()){
            log.info("Create custom service");
            serviceService.saveService(Service.builder()
                            .unit(Unit.builder().id(1L).build())
                            .isActive(true)
                            .id(1L)
                            .meterReadingList(new ArrayList<>())
                            .serviceBillList(new ArrayList<>())
                            .name("Water")
                    .build());
        }

        log.info("Try to find rate");
        if (rateService.getAllRate().isEmpty()){
            log.info("Create custom service");
            Rate rate = Rate.builder()
                    .id(1L)
                    .name("Economy")
                    .description("Economy rate for apartments 1-2 rooms")
                    .apartmentList(new ArrayList<>())
                    .billList(new ArrayList<>())
                    .updateAt(Instant.now())
                    .priceRateList(new ArrayList<>())
                    .build();
            rate.getPriceRateList().add(
                    PriceRate.builder()
                            .price(new BigDecimal(100))
                            .service(Service.builder().id(1L).build())
                            .rate(rate)
                            .build()
            );
            rateService.saveRate(rate);
        }


        log.info("Try to find house");
        if (houseService.findAll().isEmpty()){
            log.info("Create custom house with floor, section && apartment");
            House house = House.builder()
                    .name("The Joinery")
                    .image1("")
                    .image2("")
                    .image3("")
                    .image4("")
                    .image5("")
                    .staffList(new HashSet<>())
                    .floorList(new ArrayList<>())
                    .sectionList(new ArrayList<>())
                    .address("St. Serednofontanska 100-g ")
                    .sectionList(new ArrayList<>())
                    .id(1L)
                    .apartmentList(new ArrayList<>())
                    .build();
            Floor floor = Floor.builder()
                    .house(house)
                    .name("1")
                    .apartmentList(new ArrayList<>())
                    .build();
            Section section = Section.builder()
                    .name("1")
                    .house(house)
                    .apartmentList(new ArrayList<>())
                    .build();
            Apartment apartment = Apartment.builder()
                    .house(house)
                    .number(100)
                    .user(User.builder().id(1L).build())
                    .section(section)
                    .floor(floor)
                    .rate(Rate.builder().id(1L).build())
                    .area(50.5)
                    .build();
            apartment.setBankBook(BankBook.builder().bankBookStatus(BankBookStatus.ACTIVE).apartment(apartment).cashBoxes(new ArrayList<>())
                            .totalPrice(BigDecimal.ZERO)
                            .bill(new ArrayList<>())
                            .number("00000-00001")
                    .build());
            floor.getApartmentList().add(apartment);
            section.getApartmentList().add(apartment);
            house.getFloorList().add(floor);
            house.getSectionList().add(section);
            house.getApartmentList().add(apartment);
            houseService.save(house);
        }





        log.info("################## FINISH OF INITIALIZATION ##################");



    }

}

package lab.space.my_house_24;

import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.Role;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class Init implements CommandLineRunner {
    private final StaffRepository staffRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Try to find staff");
        if (staffRepository.findAll().isEmpty()) {
            log.warn("Create custom staff");
            staffRepository.save(
                    new Staff()
                            .setEmail("admin@gmail.com")
                            .setPassword("$2a$12$75IrwcjJ19FF4XxE7P2mROGRME9LFsRax.muaQN7IafZ1m4/dcknO")
                            .setFirstname("Admin")
                            .setLastname("Admin")
                            .setStaffStatus(UserStatus.NEW)
                            .setRole(Role.DIRECTOR)
                            .setSecurityLevelList(new ArrayList<>())
            );
        } else log.info("Staff found");
    }

}

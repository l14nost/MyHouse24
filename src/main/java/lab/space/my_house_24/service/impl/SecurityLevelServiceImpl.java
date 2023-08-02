package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.SecurityLevel;
import lab.space.my_house_24.enums.Page;
import lab.space.my_house_24.repository.SecurityLevelRepository;
import lab.space.my_house_24.service.SecurityLevelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityLevelServiceImpl implements SecurityLevelService {

    private final SecurityLevelRepository securityLevelRepository;

    @Override
    public SecurityLevel getSecurityLevelByPage(Page page) {
        log.info("Try to search SecurityLevel by Page " + page);
        return securityLevelRepository.findByPage(page)
                .orElseThrow(() -> new EntityNotFoundException("SecurityLevel not found by Page " + page));
    }

    @Override
    public List<SecurityLevel> getAllSecurityLevel() {
        log.info("Try to search All SecurityLevels");
        return securityLevelRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<SecurityLevel> getAllSecurityLevelByRole(Long id) {
        log.info("Try to search SecurityLevels by Role id " + id);
        return getAllSecurityLevel();
    }

    @Override
    public void saveSecurityLevel(SecurityLevel securityLevel) {
        log.info("Try to save SecurityLevel");
        securityLevelRepository.save(securityLevel);
        log.info("Success save SecurityLevel");
    }

}

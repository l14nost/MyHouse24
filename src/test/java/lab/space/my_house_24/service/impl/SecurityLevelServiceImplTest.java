package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.SecurityLevel;
import lab.space.my_house_24.enums.Page;
import lab.space.my_house_24.repository.SecurityLevelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityLevelServiceImplTest {

    @Mock
    private SecurityLevelRepository securityLevelRepository;

    @InjectMocks
    private SecurityLevelServiceImpl securityLevelService;

    @Test
    void getSecurityLevelByPage() {
        when(securityLevelRepository.findByPage(any())).thenReturn(Optional.of(SecurityLevel.builder().build()));
        assertEquals(SecurityLevel.builder().build(), securityLevelService.getSecurityLevelByPage(Page.SETTINGS_PAGE));
    }

    @Test
    void getAllSecurityLevel() {
        List<SecurityLevel> securityLevels = List.of(
                SecurityLevel.builder()
                        .id(1L)
                        .build(),
                SecurityLevel.builder()
                        .id(2L)
                        .build(),
                SecurityLevel.builder()
                        .id(3L)
                        .build(),
                SecurityLevel.builder()
                        .id(4L)
                        .build()
        );
        when(securityLevelRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(securityLevels);
        List<SecurityLevel> articleResponses = securityLevelService.getAllSecurityLevel();
        assertEquals(4, articleResponses.size());
        assertEquals(SecurityLevel.class, articleResponses.iterator().next().getClass());
    }

    @Test
    void saveSecurityLevel() {
        securityLevelService.saveSecurityLevel(SecurityLevel.builder().build());
        verify(securityLevelRepository, times(1)).save(any());
    }
}

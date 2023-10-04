package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.enums.Master;
import lab.space.my_house_24.enums.MastersApplicationStatus;
import lab.space.my_house_24.model.masters_application.MastersApplicationRequest;
import lab.space.my_house_24.model.masters_application.MastersApplicationResponse;
import lab.space.my_house_24.model.masters_application.MastersApplicationSaveRequest;
import lab.space.my_house_24.model.masters_application.MastersApplicationUpdateRequest;
import lab.space.my_house_24.repository.MastersApplicationRepository;
import lab.space.my_house_24.service.ApartmentService;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.service.UserService;
import lab.space.my_house_24.specification.MastersApplicationSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MastersApplicationServiceImplTest {

    @Mock
    private MastersApplicationRepository repository;

    @Mock
    private StaffService staffService;

    @Mock
    private UserService userService;

    @Mock
    private ApartmentService apartmentService;

    @Mock
    private MastersApplicationSpecification specification;

    @InjectMocks
    private MastersApplicationServiceImpl mastersApplicationService;

    @Test
    void getMastersApplicationById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(MastersApplication.builder().build()));
        assertEquals(MastersApplication.builder().build(), mastersApplicationService.getMastersApplicationById(1L));
    }

    @Test
    void getMastersApplicationResponseById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(MastersApplication.builder()
                .id(1L)
                .createAt(Instant.now())
                .description("Test")
                .comment("Test")
                .master(Master.ANY_MASTER)
                .mastersApplicationStatus(MastersApplicationStatus.IN_PROCESS)
                .staff(null)
                .dateTime(LocalDateTime.now())
                .apartment(Apartment.builder()
                        .id(1L)
                        .number(101010)
                        .floor(Floor.builder().name("Test").build())
                        .house(House.builder()
                                .id(1L)
                                .name("Test")
                                .build())
                        .section(Section.builder().name("Test").build())
                        .user(User.builder().id(1L).firstname("Test").lastname("Test").surname("Test").number("123456789").build())
                        .build())
                .build()));
        mastersApplicationService.getMastersApplicationResponseById(1L);
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void updateMastersApplicationByRequest() {
        MastersApplicationUpdateRequest request = MastersApplicationUpdateRequest.builder()
                .id(1L)
                .description("Test")
                .comment("Test")
                .master(Master.ANY_MASTER)
                .mastersApplicationStatus(MastersApplicationStatus.NEW)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .staffId(null)
                .apartmentId(1L)
                .userId(1L)
                .build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(MastersApplication.builder().id(1L).build()));
        when(userService.findEntityById(anyLong())).thenReturn(User.builder().build());
        when(apartmentService.findById(anyLong())).thenReturn(Apartment.builder().build());

        mastersApplicationService.updateMastersApplicationByRequest(request);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any());
    }

    @Test
    void saveMastersApplicationByRequest() {
        MastersApplicationSaveRequest request = MastersApplicationSaveRequest.builder()
                .description("Test")
                .comment("Test")
                .master(Master.ANY_MASTER)
                .mastersApplicationStatus(MastersApplicationStatus.NEW)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .staffId(null)
                .apartmentId(1L)
                .userId(1L)
                .build();
        when(userService.findEntityById(anyLong())).thenReturn(User.builder().build());
        when(apartmentService.findById(anyLong())).thenReturn(Apartment.builder().build());

        mastersApplicationService.saveMastersApplicationByRequest(request);

        verify(repository, times(1)).save(any());
    }

    @Test
    void saveMastersApplication() {
        mastersApplicationService.saveMastersApplication(MastersApplication.builder().build());
        verify(repository, times(1)).save(any());
    }

    @Test
    void deleteMastersApplicationById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(MastersApplication.builder().build()));
        mastersApplicationService.deleteMastersApplicationById(1L);
        verify(repository, times(1)).delete((MastersApplication) any());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void getAllMastersApplication() {
        Page<MastersApplication> mastersApplications = new PageImpl<>(
                List.of(
                        MastersApplication.builder()
                                .id(1L)
                                .createAt(Instant.now())
                                .description("Test")
                                .comment("Test")
                                .master(Master.ANY_MASTER)
                                .mastersApplicationStatus(MastersApplicationStatus.IN_PROCESS)
                                .staff(null)
                                .dateTime(LocalDateTime.now())
                                .apartment(Apartment.builder()
                                        .id(1L)
                                        .number(101010)
                                        .floor(Floor.builder().name("Test").build())
                                        .house(House.builder()
                                                .id(1L)
                                                .name("Test")
                                                .build())
                                        .section(Section.builder().name("Test").build())
                                        .user(User.builder().id(1L).firstname("Test").lastname("Test").surname("Test").number("123456789").build())
                                        .build())
                                .build(),
                        MastersApplication.builder()
                                .id(2L)
                                .createAt(Instant.now())
                                .description("Test")
                                .comment("Test")
                                .master(Master.ANY_MASTER)
                                .mastersApplicationStatus(MastersApplicationStatus.IN_PROCESS)
                                .staff(null)
                                .dateTime(LocalDateTime.now())
                                .apartment(Apartment.builder()
                                        .id(1L)
                                        .number(101010)
                                        .floor(Floor.builder().name("Test").build())
                                        .house(House.builder()
                                                .id(1L)
                                                .name("Test")
                                                .build())
                                        .section(Section.builder().name("Test").build())
                                        .user(User.builder().id(1L).firstname("Test").lastname("Test").surname("Test").number("123456789").build())
                                        .build())
                                .build(),
                        MastersApplication.builder()
                                .id(3L)
                                .createAt(Instant.now())
                                .description("Test")
                                .comment("Test")
                                .master(Master.ANY_MASTER)
                                .mastersApplicationStatus(MastersApplicationStatus.IN_PROCESS)
                                .staff(null)
                                .dateTime(LocalDateTime.now())
                                .apartment(Apartment.builder()
                                        .id(1L)
                                        .number(101010)
                                        .floor(Floor.builder().name("Test").build())
                                        .house(House.builder()
                                                .id(1L)
                                                .name("Test")
                                                .build())
                                        .section(Section.builder().name("Test").build())
                                        .user(User.builder().id(1L).firstname("Test").lastname("Test").surname("Test").number("123456789").build())
                                        .build())
                                .build(),
                        MastersApplication.builder()
                                .id(4L)
                                .createAt(Instant.now())
                                .description("Test")
                                .comment("Test")
                                .master(Master.ANY_MASTER)
                                .mastersApplicationStatus(MastersApplicationStatus.IN_PROCESS)
                                .staff(null)
                                .dateTime(LocalDateTime.now())
                                .apartment(Apartment.builder()
                                        .id(1L)
                                        .number(101010)
                                        .floor(Floor.builder().name("Test").build())
                                        .house(House.builder()
                                                .id(1L)
                                                .name("Test")
                                                .build())
                                        .section(Section.builder().name("Test").build())
                                        .user(User.builder().id(1L).firstname("Test").lastname("Test").surname("Test").number("123456789").build())
                                        .build())
                                .build()
                )
        );
        when(repository.findAll((Specification<MastersApplication>) any(), any(PageRequest.class))).thenReturn(mastersApplications);
        Page<MastersApplicationResponse> mastersApplicationResponses = mastersApplicationService.getAllMastersApplication(MastersApplicationRequest.builder().pageIndex(1).build());
        assertEquals(4, mastersApplicationResponses.getTotalElements());
        assertEquals(MastersApplicationResponse.class, mastersApplicationResponses.iterator().next().getClass());
    }

    @Test
    void getAllStatus() {
        assertEquals(3, mastersApplicationService.getAllStatus().size());
    }

    @Test
    void getAllTypeMaster() {
        assertEquals(3, mastersApplicationService.getAllTypeMaster().size());
    }

    @Test
    void countByStatus() {
        when(repository.countByMastersApplicationStatus(any())).thenReturn(1L);
        assertEquals(1L, mastersApplicationService.countByStatus(MastersApplicationStatus.IN_PROCESS));
    }
}
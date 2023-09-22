package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.Floor;
import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.entity.Section;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.model.house.*;
import lab.space.my_house_24.repository.HouseRepository;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.specification.HouseSpecification;
import lab.space.my_house_24.specification.HouseSpecificationForSelect;
import lab.space.my_house_24.util.FileHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HouseServiceImplTest {
    @Mock
    private HouseRepository houseRepository;

    @Mock
    private StaffService staffService;

    @InjectMocks
    private HouseServiceImpl houseService;

    @Test
    void houseListForTable() {
        when(houseRepository.findAll()).thenReturn(List.of(
                House.builder().build(),
                House.builder().build(),
                House.builder().build(),
                House.builder().build()
        ));

        assertEquals(4, houseService.houseListForTable().size());
        assertEquals(HouseResponseForTable.class, houseService.houseListForTable().get(0).getClass());
    }

    @Test
    void findById() {
        House house = House.builder()
                .id(1L)
                .build();
        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));
        assertEquals(house, houseService.findById(1L));
        assertEquals(House.class, houseService.findById(1L).getClass());
    }

    @Test
    void finaAllForMain() {
        Page<House> housePage = new PageImpl<>(List.of(
                House.builder().build(),
                House.builder().build(),
                House.builder().build(),
                House.builder().build()
        ));
        HouseRequestForMainPage houseRequestForMainPage = HouseRequestForMainPage.builder()
                .page(1)
                .id(1L)
                .name("Name")
                .address("address")
                .build();
        HouseSpecification houseSpecification = HouseSpecification.builder().houseRequestForMainPage(houseRequestForMainPage).build();
        when(houseRepository.findAll(houseSpecification, PageRequest.of(1,10))).thenReturn(housePage);

        assertEquals(4, houseService.findAllForMain(houseRequestForMainPage).getTotalElements());
        assertEquals(HouseResponseForMain.class, houseService.findAllForMain(houseRequestForMainPage).iterator().next().getClass());
    }

    @Test
    void deleteById() {
        House house = House.builder()
                .id(1L)
                .image1("")
                .image2("")
                .image3("")
                .image4("")
                .image5("")
                .build();
        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));
        houseService.deleteById(1L);
        verify(houseRepository,times(1)).deleteById(1L);
    }

    @Test
    void findByIdForCard() {
        House house = House.builder()
                .id(1L)
                .staffList(Set.of())
                .sectionList(List.of())
                .floorList(List.of())
                .build();
        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));
        assertEquals(HouseResponseForCard.builder().section(0).floor(0).users(List.of()).build(), houseService.findByIdForCard(1L));
        assertEquals(HouseResponseForCard.class, houseService.findByIdForCard(1L).getClass());
    }

    @Test
    void save() {
        HouseRequestForAddPage houseRequestForAddPage =
                HouseRequestForAddPage.builder()
                        .address("address")
                        .floorNameList(new ArrayList<>(List.of("1","2")))
                        .sectionNameList(new ArrayList<>(List.of("1","2")))
                        .userList(new ArrayList<>(List.of(1L, 2L)))
                        .name("name")
                        .image1(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                        .image2(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                        .image3(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                        .image4(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                        .image5(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                        .build();
        when(staffService.getStaffById(1L)).thenReturn(Staff.builder().houseList(new HashSet<>(Set.of())).build());
        when(staffService.getStaffById(2L)).thenReturn(Staff.builder().houseList(new HashSet<>(Set.of())).build());
        houseService.save(houseRequestForAddPage);
        verify(houseRepository, times(1)).save(any(House.class));
    }

    @Test
    void save_fileEmpty() throws IOException {
        HouseRequestForAddPage houseRequestForAddPage =
                HouseRequestForAddPage.builder()
                        .address("address")
                        .floorNameList(new ArrayList<>(List.of("1","2")))
                        .sectionNameList(new ArrayList<>(List.of("1","2")))
                        .userList(new ArrayList<>(List.of(1L, 2L)))
                        .name("name")
                        .image1(new MockMultipartFile("file","example.txt","text/plain",new ByteArrayResource(new byte[0]).getInputStream()))
                        .image2(new MockMultipartFile("file","example.txt","text/plain",new ByteArrayResource(new byte[0]).getInputStream()))
                        .image3(new MockMultipartFile("file","example.txt","text/plain",new ByteArrayResource(new byte[0]).getInputStream()))
                        .image4(new MockMultipartFile("file","example.txt","text/plain",new ByteArrayResource(new byte[0]).getInputStream()))
                        .image5(new MockMultipartFile("file","example.txt","text/plain",new ByteArrayResource(new byte[0]).getInputStream()))
                        .build();
        when(staffService.getStaffById(1L)).thenReturn(Staff.builder().houseList(new HashSet<>(Set.of())).build());
        when(staffService.getStaffById(2L)).thenReturn(Staff.builder().houseList(new HashSet<>(Set.of())).build());
        houseService.save(houseRequestForAddPage);
        verify(houseRepository, times(1)).save(any(House.class));
    }



    @Test
    void findByIdForEdit() {
        House house = House.builder()
                .id(1L)
                .sectionList(List.of())
                .floorList(List.of())
                .staffList(Set.of())
                .build();
        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));
        assertEquals(HouseResponseForEdit.builder().id(1L).staffList(List.of()).sectionList(List.of()).floorList(List.of()).build(), houseService.findByIdForEdit(1L));
        assertEquals(HouseResponseForEdit.class, houseService.findByIdForEdit(1L).getClass());
    }

    @Test
    void update() {
        House house = House.builder()
                .id(1L)
                .staffList(new HashSet<>(Set.of(Staff.builder().id(3L).houseList(Set.of()).build())))
                .sectionList(new ArrayList<>(List.of(Section.builder().id(1L).build(), Section.builder().build())))
                .floorList(new ArrayList<>(List.of(Floor.builder().id(1L).build(), Floor.builder().build())))
                .build();
        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));

        HouseRequestForEditPage houseRequestForAddPage =
                HouseRequestForEditPage.builder()
                        .deleteFloorList(List.of(1L))
                        .deleteSectionList(List.of(1L))
                        .deleteStaffList(List.of(3L))
                        .address("address")
                        .floorNameList(new ArrayList<>(List.of("1","2")))
                        .sectionNameList(new ArrayList<>(List.of("1","2")))
                        .userList(new ArrayList<>(List.of(1L, 2L)))
                        .name("name")
                        .image1(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                        .image2(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                        .image3(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                        .image4(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                        .image5(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                        .build();
        when(staffService.getStaffById(1L)).thenReturn(Staff.builder().houseList(new HashSet<>(Set.of())).build());
        when(staffService.getStaffById(2L)).thenReturn(Staff.builder().houseList(new HashSet<>(Set.of())).build());
        when(staffService.getStaffById(3L)).thenReturn(Staff.builder().id(3L).houseList(new HashSet<>(Set.of())).build());

        houseService.update(houseRequestForAddPage,1L);
        verify(houseRepository, times(1)).save(any(House.class));


    }

    @Test
    void houseResponseForSelect() {
        Page<House> housePage = new PageImpl<>(List.of(
                House.builder().build(),
                House.builder().build(),
                House.builder().build(),
                House.builder().build()
        ));
        HouseSpecificationForSelect houseSpecificationForSelect = HouseSpecificationForSelect.builder().search("search").build();
        when(houseRepository.findAll(houseSpecificationForSelect, PageRequest.of(1,5))).thenReturn(housePage);
        Page<HouseResponseForTable> houseResponseForTables = houseService.houseResponseForSelect(1,"search");
        assertEquals(4, houseResponseForTables.getTotalElements());
        assertEquals(HouseResponseForTable.class, houseResponseForTables.iterator().next().getClass());
    }

    @Test
    void count() {
        when(houseRepository.count()).thenReturn(3L);
        assertEquals(3L, houseService.count());
    }

    @Test
    void testSave() {
        House house = House.builder().build();
        houseService.save(house);
        verify(houseRepository, times(1)).save(house);
    }
}
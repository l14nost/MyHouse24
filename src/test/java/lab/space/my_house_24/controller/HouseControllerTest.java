package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.space.my_house_24.model.house.*;
import lab.space.my_house_24.service.HouseService;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.validator.HouseValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HouseController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class HouseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HouseService houseService;
    @MockBean
    private HouseValidator houseValidator;
    @MockBean
    private StaffService staffService;

    @Test
    void mainPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/houses"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/houses/house-main"));
    }

    @Test
    void getAllHouse() throws Exception {
        HouseRequestForMainPage houseRequestForMainPage = HouseRequestForMainPage.builder().page(1).build();
        when(houseService.findAllForMain(houseRequestForMainPage)).thenReturn(new PageImpl<>(List.of()));
        mockMvc.perform(MockMvcRequestBuilders.post("/houses/get-all-house")
                        .content(objectMapper.writeValueAsString(houseRequestForMainPage))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new PageImpl<>(List.of()))));
    }

    @Test
    void deleteHouse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/houses/delete-house/1"))
                .andExpect(status().isOk());
        verify(houseService, times(1)).deleteById(1L);
    }

    @Test
    void houseCard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/houses/house-card/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/houses/house-card"));
    }

    @Test
    void getHouseById() throws Exception {
        HouseResponseForCard houseResponseForCard = HouseResponseForCard.builder()
                .build();
        when(houseService.findByIdForCard(1L)).thenReturn(houseResponseForCard);
        mockMvc.perform(MockMvcRequestBuilders.get("/houses/get-house-by-id/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(houseResponseForCard)));

    }

    @Test
    void addHousePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/houses/add-house"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/houses/house-add"));
    }

    @Test
    void addHouse() throws Exception {
        HouseRequestForAddPage houseRequestForAddPage =
                HouseRequestForAddPage.builder()
                        .address("address")
                        .floorNameList(new ArrayList<>(List.of("1","2")))
                        .sectionNameList(new ArrayList<>(List.of("1","2")))
                        .userList(new ArrayList<>(List.of(1L, 2L)))
                        .name("name")
                        .image1(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image2(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image3(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image4(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image5(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/houses/add-house")
                        .flashAttr("houseRequestForAddPage", houseRequestForAddPage))
                .andExpect(status().isOk());
        verify(houseValidator, times(1)).uniqueStaffForHouse(anyList(), any(BindingResult.class));
        verify(houseService, times(1)).save(any(HouseRequestForAddPage.class));
    }

    @Test
    void addHouse_Valid() throws Exception {
        HouseRequestForAddPage houseRequestForAddPage =
                HouseRequestForAddPage.builder()
                        .address("address")
                        .floorNameList(new ArrayList<>(List.of("1","2")))
                        .sectionNameList(new ArrayList<>(List.of("1","2")))
                        .userList(new ArrayList<>(List.of(1L, 2L)))
                        .name("name")
                        .image1(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                        .image2(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image3(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image4(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image5(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/houses/add-house")
                        .flashAttr("houseRequestForAddPage", houseRequestForAddPage))
                .andExpect(status().isBadRequest());
        verify(houseValidator, times(1)).uniqueStaffForHouse(anyList(), any(BindingResult.class));
        verify(houseService, times(0)).save(any(HouseRequestForAddPage.class));
    }

    @Test
    void editHousePage() throws Exception {
        when(houseService.findByIdForEdit(1L)).thenReturn(HouseResponseForEdit.builder().name("").build());
        when(staffService.getAllStaffDtoForHouse("")).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/houses/edit-house/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/houses/house-edit"));
    }

    @Test
    void editHouse() throws Exception {
        HouseRequestForEditPage houseRequestForEditPage =
                HouseRequestForEditPage.builder()
                        .address("address")
                        .floorNameList(new ArrayList<>(List.of("1","2")))
                        .sectionNameList(new ArrayList<>(List.of("1","2")))
                        .userList(new ArrayList<>(List.of(1L, 2L)))
                        .name("name")
                        .image1(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image2(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image3(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image4(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image5(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .deleteFloorList(List.of())
                        .deleteSectionList(List.of())
                        .deleteStaffList(List.of())
                        .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/houses/edit-house/1")
                        .flashAttr("houseRequestForEditPage", houseRequestForEditPage))
                .andExpect(status().isOk());
        verify(houseValidator, times(1)).uniqueStaffForHouse(anyList(), any(BindingResult.class));
        verify(houseService, times(1)).update(any(HouseRequestForEditPage.class), anyLong());
    }

    @Test
    void editHouse_Valid() throws Exception {
        HouseRequestForEditPage houseRequestForEditPage =
                HouseRequestForEditPage.builder()
                        .address("address")
                        .floorNameList(new ArrayList<>(List.of("1","2")))
                        .sectionNameList(new ArrayList<>(List.of("1","2")))
                        .userList(new ArrayList<>(List.of(1L, 2L)))
                        .name("name")
                        .image1(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                        .image2(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image3(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image4(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .image5(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                        .deleteFloorList(List.of())
                        .deleteSectionList(List.of())
                        .deleteStaffList(List.of())
                        .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/houses/edit-house/1")
                        .flashAttr("houseRequestForEditPage", houseRequestForEditPage))
                .andExpect(status().isBadRequest());
        verify(houseValidator, times(1)).uniqueStaffForHouse(anyList(), any(BindingResult.class));
        verify(houseService, times(0)).update(any(HouseRequestForEditPage.class), anyLong());
    }

    @Test
    void userForApartmentTable() throws Exception {
        when(houseService.houseResponseForSelect(1,"search")).thenReturn(new PageImpl<>(List.of()));
        mockMvc.perform(MockMvcRequestBuilders.get("/houses/get-houses-for-select")
                        .param("page", "1")
                        .param("search", "search"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new PageImpl<>(List.of()))));
    }
}
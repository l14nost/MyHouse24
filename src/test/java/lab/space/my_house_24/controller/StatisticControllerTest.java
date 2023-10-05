package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.space.my_house_24.entity.Statistic;
import lab.space.my_house_24.enums.MastersApplicationStatus;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatisticController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private HouseService houseService;
    @MockBean
    private BankBookService bankBookService;
    @MockBean
    private MastersApplicationService mastersApplicationService;
    @MockBean
    private ApartmentService apartmentService;
    @MockBean
    private CashBoxService cashBoxService;
    @MockBean
    private StatisticService statisticService;

    @Test
    void showStatisticPage() throws Exception {
        when(userService.countByStatus(UserStatus.ACTIVE)).thenReturn(10L);
        when(apartmentService.count()).thenReturn(10L);
        when(houseService.count()).thenReturn(10L);
        when(bankBookService.count()).thenReturn(10L);
        when(mastersApplicationService.countByStatus(MastersApplicationStatus.IN_PROCESS)).thenReturn(10L);
        when(mastersApplicationService.countByStatus(MastersApplicationStatus.NEW)).thenReturn(10L);
        when(cashBoxService.statisticCashStatement()).thenReturn(BigDecimal.ZERO);
        when(cashBoxService.statisticAccountBalance()).thenReturn(BigDecimal.ZERO);

        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/statistic/statistic"));
    }

    @Test
    void getStatistic() throws Exception {
        when(statisticService.getAllStatistics()).thenReturn(List.of(Statistic.builder().id(1L).bankBookBalance(BigDecimal.ZERO).bankBookBalance(BigDecimal.TEN).bankBookExpense(BigDecimal.ZERO).cashBoxState(BigDecimal.ZERO).build()));

        mockMvc.perform(get("/statistics/get-statistic"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(Statistic.builder().id(1L).bankBookBalance(BigDecimal.ZERO).bankBookBalance(BigDecimal.TEN).bankBookExpense(BigDecimal.ZERO).cashBoxState(BigDecimal.ZERO).build())));
    }
}
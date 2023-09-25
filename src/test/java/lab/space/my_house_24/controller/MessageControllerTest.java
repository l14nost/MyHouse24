package lab.space.my_house_24.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.controller.settingsPage.ServicePageController;
import lab.space.my_house_24.model.message.MessageMainPageRequest;
import lab.space.my_house_24.model.message.MessageRequestForSend;
import lab.space.my_house_24.model.message.MessageResponseForCard;
import lab.space.my_house_24.service.MessageService;
import lab.space.my_house_24.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MessageService messageService;

    @Test
    void messageMainPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/messages"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/message/message-main"));
    }

    @Test
    void getAllMessage() throws Exception {
        MessageMainPageRequest mainPageRequest = new MessageMainPageRequest(0,"");
        when(messageService.findAllForMessageMain(mainPageRequest)).thenReturn(new PageImpl<>(List.of()));
        mockMvc.perform(MockMvcRequestBuilders.post("/messages/get-all-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mainPageRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new PageImpl<>(List.of()))));

    }

    @Test
    void messageCardPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/messages/message-card/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/message/message-card"));
    }

    @Test
    void getMessageById() throws Exception {
        when(messageService.findByIdForCard(1L)).thenReturn(MessageResponseForCard.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/messages/get-message-by-id/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(MessageResponseForCard.builder().build())));

    }

    @Test
    void getMessageById_NotFound() throws Exception {
        when(messageService.findByIdForCard(1L)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.get("/messages/get-message-by-id/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));

    }

    @Test
    void deleteMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/messages/delete-message/1"))
                .andExpect(status().isOk());

        verify(messageService, times(1)).deleteMessage(1L);
    }

    @Test
    void addMessagePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/messages/add-message"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/message/message-add"));
    }

    @Test
    void addMessage() throws Exception {
        MessageRequestForSend messageRequestForSend = new MessageRequestForSend("subject", "message", "messageStyle", true, 1L, 1L, 1L, 1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/messages/add-message")
                        .content(objectMapper.writeValueAsString(messageRequestForSend))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(messageService, times(1)).sendMessage(messageRequestForSend);
    }
    @Test
    void addMessage_Valid() throws Exception {
        MessageRequestForSend messageRequestForSend = new MessageRequestForSend("", "message", "messageStyle", true, 1L, 1L, 1L, 1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/messages/add-message")
                        .content(objectMapper.writeValueAsString(messageRequestForSend))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(messageService, times(0)).sendMessage(messageRequestForSend);
    }

    @Test
    void deleteMessages() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/messages/delete-messages")
                        .param("ids", "1,2,3"))
                .andExpect(status().isOk());
        verify(messageService,times(3)).deleteMessage(anyLong());
    }

    @Test
    void addMessageDebtPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/messages/add-message-debt"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/message/message-add"));
    }

}
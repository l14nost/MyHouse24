package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.Message;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.model.message.MessageMainPageRequest;
import lab.space.my_house_24.model.message.MessageRequestForSend;
import lab.space.my_house_24.model.message.MessageResponseForCard;
import lab.space.my_house_24.model.message.MessageResponseForMain;
import lab.space.my_house_24.repository.MessageRepository;
import lab.space.my_house_24.service.ApartmentService;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.specification.MessageSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private StaffService staffService;
    @Mock
    private ApartmentService apartmentService;
    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    void findAllForMessageMain() {
        Page<Message> messagePage = new PageImpl<>(List.of(
                Message.builder().sendDate(LocalDateTime.of(2023,9,9,10,10).atZone(ZoneId.systemDefault()).toInstant()).build(),
                Message.builder().sendDate(LocalDateTime.of(2023,9,9,10,10).atZone(ZoneId.systemDefault()).toInstant()).build(),
                Message.builder().sendDate(LocalDateTime.of(2023,9,9,10,10).atZone(ZoneId.systemDefault()).toInstant()).build(),
                Message.builder().sendDate(LocalDateTime.of(2023,9,9,10,10).atZone(ZoneId.systemDefault()).toInstant()).build(),
                Message.builder().sendDate(LocalDateTime.of(2023,9,9,10,10).atZone(ZoneId.systemDefault()).toInstant()).build()
        ));
        MessageSpecification messageSpecification = MessageSpecification.builder().mainPageRequest(new MessageMainPageRequest(0,"title")).build();
        when(messageRepository.findAll(messageSpecification, PageRequest.of(0,10))).thenReturn(messagePage);
        Page<MessageResponseForMain> messageResponseForMains = new PageImpl<>(List.of(
                MessageResponseForMain.builder().sendDate(LocalDateTime.of(2023,9,9,10,10)).build(),
                MessageResponseForMain.builder().sendDate(LocalDateTime.of(2023,9,9,10,10)).build(),
                MessageResponseForMain.builder().sendDate(LocalDateTime.of(2023,9,9,10,10)).build(),
                MessageResponseForMain.builder().sendDate(LocalDateTime.of(2023,9,9,10,10)).build(),
                MessageResponseForMain.builder().sendDate(LocalDateTime.of(2023,9,9,10,10)).build()
        ));
        Page<MessageResponseForMain> messageResponseForMains1 = messageService.findAllForMessageMain(new MessageMainPageRequest(0,"title"));
        assertEquals(messageResponseForMains.getTotalElements(), messageResponseForMains1.getTotalElements());
    }

    @Test
    void findAllForMessageMain_KeyWordNull() {
        Page<Message> messagePage = new PageImpl<>(List.of(
                Message.builder().sendDate(LocalDateTime.of(2023,9,9,10,10).atZone(ZoneId.systemDefault()).toInstant()).build(),
                Message.builder().sendDate(LocalDateTime.of(2023,9,9,10,10).atZone(ZoneId.systemDefault()).toInstant()).build(),
                Message.builder().sendDate(LocalDateTime.of(2023,9,9,10,10).atZone(ZoneId.systemDefault()).toInstant()).build(),
                Message.builder().sendDate(LocalDateTime.of(2023,9,9,10,10).atZone(ZoneId.systemDefault()).toInstant()).build(),
                Message.builder().sendDate(LocalDateTime.of(2023,9,9,10,10).atZone(ZoneId.systemDefault()).toInstant()).build()
        ));
        when(messageRepository.findAll(PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"id")))).thenReturn(messagePage);
        Page<MessageResponseForMain> messageResponseForMains = new PageImpl<>(List.of(
                MessageResponseForMain.builder().sendDate(LocalDateTime.of(2023,9,9,10,10)).build(),
                MessageResponseForMain.builder().sendDate(LocalDateTime.of(2023,9,9,10,10)).build(),
                MessageResponseForMain.builder().sendDate(LocalDateTime.of(2023,9,9,10,10)).build(),
                MessageResponseForMain.builder().sendDate(LocalDateTime.of(2023,9,9,10,10)).build(),
                MessageResponseForMain.builder().sendDate(LocalDateTime.of(2023,9,9,10,10)).build()
        ));
        Page<MessageResponseForMain> messageResponseForMains1 = messageService.findAllForMessageMain(new MessageMainPageRequest(0,""));
        assertEquals(messageResponseForMains.getTotalElements(), messageResponseForMains1.getTotalElements());
    }

    @Test
    void findByIdForCard() {
        when(messageRepository.findById(1L)).thenReturn(Optional.of(Message.builder().sendDate(LocalDateTime.of(2023,9,9,10,10).atZone(ZoneId.systemDefault()).toInstant()).staff(Staff.builder().build()).build()));
        assertEquals(MessageResponseForCard.builder()
                .staffFullName("null null")
                .sendDate(LocalDateTime.of(2023,9,9,10,10))
                .build(), messageService.findByIdForCard(1L));
    }

    @Test
    void sendMessage() {
        MessageRequestForSend messageRequestForSend = new MessageRequestForSend("subject", "message", "messageStyle", true, 1L, 1L, 1L, 1L);
        when(staffService.getCurrentStaff()).thenReturn(1L);
        when(apartmentService.apartmentListForMessage(messageRequestForSend.house(), messageRequestForSend.section(), messageRequestForSend.floor(), messageRequestForSend.apartment(), messageRequestForSend.debt()))
                .thenReturn(List.of(Apartment.builder().user(User.builder().id(1L).messageList(new ArrayList<>()).build()).build()));
        messageService.sendMessage(messageRequestForSend);
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void deleteMessage() {
        messageService.deleteMessage(1L);
        verify(messageRepository, times(1)).deleteById(1L);
    }
}
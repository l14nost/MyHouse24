package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.mapper.MessageMapper;
import lab.space.my_house_24.model.message.MessageMainPageRequest;
import lab.space.my_house_24.model.message.MessageRequestForSend;
import lab.space.my_house_24.model.message.MessageResponseForCard;
import lab.space.my_house_24.model.message.MessageResponseForMain;
import lab.space.my_house_24.repository.MessageRepository;
import lab.space.my_house_24.service.*;
import lab.space.my_house_24.specification.MessageSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ApartmentService apartmentService;
    private final StaffService staffService;
    @Override
    public Page<MessageResponseForMain> findAllForMessageMain(MessageMainPageRequest mainPageRequest) {
        log.info("Try to find all message for main page");
        MessageSpecification messageSpecification = MessageSpecification.builder().mainPageRequest(mainPageRequest).build();
        if (mainPageRequest.keyWord()!=null && !mainPageRequest.keyWord().isBlank()){
            log.info("Try to search message by keyword: "+mainPageRequest.keyWord());
            return messageRepository.findAll(messageSpecification, PageRequest.of(mainPageRequest.page(),10)).map(MessageMapper::entityToDtoForMain);
        }
        return messageRepository.findAll(PageRequest.of(mainPageRequest.page(),10)).map(MessageMapper::entityToDtoForMain);
    }

    @Override
    public MessageResponseForCard findByIdForCard(Long id) {
        log.info("Try to find message dto for card page by id: "+id);
        return MessageMapper.entityToDtoForCard(messageRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Message by id "+id+" is not found")));
    }

    @Override
    public void sendMessage(MessageRequestForSend messageRequestForSend) {
        log.info("Try to send message");
        Message message = Message.builder()
                .isActive(messageRequestForSend.debt())
                .description(messageRequestForSend.message())
                .descriptionStyle(messageRequestForSend.messageStyle())
                .title(messageRequestForSend.subject())
                .sendDate(LocalDateTime.now().withNano(0).atZone(ZoneId.systemDefault()).toInstant())
                .staff(Staff.builder().id(staffService.getCurrentStaff()).build())
                .users(new HashSet<>())
                .isCheck(false)
                .build();
        if (messageRequestForSend.house()!=0){
            message.setHouse(House.builder().id(messageRequestForSend.house()).build());
            if (messageRequestForSend.section()!=0){
                message.setSection(Section.builder().id(messageRequestForSend.section()).build());
            }
            if (messageRequestForSend.floor()!=0){
                message.setFloor(Floor.builder().id(messageRequestForSend.floor()).build());
            }
        }
        List<Apartment> apartmentList = apartmentService.apartmentListForMessage(messageRequestForSend.house(), messageRequestForSend.section(), messageRequestForSend.floor(), messageRequestForSend.apartment(), messageRequestForSend.debt());
        for (Apartment apartment: apartmentList){
            message.addUser(apartment.getUser());
        }
        messageRepository.save(message);
        log.info("Message was send");

    }
    @Override
    public void deleteMessage(Long id) {
        log.info("Try to delete message by id: "+id);
        messageRepository.deleteById(id);
        log.info("Message was delete");
    }
}

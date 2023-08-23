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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final HouseService houseService;
    private final SectionService sectionService;
    private final FloorService floorService;
    private final ApartmentService apartmentService;
    @Override
    public Page<MessageResponseForMain> findAllForMessageMain(MessageMainPageRequest mainPageRequest) {
        MessageSpecification messageSpecification = MessageSpecification.builder().mainPageRequest(mainPageRequest).build();
        if (mainPageRequest.keyWord()!=null){
            return messageRepository.findAll(messageSpecification, PageRequest.of(mainPageRequest.page(),10)).map(MessageMapper::entityToDtoForMain);
        }
        return messageRepository.findAll(PageRequest.of(mainPageRequest.page(),10)).map(MessageMapper::entityToDtoForMain);
    }

    @Override
    public MessageResponseForCard findByIdForCard(Long id) {
        return MessageMapper.entityToDtoForCard(messageRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Message by id "+id+" is not found")));
    }

    @Override
    public void sendMessage(MessageRequestForSend messageRequestForSend) {
        Message message = Message.builder()
                .isActive(messageRequestForSend.debt())
                .description(messageRequestForSend.message())
                .descriptionStyle(messageRequestForSend.messageStyle())
                .title(messageRequestForSend.subject())
                .apartmentList(new ArrayList<>())
                .floorList(new ArrayList<>())
                .houseList(new ArrayList<>())
                .sectionList(new ArrayList<>())
                .sendDate(LocalDateTime.now().withNano(0).atZone(ZoneId.systemDefault()).toInstant())
                .build();
        if (messageRequestForSend.house()!=0){
            message.addHouse(houseService.findById(messageRequestForSend.house()));
            if (messageRequestForSend.section()!=0){
                message.addSection(sectionService.findById(messageRequestForSend.section()));
            }
            else {
                for (Section section: sectionService.findAllSectionByHouse(messageRequestForSend.house())){
                    message.addSection(section);
                }
            }
            if (messageRequestForSend.floor()!=0){
                message.addFloor(floorService.findById(messageRequestForSend.floor()));
            }
            else {
                for (Floor floor: floorService.findAllFloorByHouse(messageRequestForSend.house())){
                    message.addFloor(floor);
                }
            }
            if (messageRequestForSend.apartment()!=0){
                message.addApartment(apartmentService.findById(messageRequestForSend.apartment()));
            }
            else if (messageRequestForSend.apartment()==0&&messageRequestForSend.floor()!=0&&messageRequestForSend.section()==0){
                for (Apartment apartment: apartmentService.findAllApartmentByFloor(messageRequestForSend.house())){
                    message.addApartment(apartment);
                }
            }
            else if (messageRequestForSend.apartment()==0&&messageRequestForSend.floor()==0&&messageRequestForSend.section()!=0){
                for (Apartment apartment: apartmentService.findAllApartmentBySection(messageRequestForSend.house())){
                    message.addApartment(apartment);
                }
            }
            else if (messageRequestForSend.apartment()==0&&messageRequestForSend.floor()==0&&messageRequestForSend.section()==0){
                for (Apartment apartment: apartmentService.findAllApartmentByHouse(messageRequestForSend.house())){
                    message.addApartment(apartment);
                }
            }
        }
        else {
            for (House house: houseService.findAll()){
                for (Apartment apartment: house.getApartmentList()){
                    message.addApartment(apartment);
                }
                for (Section section: house.getSectionList()){
                    message.addSection(section);
                }
                for (Floor floor: house.getFloorList()){
                    message.addFloor(floor);
                }
                message.addHouse(house);
            }
        }
        messageRepository.save(message);

    }

    @Override
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}

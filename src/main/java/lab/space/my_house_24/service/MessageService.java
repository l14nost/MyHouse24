package lab.space.my_house_24.service;


import lab.space.my_house_24.model.message.MessageMainPageRequest;
import lab.space.my_house_24.model.message.MessageRequestForSend;
import lab.space.my_house_24.model.message.MessageResponseForCard;
import lab.space.my_house_24.model.message.MessageResponseForMain;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MessageService {
    Page<MessageResponseForMain> findAllForMessageMain(MessageMainPageRequest mainPageRequest);
    MessageResponseForCard findByIdForCard(Long id);

    void sendMessage(MessageRequestForSend messageRequestForSend);

    void deleteMessage(Long idList);
}

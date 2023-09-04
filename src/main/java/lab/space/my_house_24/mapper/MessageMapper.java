package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Message;
import lab.space.my_house_24.model.message.MessageResponseForCard;
import lab.space.my_house_24.model.message.MessageResponseForMain;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

public class MessageMapper {

    public static MessageResponseForMain entityToDtoForMain(Message message){
        String recipient = "";
        Locale locale = LocaleContextHolder.getLocale();
        if (message.getHouse() == null){
            if (locale.toLanguageTag().equals("uk")) {
                recipient = "Усім";
            }
            else {
                recipient = "To all";
            }
        }
        else {
            recipient = message.getHouse().getName();
        }
        if (message.getSection() != null){
            recipient+= ", "+message.getSection().getName();
        }
        if (message.getFloor() != null){
            recipient+= ", "+message.getFloor().getName();
        }
        if (message.getApartment()!=null){
            recipient+= ", №"+message.getApartment().getNumber();
        }

        return MessageResponseForMain.builder()
                .id(message.getId())
                .recipient(recipient)
                .text("<b>"+message.getTitle()+"</b>"+"-"+message.getDescription())
                .sendDate(message.getSendDate().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }

    public static MessageResponseForCard entityToDtoForCard(Message message){
        return MessageResponseForCard.builder()
                .title(message.getTitle())
                .message(message.getDescriptionStyle())
                .sendDate(message.getSendDate().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .staffFullName(message.getStaff().getLastname()+" "+message.getStaff().getFirstname())
                .id(message.getId())
                .build();
    }
}

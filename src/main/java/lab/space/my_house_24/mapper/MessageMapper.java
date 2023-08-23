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
        if (message.getHouseList().size() != 1){
            if (locale.toLanguageTag().equals("uk")) {
                recipient = "Усім";
            }
            else {
                recipient = "To all";
            }
        }
        else {
            recipient = message.getHouseList().get(0).getName();
        }
        if (message.getSectionList().size() == 1){
            recipient+= ", "+message.getSectionList().get(0).getName();
        }
        if (message.getFloorList().size() == 1){
            recipient+= ", "+message.getFloorList().get(0).getName();
        }
        if (message.getApartmentList().size() == 1){
            recipient+= ", №"+message.getApartmentList().get(0).getNumber();
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

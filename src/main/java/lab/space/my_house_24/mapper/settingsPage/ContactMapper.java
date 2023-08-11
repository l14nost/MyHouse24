package lab.space.my_house_24.mapper.settingsPage;

import lab.space.my_house_24.entity.settingsPage.Contact;
import lab.space.my_house_24.model.settingsPage.contact.ContactResponse;

public class ContactMapper {

    public static ContactResponse entityToResponse(Contact contact){
        return ContactResponse.builder()
                .address(contact.getAddress())
                .email(contact.getEmail())
                .codeMap(contact.getCodeMap())
                .description(contact.getDescription())
                .fullName(contact.getFullName())
                .url(contact.getUrl())
                .location(contact.getLocation())
                .title(contact.getTitle())
                .seoDescription(contact.getSeo().getDescription())
                .seoKeyWords(contact.getSeo().getKeyWords())
                .seoTitle(contact.getSeo().getTitle())
                .number(contact.getNumber())
                .build();
    }
}

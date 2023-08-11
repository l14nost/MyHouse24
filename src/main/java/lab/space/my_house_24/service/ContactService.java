package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.settingsPage.Contact;
import lab.space.my_house_24.model.settingsPage.contact.ContactRequest;
import lab.space.my_house_24.model.settingsPage.contact.ContactResponse;

public interface ContactService {
    Contact findById(Long id);

    ContactResponse findByIdResponse(Long id);

    void update(ContactRequest contactRequest);
}

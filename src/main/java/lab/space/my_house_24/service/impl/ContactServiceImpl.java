package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.settingsPage.Contact;
import lab.space.my_house_24.model.settingsPage.contact.ContactRequest;
import lab.space.my_house_24.model.settingsPage.contact.ContactResponse;
import lab.space.my_house_24.repository.ContactRepository;
import lab.space.my_house_24.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    @Override
    public Contact findById(Long id) {
        return null;
    }

    @Override
    public ContactResponse findByIdDto(Long id) {
        return null;
    }

    @Override
    public void update(ContactRequest contectRequest) {

    }
}

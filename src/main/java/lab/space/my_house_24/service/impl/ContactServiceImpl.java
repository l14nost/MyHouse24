package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.settingsPage.Contact;
import lab.space.my_house_24.mapper.settingsPage.ContactMapper;
import lab.space.my_house_24.model.settingsPage.contact.ContactRequest;
import lab.space.my_house_24.model.settingsPage.contact.ContactResponse;
import lab.space.my_house_24.repository.ContactRepository;
import lab.space.my_house_24.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    @Override
    public Contact findById(Long id) {
        log.info("Try to find contact by id: "+id);
        return contactRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Contact by id "+id+" is not found"));
    }

    @Override
    public ContactResponse findByIdResponse(Long id) {
        log.info("Try to find contact dto by id: "+id);
        return ContactMapper.entityToResponse(findById(id));
    }

    @Override
    public void update(ContactRequest contactRequest) {
        log.info("Try to update contact page");
        Contact contact = findById(1L);
        contact.setAddress(contactRequest.address());
        contact.setEmail(contactRequest.email());
        contact.setLocation(contactRequest.location());
        contact.setUrl(contactRequest.url());
        contact.setCodeMap(contactRequest.codeMap());
        contact.setFullName(contactRequest.fullName());
        contact.setNumber(contactRequest.number());
        contact.setTitle(contactRequest.title());
        contact.setDescription(contactRequest.description());
        contact.getSeo().setKeyWords(contactRequest.seoKeyWords());
        contact.getSeo().setDescription(contactRequest.seoDescription());
        contact.getSeo().setTitle(contactRequest.seoTitle());
        contactRepository.save(contact);
        log.info("Contact page was update");
    }

    @Override
    public void save(Contact build) {
        log.info("Try to save new contact page");
        contactRepository.save(build);
        log.info("Contact page was save");
    }
}

package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.settingsPage.Contact;
import lab.space.my_house_24.entity.settingsPage.Seo;
import lab.space.my_house_24.model.settingsPage.contact.ContactRequest;
import lab.space.my_house_24.model.settingsPage.contact.ContactResponse;
import lab.space.my_house_24.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    @Test
    void findById() {
        Contact contact = Contact.builder()
                .id(1L)
                .address("address")
                .email("mail@gmail.com")
                .number("123123123")
                .url("url")
                .description("descr")
                .location("location")
                .fullName("fullName")
                .seo(Seo.builder().keyWords("key").title("title").description("descr").build())
                .build();
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        Contact contact1 = contactService.findById(1L);
        assertEquals(contact, contact1);
    }

    @Test
    void findByIdResponse() {
        Contact contact = Contact.builder()
                .id(1L)
                .address("address")
                .email("mail@gmail.com")
                .number("123123123")
                .url("url")
                .description("descr")
                .location("location")
                .fullName("fullName")
                .seo(Seo.builder().keyWords("key").title("title").description("descr").build())
                .build();
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        ContactResponse contactResponse = contactService.findByIdResponse(1L);
        ContactResponse contactResponse1 = ContactResponse.builder()
                .address("address")
                .email("mail@gmail.com")
                .number("123123123")
                .url("url")
                .description("descr")
                .location("location")
                .fullName("fullName")
                .seoTitle("title")
                .seoKeyWords("key")
                .seoDescription("descr")
                .build();
        assertEquals(contactResponse1, contactResponse);
    }

    @Test
    void update() {
        ContactRequest contactRequest = ContactRequest.builder()
                .address("address")
                .email("mail@gmail.com")
                .number("123123123")
                .url("url")
                .description("descr")
                .location("location")
                .fullName("fullName")
                .seoTitle("title")
                .seoKeyWords("key")
                .seoDescription("descr")
                .build();

        Contact contact = Contact.builder()
                .id(1L)
                .address("address1")
                .email("mail1@gmail.com")
                .number("1231231231")
                .url("url1")
                .description("descr1")
                .location("location1")
                .fullName("fullName1")
                .seo(Seo.builder().keyWords("key1").title("title1").description("descr1").build())
                .build();
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        contactService.update(contactRequest);
        verify(contactRepository).save(Contact.builder()
                .id(1L)
                .address("address")
                .email("mail@gmail.com")
                .number("123123123")
                .url("url")
                .description("descr")
                .location("location")
                .fullName("fullName")
                .seo(Seo.builder().keyWords("key").title("title").description("descr").build())
                .build());

    }

    @Test
    void save() {
        contactService.save(Contact.builder().build());
        verify(contactRepository).save(Contact.builder().build());
    }
}
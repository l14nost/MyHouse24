package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.settingsPage.About;
import lab.space.my_house_24.entity.settingsPage.Document;
import lab.space.my_house_24.entity.settingsPage.Photo;
import lab.space.my_house_24.entity.settingsPage.Seo;
import lab.space.my_house_24.model.settingsPage.about.AboutRequest;
import lab.space.my_house_24.model.settingsPage.about.AboutResponse;
import lab.space.my_house_24.model.settingsPage.document.DocumentResponse;
import lab.space.my_house_24.model.settingsPage.photo.PhotoResponse;
import lab.space.my_house_24.repository.AboutRepository;
import lab.space.my_house_24.service.DocumentService;
import lab.space.my_house_24.service.PhotoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AboutServiceImplTest {
    @Mock
    private AboutRepository aboutRepository;
    @Mock
    private PhotoService photoService;
    @Mock
    private DocumentService documentService;
    @InjectMocks
    private AboutServiceImpl aboutService;


    @Test
    void findByIdResponse() {
        when(aboutRepository.findById(1L)).thenReturn(Optional.of(
                About.builder()
                        .id(1L)
                        .description("Descr")
                        .descriptionAdd("descrAdd")
                        .documentList(List.of(
                                Document.builder().document("doc").name("doc").build(),
                                Document.builder().document("doc").name("doc").build(),
                                Document.builder().document("doc").name("doc").build(),
                                Document.builder().document("doc").name("doc").build()
                        ))
                        .imageDirector("img")
                        .photoList(List.of(
                                Photo.builder().type(true).image("img").build(),
                                Photo.builder().type(true).image("img").build(),
                                Photo.builder().type(false).image("img").build(),
                                Photo.builder().type(false).image("img").build()
                        ))
                        .title("title")
                        .titleAdd("titleAdd")
                        .seo(Seo.builder().description("descr").title("title").keyWords("key").build())
                        .build()
        ));
        AboutResponse aboutResponse = aboutService.findByIdResponse(1L);

        AboutResponse aboutResponse1 = AboutResponse.builder()
                .description("Descr")
                .descriptionAdd("descrAdd")
                .documentNames(List.of(
                        DocumentResponse.builder().document("doc").name("doc").build(),
                        DocumentResponse.builder().document("doc").name("doc").build(),
                        DocumentResponse.builder().document("doc").name("doc").build(),
                        DocumentResponse.builder().document("doc").name("doc").build()
                ))
                .imageDirector("img")
                .photoList(List.of(
                        PhotoResponse.builder().image("img").build(),
                        PhotoResponse.builder().image("img").build()
                ))
                .addPhotoList(List.of(
                        PhotoResponse.builder().image("img").build(),
                        PhotoResponse.builder().image("img").build()
                ))
                .title("title")
                .titleAdd("titleAdd")
                .seoDescription("descr")
                .seoKeyWord("key")
                .seoTitle("title")
                .build();
        assertEquals(aboutResponse1, aboutResponse);
    }

    @Test
    void findById() {
        when(aboutRepository.findById(1L)).thenReturn(Optional.of(
                About.builder()
                        .id(1L)
                        .description("Descr")
                        .descriptionAdd("descrAdd")
                        .documentList(List.of(
                                Document.builder().document("doc").name("doc").build(),
                                Document.builder().document("doc").name("doc").build(),
                                Document.builder().document("doc").name("doc").build(),
                                Document.builder().document("doc").name("doc").build()
                        ))
                        .imageDirector("img")
                        .photoList(List.of(
                                Photo.builder().type(true).image("img").build(),
                                Photo.builder().type(true).image("img").build(),
                                Photo.builder().type(false).image("img").build(),
                                Photo.builder().type(false).image("img").build()
                        ))
                        .title("title")
                        .titleAdd("titleAdd")
                        .seo(Seo.builder().description("descr").title("title").keyWords("key").build())
                        .build()
        ));
        About about = About.builder()
                .id(1L)
                .description("Descr")
                .descriptionAdd("descrAdd")
                .documentList(List.of(
                        Document.builder().document("doc").name("doc").build(),
                        Document.builder().document("doc").name("doc").build(),
                        Document.builder().document("doc").name("doc").build(),
                        Document.builder().document("doc").name("doc").build()
                ))
                .imageDirector("img")
                .photoList(List.of(
                        Photo.builder().type(true).image("img").build(),
                        Photo.builder().type(true).image("img").build(),
                        Photo.builder().type(false).image("img").build(),
                        Photo.builder().type(false).image("img").build()
                ))
                .title("title")
                .titleAdd("titleAdd")
                .seo(Seo.builder().description("descr").title("title").keyWords("key").build())
                .build();
        About about1 = aboutService.findById(1L);
        assertEquals(about, about1);
    }

    @Test
    void update() {
        AboutRequest aboutRequest = AboutRequest.builder()
                .documentList(List.of(
                        new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes())
                ))
                .addDescription("addDescr")
                .title("title")
                .description("descr")
                .seoTitle("seoTitle")
                .addTitle("addTitle")
                .addGalleryList(List.of(
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes())
                ))
                .galleryList(
                        List.of(
                                new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                                new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                                new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                                new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes())
                        )
                )
                .seoDescription("seoDescription")
                .seoKeyWords("seoKey")
                .directImage(
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes())
                )
                .deleteAddGalleryList(List.of(1L,2L))
                .deleteGalleryList(List.of(4L,5L))
                .deleteDocumentList(List.of(1L,3L))
                .build();
        About about = About.builder()
                .id(1L)
                .description("Descr")
                .descriptionAdd("descrAdd")
                .documentList(new ArrayList<>(List.of(
                        Document.builder().id(1L).document("doc").name("doc").build(),
                        Document.builder().id(2L).document("doc").name("doc").build(),
                        Document.builder().id(3L).document("doc").name("doc").build()
                )))
                .imageDirector("img")
                .photoList(List.of(
                        Photo.builder().id(1L).type(true).image("img").build(),
                        Photo.builder().id(2L).type(true).image("img").build(),
                        Photo.builder().id(3L).type(true).image("img").build(),
                        Photo.builder().id(4L).type(false).image("img").build(),
                        Photo.builder().id(5L).type(false).image("img").build(),
                        Photo.builder().id(6L).type(false).image("img").build()
                ))
                .title("Title")
                .titleAdd("TitleAdd")
                .seo(Seo.builder().description("descr").title("title").keyWords("key").build())
                .build();
        when(aboutRepository.findById(1L)).thenReturn(Optional.of(about));
        aboutService.update(aboutRequest);
        verify(photoService,times(4)).delete(anyLong());
        verify(documentService,times(2)).delete(anyLong());
        verify(aboutRepository).save(any(About.class));

    }

    @Test
    void save() {
        aboutService.save(About.builder().build());
        verify(aboutRepository).save(About.builder().build());
    }
}
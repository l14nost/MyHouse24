package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.settingsPage.Document;
import lab.space.my_house_24.entity.settingsPage.Photo;
import lab.space.my_house_24.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {
    @Mock
    private DocumentRepository documentRepository;
    @InjectMocks
    private DocumentServiceImpl documentService;

    @Test
    void delete() {
        when(documentRepository.findById(1L)).thenReturn(Optional.of(Document.builder().document("img.doc").build()));
        documentService.delete(1L);
        verify(documentRepository,times(1)).deleteById(1L);
    }
}
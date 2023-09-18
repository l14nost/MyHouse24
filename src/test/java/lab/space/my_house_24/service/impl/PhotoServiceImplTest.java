package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.settingsPage.Photo;
import lab.space.my_house_24.repository.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhotoServiceImplTest {
    @Mock
    private PhotoRepository photoRepository;
    @InjectMocks
    private PhotoServiceImpl photoService;

    @Test
    void delete() {
        when(photoRepository.findById(1L)).thenReturn(Optional.of(Photo.builder().image("img.jpg").build()));
        photoService.delete(1L);
        verify(photoRepository,times(1)).deleteById(1L);
    }
}
package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.settingsPage.Photo;
import lab.space.my_house_24.repository.PhotoRepository;
import lab.space.my_house_24.service.PhotoService;
import lab.space.my_house_24.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    @Override
    public void delete(Long id) {
        Photo photo = photoRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Photo by id "+id+" is not found"));
        if (photo.getImage()!=null) {
            FileHandler.deleteFile(photo.getImage());
        }
        photoRepository.deleteById(id);
    }
}

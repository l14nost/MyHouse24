package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.settingsPage.About;
import lab.space.my_house_24.entity.settingsPage.Document;
import lab.space.my_house_24.entity.settingsPage.Photo;
import lab.space.my_house_24.mapper.settingsPage.AboutMapper;
import lab.space.my_house_24.model.settingsPage.about.AboutRequest;
import lab.space.my_house_24.model.settingsPage.about.AboutResponse;
import lab.space.my_house_24.repository.AboutRepository;
import lab.space.my_house_24.repository.PhotoRepository;
import lab.space.my_house_24.service.AboutService;
import lab.space.my_house_24.service.DocumentService;
import lab.space.my_house_24.service.PhotoService;
import lab.space.my_house_24.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class AboutServiceImpl implements AboutService {
    private final AboutRepository aboutRepository;
    private final PhotoService photoService;
    private final DocumentService documentService;
    @Override
    public AboutResponse findByIdResponse(Long id) {
        log.info("Try to find about dto by id: "+id);
        return AboutMapper.entityToDto(findById(id));
    }

    @Override
    public About findById(Long id) {
        log.info("Try to find about by id: "+id);
        return aboutRepository.findById(id).orElseThrow(()->new EntityNotFoundException("About by id "+id+" is not found"));
    }

    @Override
    public void update(AboutRequest aboutRequest) {
        log.info("Try to update about page");
        About about = findById(1L);
        if (!aboutRequest.deleteAddGalleryList().isEmpty()){
            for (int i = 0 ; i< aboutRequest.deleteAddGalleryList().size();i++){
                photoService.delete(aboutRequest.deleteAddGalleryList().get(i));
            }
        }
        if (!aboutRequest.deleteGalleryList().isEmpty()){
            for (int i = 0 ; i< aboutRequest.deleteGalleryList().size();i++){
                photoService.delete(aboutRequest.deleteGalleryList().get(i));
            }
        }
        if (!aboutRequest.deleteDocumentList().isEmpty()){
            for (int i = 0 ; i< aboutRequest.deleteDocumentList().size();i++){
                documentService.delete(aboutRequest.deleteDocumentList().get(i));
            }
        }
        List<Photo> gallery = new ArrayList<>();
        List<Photo> addGallery = new ArrayList<>();
        for (Photo photo: about.getPhotoList()){
            if (photo.getType()){
                gallery.add(photo);
            }
            else addGallery.add(photo);
        }
        for (int i = 0; i<aboutRequest.galleryList().size();i++){
            if (!aboutRequest.galleryList().get(i).isEmpty()) {
                if (i < gallery.size()) {
                    gallery.get(i).setImage(FileHandler.saveFile(aboutRequest.galleryList().get(i)));
                } else
                    gallery.add(Photo.builder().about(about).image(FileHandler.saveFile(aboutRequest.galleryList().get(i))).type(true).build());
            }
        }
        for (int i =0; i<aboutRequest.addGalleryList().size();i++) {
            if (!aboutRequest.addGalleryList().get(i).isEmpty()){
                if (i < addGallery.size()) {
                    addGallery.get(i).setImage(FileHandler.saveFile(aboutRequest.addGalleryList().get(i)));
                } else
                    addGallery.add(Photo.builder().about(about).image(FileHandler.saveFile(aboutRequest.addGalleryList().get(i))).type(false).build());
            }
        }
        List<Photo> merged = new ArrayList<>(gallery);
        merged.addAll(addGallery);
        about.setPhotoList(merged);

        for (int i = 0; i< aboutRequest.documentList().size();i++){
            if (!aboutRequest.documentList().get(i).isEmpty()){
                if (i< about.getDocumentList().size()){
                    about.getDocumentList().get(i).setDocument(FileHandler.saveFile(aboutRequest.documentList().get(i)));
                    about.getDocumentList().get(i).setName(aboutRequest.documentList().get(i).getOriginalFilename());
                }
                else {
                    about.getDocumentList().add(Document.builder().about(about).name(aboutRequest.documentList().get(i).getOriginalFilename()).document(FileHandler.saveFile(aboutRequest.documentList().get(i))).build());
                }
            }
        }

        about.getSeo().setTitle(aboutRequest.seoTitle());
        about.getSeo().setDescription(aboutRequest.seoDescription());
        about.getSeo().setKeyWords(aboutRequest.seoKeyWords());

        about.setTitle(aboutRequest.title());
        about.setDescription(aboutRequest.description());
        about.setTitleAdd(aboutRequest.addTitle());
        about.setDescriptionAdd(aboutRequest.addDescription());

        if (!aboutRequest.directImage().isEmpty()){
            about.setImageDirector(FileHandler.saveFile(aboutRequest.directImage()));
        }
        aboutRepository.save(about);
        log.info("Success update");
    }
    @Override
    public void save(About build) {
        log.info("Try to save new about page");
        aboutRepository.save(build);
        log.info("About page was save");
    }
}

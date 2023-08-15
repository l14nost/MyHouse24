package lab.space.my_house_24.mapper.settingsPage;

import lab.space.my_house_24.entity.settingsPage.About;
import lab.space.my_house_24.entity.settingsPage.Document;
import lab.space.my_house_24.entity.settingsPage.Photo;
import lab.space.my_house_24.model.settingsPage.about.AboutResponse;
import lab.space.my_house_24.model.settingsPage.document.DocumentResponse;
import lab.space.my_house_24.model.settingsPage.photo.PhotoResponse;

import java.util.ArrayList;
import java.util.List;

public class AboutMapper {
    public static AboutResponse entityToDto(About about){
        List<PhotoResponse> photoList = new ArrayList<>();
        List<PhotoResponse> addPhotoList = new ArrayList<>();
        List<DocumentResponse> documentNames = new ArrayList<>();
        for (Photo photo: about.getPhotoList()){
            if (photo.getType()){
                photoList.add(PhotoResponse.builder().image(photo.getImage()).id(photo.getId()).build());
            }
            else addPhotoList.add(PhotoResponse.builder().image(photo.getImage()).id(photo.getId()).build());
        }
        for (Document document: about.getDocumentList()){
            documentNames.add(DocumentResponse.builder().id(document.getId()).name(document.getName()).document(document.getDocument()).build());
        }

        return AboutResponse.builder()
                .title(about.getTitle())
                .description(about.getDescription())
                .descriptionAdd(about.getDescriptionAdd())
                .titleAdd(about.getTitleAdd())
                .imageDirector(about.getImageDirector())
                .seoTitle(about.getSeo().getTitle())
                .seoDescription(about.getSeo().getDescription())
                .seoKeyWord(about.getSeo().getKeyWords())
                .photoList(photoList)
                .addPhotoList(addPhotoList)
                .documentNames(documentNames)
                .build();
    }
}

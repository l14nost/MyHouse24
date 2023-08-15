package lab.space.my_house_24.model.settingsPage.about;

import lab.space.my_house_24.model.settingsPage.document.DocumentResponse;
import lab.space.my_house_24.model.settingsPage.photo.PhotoResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record AboutResponse(
        String title,
        String description,
        String imageDirector,
        String titleAdd,
        String descriptionAdd,
        String seoTitle,
        String seoDescription,
        String seoKeyWord,
        List<PhotoResponse> photoList,
        List<PhotoResponse> addPhotoList,
        List<DocumentResponse> documentNames
) {
}

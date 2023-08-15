package lab.space.my_house_24.model.settingsPage.about;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24.validator.annotation.FileExtension;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record AboutRequest(
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
        String title,
        @Size(max = 1000, message = "{size.less.message}"+" 1000")
        String description,
        @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
        MultipartFile directImage,
        @Valid
        List< @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
                MultipartFile> galleryList,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
        String addTitle,
        @Size(max = 1000, message = "{size.less.message}"+" 1000")
        String addDescription,
        @Valid
        List<@FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
                MultipartFile> addGalleryList,
        @Valid
        List<@FileExtension(value = {"doc", "docx", "pdf"}, message = "{file.message}"+"(.doc,.docx,.pdf)")
                MultipartFile> documentList,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
        String seoTitle,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 1000, message = "{size.less.message}"+" 1000")
        String seoDescription,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 200, message = "{size.less.message}"+" 200")
        String seoKeyWords,
        List<Long> deleteGalleryList,
        List<Long> deleteAddGalleryList,
        List<Long> deleteDocumentList
) {
}

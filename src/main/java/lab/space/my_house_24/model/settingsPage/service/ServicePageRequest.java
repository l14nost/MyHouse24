package lab.space.my_house_24.model.settingsPage.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24.validator.annotation.FileExtension;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record ServicePageRequest(
        @Valid
        List<
                @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
                MultipartFile> imageList,
        @Valid
        List<@NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
                String> blockTitleList,
        @Valid
        List<@Size(max = 1000, message = "{size.less.message}" + " 1000")
                String> blockDescriptionList,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
        String seoTitle,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 1000, message = "{size.less.message}"+" 1000")
        String seoDescription,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 200, message = "{size.less.message}"+" 200")
        String seoKeyWords,

        List<Long> deleteBlockList
        ) {
}

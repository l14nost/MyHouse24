package lab.space.my_house_24.model.settingsPage.banner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24.validator.annotation.FileExtension;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record BannerRequest(
        @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
        MultipartFile block,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
        String blockTitle,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 1000, message = "{size.less.message}"+" 1000")
        String blockDescription
) {
}

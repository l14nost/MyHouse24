package lab.space.my_house_24.model.settingsPage.mainPage;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24.model.settingsPage.banner.BannerRequest;
import lab.space.my_house_24.validator.annotation.FileExtension;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record MainPageRequest(
        @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
        MultipartFile slide1,
        @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
        MultipartFile slide2,
        @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
        MultipartFile slide3,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
        String title,
        @Size(max = 1000, message = "{size.less.message}"+" 1000")
        String description,
        Boolean showLink,
        @Valid
        List< @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)") MultipartFile> block,

        @Valid
        List<
                @NotBlank(message = "{not.blank.message}")
        @Size(max = 1000, message = "{size.less.message}" + " 1000")String> blockTitle,

        @Valid
        List<
        @Size(max = 1000, message = "{size.less.message}" + " 1000")
                String> blockDescription,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
        String seoTitle,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 1000, message = "{size.less.message}"+" 1000")
        String seoDescription,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 200, message = "{size.less.message}"+" 200")
        String seoKeyWords

        ) {
}

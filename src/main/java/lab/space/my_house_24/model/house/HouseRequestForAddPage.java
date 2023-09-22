package lab.space.my_house_24.model.house;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24.validator.annotation.FileExtension;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record HouseRequestForAddPage(
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
        String name,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 150, message = "{size.less.message}"+" 150")
        String address,
        @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
        MultipartFile image1,
        @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
        MultipartFile image2,
        @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
        MultipartFile image3,
        @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
        MultipartFile image4,
        @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
        MultipartFile image5,
        @Valid
        @NotEmpty(message = "{not.blank.message}")
        List<@NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
                String> sectionNameList,
        @Valid
        @NotEmpty(message = "{not.blank.message}")
        List<@NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
                String> floorNameList,
        @Valid
        List<@NotNull(message = "{not.blank.message}")
                Long> userList
) {
}

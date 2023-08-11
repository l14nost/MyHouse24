package lab.space.my_house_24.model.settingsPage.mainPage;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record MainPageRequest(
        MultipartFile slide1,
        MultipartFile slide2,
        MultipartFile slide3,
        String title,
        String description,
        Boolean showLink,

        List<MultipartFile> block,
        List<String> blockTitle,
        List<String> blockDescription,

        String seoTitle,
        String seoDescription,
        String seoKeyWords

        ) {
}

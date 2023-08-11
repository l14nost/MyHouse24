package lab.space.my_house_24.model.settingsPage.contact;

import lombok.Builder;

@Builder
public record ContactResponse(
    String title,
    String description,
    String codeMap,
    String email,
    String fullName,
    String location,
    String number,
    String url,

    String address,

    String seoTitle,

    String seoDescription,

    String seoKeyWords

) {
}

package lab.space.my_house_24.service;

import lab.space.my_house_24.model.apartment.ApartmentRequestForMainPage;
import lab.space.my_house_24.model.apartment.ApartmentResponse;
import lab.space.my_house_24.model.apartment.ApartmentResponseForCard;
import lab.space.my_house_24.model.floor.FloorResponseForTable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApartmentService {
    Page<ApartmentResponse> findAllForMainPage(ApartmentRequestForMainPage apartmentRequestForMainPage);
    void deleteApartment(Long id);

    ApartmentResponseForCard findByIdForCard(Long id);


}

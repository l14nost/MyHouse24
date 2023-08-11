package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.model.apartment.*;
import lab.space.my_house_24.model.floor.FloorResponseForTable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApartmentService {
    Page<ApartmentResponse> findAllForMainPage(ApartmentRequestForMainPage apartmentRequestForMainPage);
    void deleteApartment(Long id);

    ApartmentResponseForCard findByIdForCard(Long id);


    void saveApartment(ApartmentAddRequest apartmentAddRequest);

    ApartmentResponseForEdit findByIdApartment(Long id);

    void updateApartment(Long id,ApartmentAddRequest apartmentAddRequest);
    Apartment findById(Long id);
}

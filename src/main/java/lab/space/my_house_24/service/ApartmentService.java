package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.Floor;
import lab.space.my_house_24.model.apartment.*;
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

    List<ApartmentResponseForMastersApplication> getAllApartmentResponseByUserId(ApartmentMastersApplicationRequest request);


    List<ApartmentResponseForTable> apartmentForSelect(Long idHouse, Long idSection, Long idFloor);

    List<Apartment> findAllApartmentByHouse(Long house);
    List<Apartment> findAllApartmentByFloor(Long floor);
    List<Apartment> findAllApartmentBySection(Long section);
}

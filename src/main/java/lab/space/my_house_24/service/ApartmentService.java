package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.model.apartment.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApartmentService {

    Page<ApartmentResponse> findAllForMainPage(ApartmentRequestForMainPage apartmentRequestForMainPage);

    void deleteApartment(Long id);

    ApartmentResponseForCard findByIdForCard(Long id);


    void saveApartment(ApartmentAddRequest apartmentAddRequest);

    ApartmentResponseForEdit findByIdApartment(Long id);

    void updateApartment(Long id, ApartmentAddRequest apartmentAddRequest);

    Apartment findById(Long id);

    Page<ApartmentResponseForMastersApplication> getAllApartmentResponseByUserId(ApartmentMastersApplicationRequest request);

    List<ApartmentResponseForBankBook> getAllApartmentResponseByHouseId(Long id);

    List<ApartmentResponseForBankBook> getAllApartmentResponseByHouseIdAndSectionId(Long houseId, Long sectionId);

    List<ApartmentResponseForBill> getAllApartmentResponseByHouseIdForBill(Long id);

    List<ApartmentResponseForBill> getAllApartmentResponseByHouseIdAndSectionIdForBill(Long houseId, Long sectionId);

    List<ApartmentResponseForBill> getAllApartmentResponseForBill();

    Page<ApartmentResponseForBill> getAllApartmentResponse(Integer pageIndex, String search, Long houseId, Long sectionId);

    List<ApartmentResponseForTable> apartmentForSelect(Long idHouse, Long idSection, Long idFloor, Boolean duty);

    List<Apartment> findAllApartmentByHouse(Long house);

    List<ApartmentResponseForTable> apartmentListForSelect();

    Long count();

    List<Apartment> apartmentListForMessage(Long house, Long section, Long floor, Long apartment, Boolean debt);

    Page<ApartmentResponseForTable> apartmentForSelectPagination(Long idHouse, Long idSection, Long idFloor, Boolean duty, Integer page);
}

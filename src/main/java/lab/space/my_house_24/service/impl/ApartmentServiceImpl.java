package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.mapper.ApartmentMapper;
import lab.space.my_house_24.model.apartment.ApartmentRequestForMainPage;
import lab.space.my_house_24.model.apartment.ApartmentResponse;
import lab.space.my_house_24.model.apartment.ApartmentResponseForCard;
import lab.space.my_house_24.repository.ApartmentRepository;
import lab.space.my_house_24.service.ApartmentService;
import lab.space.my_house_24.specification.ApartmentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final HouseServiceImpl houseService;

    @Override
    public Page<ApartmentResponse> findAllForMainPage(ApartmentRequestForMainPage apartmentRequestForMainPage) {
        if (apartmentRequestForMainPage.house()!=null && !apartmentRequestForMainPage.house().isEmpty()) {
            ApartmentRequestForMainPage apartmentRequestForMainPage1 = ApartmentRequestForMainPage.builder()
                    .page(apartmentRequestForMainPage.page())
                    .balance(apartmentRequestForMainPage.balance())
                    .floor(apartmentRequestForMainPage.floor())
                    .house(houseService.findById(Long.parseLong(apartmentRequestForMainPage.house())).getName())
                    .owner(apartmentRequestForMainPage.owner())
                    .section(apartmentRequestForMainPage.section())
                    .number(apartmentRequestForMainPage.number())
                    .build();
            return apartmentRepository.findAll(ApartmentSpecification.builder().apartmentRequestForMainPage(apartmentRequestForMainPage1).build(), PageRequest.of(apartmentRequestForMainPage.page(), 10)).map(ApartmentMapper::entityToDtoForMainPage);
        }

        else return apartmentRepository.findAll(ApartmentSpecification.builder().apartmentRequestForMainPage(apartmentRequestForMainPage).build(), PageRequest.of(apartmentRequestForMainPage.page(), 10)).map(ApartmentMapper::entityToDtoForMainPage);
    }
    @Override
    public void deleteApartment(Long id) {
        apartmentRepository.deleteById(id);
    }

    @Override
    public ApartmentResponseForCard findByIdForCard(Long id) {
        return ApartmentMapper.entityToDtoForCard(apartmentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Apartment by id "+id+" is not found")));
    }
}

package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.enums.BankBookStatus;
import lab.space.my_house_24.mapper.ApartmentMapper;
import lab.space.my_house_24.model.apartment.*;
import lab.space.my_house_24.repository.ApartmentRepository;
import lab.space.my_house_24.service.ApartmentService;
import lab.space.my_house_24.service.BankBookService;
import lab.space.my_house_24.service.HouseService;
import lab.space.my_house_24.specification.ApartmentSpecification;
import lab.space.my_house_24.specification.ApartmentSpecificationForMailing;
import lab.space.my_house_24.specification.ApartmentSpecificationForSelect;
import lab.space.my_house_24.specification.ApartmentSpecificationForMasterApplication;
import lab.space.my_house_24.specification.ApartmentSpecificationForSelect;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final HouseService houseService;
    private final BankBookService bankBookService;
    private final ApartmentSpecificationForMasterApplication apartmentSpecificationForMasterApplication;

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

    @Override
    public void saveApartment(ApartmentAddRequest apartmentAddRequest) {

        Apartment apartment = Apartment.builder()
                .number(apartmentAddRequest.number())
                .area(apartmentAddRequest.area())
                .rate(Rate.builder().id(apartmentAddRequest.rate()).build())
                .section(Section.builder().id(apartmentAddRequest.section()).build())
                .floor(Floor.builder().id(apartmentAddRequest.floor()).build())
                .house(House.builder().id(apartmentAddRequest.house()).build())
                .user(User.builder().id(apartmentAddRequest.owner()).build())
                .build();
        if (apartmentAddRequest.bankBook()!=null && !apartmentAddRequest.bankBook().isEmpty()) {
            Optional<BankBook> bankBookOptional = bankBookService.findByNumber(apartmentAddRequest.bankBook());
            if (bankBookOptional.isEmpty()) {
                apartment.setBankBook(BankBook.builder().apartment(apartment).number(apartmentAddRequest.bankBook()).bankBookStatus(BankBookStatus.INACTIVE).build());
            } else {
                BankBook bankBook = bankBookOptional.get();
                bankBook.setApartment(apartment);
                apartment.setBankBook(bankBook);
            }
        }
        apartmentRepository.save(apartment);
    }

    @Override
    public ApartmentResponseForEdit findByIdApartment(Long id) {
        return ApartmentMapper.entityToDtoForEdit(apartmentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Apartment by id "+id+" is not found")));
    }

    @Override
    public void updateApartment(Long id,ApartmentAddRequest apartmentAddRequest) {
        Apartment apartment = findById(id);
        Optional<BankBook> bankBookOptional = bankBookService.findByNumber(apartmentAddRequest.bankBook());
        apartment.setArea(apartmentAddRequest.area());
        apartment.setHouse(House.builder().id(apartmentAddRequest.house()).build());
        apartment.setNumber(apartmentAddRequest.number());
        apartment.setUser(User.builder().id(apartmentAddRequest.owner()).build());
        apartment.setRate(Rate.builder().id(apartmentAddRequest.rate()).build());
        apartment.setFloor(Floor.builder().id(apartmentAddRequest.floor()).build());
        apartment.setSection(Section.builder().id(apartmentAddRequest.section()).build());
        if (bankBookOptional.isEmpty()){
            apartment.setBankBook(BankBook.builder().apartment(apartment).number(apartmentAddRequest.bankBook()).bankBookStatus(BankBookStatus.INACTIVE).build());
        }
        else {
            BankBook bankBook = bankBookOptional.get();
            if (!bankBook.getId().equals(apartment.getBankBook().getId())){
                apartment.getBankBook().setApartment(null);
            }
            bankBook.setApartment(apartment);
            apartment.setBankBook(bankBook);

        }
        apartmentRepository.save(apartment);
    }

    @Override
    public Apartment findById(Long id) {
        return apartmentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Apartment by id "+id+" is not found"));
    }

    @Override
    public List<ApartmentResponseForMastersApplication> getAllApartmentResponseByUserId(ApartmentMastersApplicationRequest request) {
        log.info("Get all Apartment and convert in Response for MastersApplication");
        return apartmentRepository.findAll(apartmentSpecificationForMasterApplication.getApartmentByUserId(request))
                .stream()
                .map(ApartmentMapper::entityToResponseForMastersApplication)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartmentResponseForBankBook> getAllApartmentResponseByHouseId(Long id) {
        log.info("Get all Apartment by HouseId and convert in Response for BankBook");
        return findAllApartmentByHouse(id)
                .stream()
                .map(ApartmentMapper::entityToResponseForBankBook)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartmentResponseForBankBook> getAllApartmentResponseByHouseIdAndSectionId(Long houseId, Long sectionId) {
        log.info("Get all Apartment by HouseId and SectionId, and convert in Response for BankBook");
        return apartmentRepository.findAllByHouse_IdAndSection_IdOrderById(houseId,sectionId)
                .stream()
                .map(ApartmentMapper::entityToResponseForBankBook)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartmentResponseForBill> getAllApartmentResponseByHouseIdForBill(Long id) {
        log.info("Get all Apartment by HouseId and convert in Response for Bill");
        return findAllApartmentByHouse(id)
                .stream()
                .map(ApartmentMapper::entityToResponseForBill)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartmentResponseForBill> getAllApartmentResponseByHouseIdAndSectionIdForBill(Long houseId, Long sectionId) {
        log.info("Get all Apartment by HouseId and SectionId, and convert in Response for Bill");
        return apartmentRepository.findAllByHouse_IdAndSection_IdOrderById(houseId,sectionId)
                .stream()
                .map(ApartmentMapper::entityToResponseForBill)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartmentResponseForBill> getAllApartmentResponseForBill() {
        log.info("Get all Apartment and convert in Response for BankBook");
        return apartmentRepository.findAll(Sort.by(Sort.Direction.DESC,"id"))
                .stream()
                .map(ApartmentMapper::entityToResponseForBill)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartmentResponseForBankBook> getAllApartmentResponse() {
        log.info("Get all Apartment and convert in Response for BankBook");
        return apartmentRepository.findAll(Sort.by(Sort.Direction.DESC,"id"))
                .stream()
                .map(ApartmentMapper::entityToResponseForBankBook)
                .collect(Collectors.toList());
    }


    @Override
    public List<ApartmentResponseForTable> apartmentForSelect(Long idHouse, Long idSection, Long idFloor) {
        ApartmentSpecificationForSelect apartmentSpecificationForSelect = ApartmentSpecificationForSelect.builder()
                .idFloor(idFloor)
                .idHouse(idHouse)
                .idSection(idSection)
                .build();
        return apartmentRepository.findAll(apartmentSpecificationForSelect).stream().map(ApartmentMapper::entityToDtoForTable).toList();

    }

    @Override
    public List<Apartment> findAllApartmentByHouse(Long house) {
        return apartmentRepository.findAllByHouse_Id(house);
    }

    @Override
    public List<Apartment> findAllApartmentByFloor(Long floor) {
        return apartmentRepository.findAllByHouse_Id(floor);
    }

    @Override
    public List<Apartment> findAllApartmentBySection(Long section) {
        return apartmentRepository.findAllByHouse_Id(section);
    }

    @Override
    public List<ApartmentResponseForTable> apartmentListForSelect() {
        return apartmentRepository.findAll().stream().map(ApartmentMapper::entityToDtoForTable).toList();
    }

    @Override
    public Long count() {
        return apartmentRepository.count();
    }

    @Override
    public List<Apartment> apartmentListForMessage(Long house, Long section, Long floor, Long apartment) {
        ApartmentSpecificationForMailing apartmentSpecification = ApartmentSpecificationForMailing.builder().idApartment(apartment).idFloor(floor).idHouse(house).idSection(section).build();
        return apartmentRepository.findAll(apartmentSpecification);
    }


}

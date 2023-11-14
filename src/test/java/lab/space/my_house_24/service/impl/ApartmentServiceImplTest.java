package lab.space.my_house_24.service.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.model.apartment.*;
import lab.space.my_house_24.model.bankBook.BankBookResponseForApartmentCard;
import lab.space.my_house_24.model.bankBook.BankBookResponseForTable;
import lab.space.my_house_24.model.floor.FloorResponseForTable;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.rate.RateResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.user.UserResponseForTable;
import lab.space.my_house_24.repository.ApartmentRepository;
import lab.space.my_house_24.service.BankBookService;
import lab.space.my_house_24.service.HouseService;
import lab.space.my_house_24.specification.ApartmentSpecification;
import lab.space.my_house_24.specification.ApartmentSpecificationForMailing;
import lab.space.my_house_24.specification.ApartmentSpecificationForMasterApplication;
import lab.space.my_house_24.specification.ApartmentSpecificationForSelect;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApartmentServiceImplTest {

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private HouseService houseService;

    @Mock
    private BankBookService bankBookService;

    @Mock
    private ApartmentSpecificationForMasterApplication apartmentSpecificationForMasterApplication;

    @InjectMocks
    private ApartmentServiceImpl apartmentService;

    @Test
    void findAllForMainPage() {
        when(houseService.findById(1L)).thenReturn(House.builder().name("Name").build());
        ApartmentRequestForMainPage apartmentRequestForMainPage = ApartmentRequestForMainPage.builder()
                .page(1)
                .house("1")
                .build();
        ApartmentSpecification apartmentSpecification = ApartmentSpecification.builder().apartmentRequestForMainPage(ApartmentRequestForMainPage.builder().page(1).house("Name").build()).build();
        Page<Apartment> apartmentPage = new PageImpl<>(List.of(
                Apartment.builder().house(House.builder().build()).user(User.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().house(House.builder().build()).user(User.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().house(House.builder().build()).user(User.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().house(House.builder().build()).user(User.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().house(House.builder().build()).user(User.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).build()
        ));
        when(apartmentRepository.findAll(apartmentSpecification, PageRequest.of(1,10))).thenReturn(apartmentPage);
        Page<ApartmentResponse> apartmentResponses = apartmentService.findAllForMainPage(apartmentRequestForMainPage);
        assertEquals(5,apartmentResponses.getTotalElements());

    }

    @Test
    void findAllForMainPage_HouseNull() {
        ApartmentRequestForMainPage apartmentRequestForMainPage = ApartmentRequestForMainPage.builder()
                .page(1)
                .build();
        ApartmentSpecification apartmentSpecification = ApartmentSpecification.builder().apartmentRequestForMainPage(ApartmentRequestForMainPage.builder().page(1).build()).build();
        Page<Apartment> apartmentPage = new PageImpl<>(List.of(
                Apartment.builder().house(House.builder().build()).user(User.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().house(House.builder().build()).user(User.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().house(House.builder().build()).user(User.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().house(House.builder().build()).user(User.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().house(House.builder().build()).user(User.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).build()
        ));
        when(apartmentRepository.findAll(apartmentSpecification, PageRequest.of(1,10))).thenReturn(apartmentPage);
        Page<ApartmentResponse> apartmentResponses = apartmentService.findAllForMainPage(apartmentRequestForMainPage);
        assertEquals(5,apartmentResponses.getTotalElements());

    }

    @Test
    void deleteApartment() {
        apartmentService.deleteApartment(1L);
        verify(apartmentRepository).deleteById(1L);
    }

    @Test
    void findByIdForCard() {
        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(
                Apartment.builder()
                        .id(1L)
                        .number(100)
                        .section(Section.builder().name("section").build())
                        .floor(Floor.builder().name("floor").build())
                        .bankBook(BankBook.builder().id(1L).number("000001").cashBoxes(List.of()).build())
                        .rate(Rate.builder().id(1L).name("Name").build())
                        .area(20.01)
                        .house(House.builder().id(1L).name("RC name").build())
                        .user(User.builder().surname("s").firstname("f").lastname("l").id(1L).build())
                        .build()
        ));

        assertEquals(ApartmentResponseForCard.builder()
                .id(1L)
                .number(100)
                .section("section")
                .floor("floor")
                .bankBook(BankBookResponseForApartmentCard.builder().id(1L).number("000001").idCashBox(0L).build())
                .rate(RateResponseForTable.builder().id(1L).name("Name").build())
                .area(20.01)
                .house(HouseResponseForTable.builder().id(1L).name("RC name").build())
                .owner(UserResponseForTable.builder().name("l f s").id(1L).build())
                .title("№100,RC name")
                .build(),apartmentService.findByIdForCard(1L));
    }

    @Test
    void saveApartment() {
        ApartmentAddRequest apartmentAddRequest = ApartmentAddRequest.builder()
                .area(20.01)
                .bankBook("0000001")
                .owner(1L)
                .floor(1L)
                .house(1L)
                .section(1L)
                .rate(1L)
                .number(100)
                .build();
        when(bankBookService.findByNumber("0000001")).thenReturn(Optional.empty());
        apartmentService.saveApartment(apartmentAddRequest);
        verify(apartmentRepository).save(any(Apartment.class));
    }

    @Test
    void saveApartment_BankBookNotEmpty() {
        ApartmentAddRequest apartmentAddRequest = ApartmentAddRequest.builder()
                .area(20.01)
                .bankBook("0000001")
                .owner(1L)
                .floor(1L)
                .house(1L)
                .section(1L)
                .rate(1L)
                .number(100)
                .build();
        when(bankBookService.findByNumber("0000001")).thenReturn(Optional.of(BankBook.builder().number("0000001").apartment(Apartment.builder().build()).build()));
        apartmentService.saveApartment(apartmentAddRequest);
        verify(apartmentRepository).save(any(Apartment.class));

    }

    @Test
    void findByIdApartment() {
        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(
                Apartment.builder()
                        .id(1L)
                        .number(100)
                        .section(Section.builder().id(1L).name("section").build())
                        .floor(Floor.builder().id(1L).name("floor").build())
                        .bankBook(BankBook.builder().id(1L).number("000001").build())
                        .rate(Rate.builder().id(1L).name("Name").build())
                        .area(20.01)
                        .house(House.builder().id(1L).name("RC name").build())
                        .user(User.builder().surname("s").firstname("f").lastname("l").id(1L).build())
                        .build()
        ));

        assertEquals(ApartmentResponseForEdit.builder()
                .number(100)
                .section(SectionResponseForTable.builder().id(1L).name("section").build())
                .floor(FloorResponseForTable.builder().id(1L).name("floor").build())
                .bankBook(BankBookResponseForTable.builder().id(1L).number("000001").build())
                .rate(RateResponseForTable.builder().id(1L).name("Name").build())
                .area(20.01)
                .house(HouseResponseForTable.builder().id(1L).name("RC name").build())
                .owner(UserResponseForTable.builder().name("l f s").id(1L).build())
                .build(),apartmentService.findByIdApartment(1L));

    }

    @Test
    void updateApartment() {
        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(
                Apartment.builder()
                        .id(1L)
                        .number(100)
                        .section(Section.builder().id(1L).name("section").build())
                        .floor(Floor.builder().id(1L).name("floor").build())
                        .bankBook(BankBook.builder().id(2L).number("000001").build())
                        .rate(Rate.builder().id(1L).name("Name").build())
                        .area(20.01)
                        .house(House.builder().id(1L).name("RC name").build())
                        .user(User.builder().surname("s").firstname("f").lastname("l").id(1L).build())
                        .build()
        ));
        ApartmentAddRequest apartmentAddRequest = ApartmentAddRequest.builder()
                .area(20.01)
                .bankBook("0000001")
                .owner(1L)
                .floor(1L)
                .house(1L)
                .section(1L)
                .rate(1L)
                .number(100)
                .build();
        when(bankBookService.findByNumber("0000001")).thenReturn(Optional.empty());
        apartmentService.updateApartment(1L, apartmentAddRequest);
        verify(apartmentRepository).save(any(Apartment.class));
    }

    @Test
    void updateApartment_BankBookNotEmpty() {
        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(
                Apartment.builder()
                        .id(1L)
                        .number(100)
                        .section(Section.builder().id(1L).name("section").build())
                        .floor(Floor.builder().id(1L).name("floor").build())
                        .bankBook(BankBook.builder().id(1L).number("000002").build())
                        .rate(Rate.builder().id(1L).name("Name").build())
                        .area(20.01)
                        .house(House.builder().id(1L).name("RC name").build())
                        .user(User.builder().surname("s").firstname("f").lastname("l").id(1L).build())
                        .build()
        ));
        ApartmentAddRequest apartmentAddRequest = ApartmentAddRequest.builder()
                .area(20.01)
                .bankBook("0000001")
                .owner(1L)
                .floor(1L)
                .house(1L)
                .section(1L)
                .rate(1L)
                .number(100)
                .build();
        when(bankBookService.findByNumber("0000001")).thenReturn(Optional.of(BankBook.builder().id(2L).number("0000001").apartment(Apartment.builder().build()).build()));
        apartmentService.updateApartment(1L, apartmentAddRequest);
        verify(apartmentRepository).save(any(Apartment.class));
    }

    @Test
    void findById() {
        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(
                Apartment.builder()
                        .id(1L)
                        .number(100)
                        .section(Section.builder().id(1L).name("section").build())
                        .floor(Floor.builder().id(1L).name("floor").build())
                        .bankBook(BankBook.builder().id(1L).number("000001").build())
                        .rate(Rate.builder().id(1L).name("Name").build())
                        .area(20.01)
                        .house(House.builder().id(1L).name("RC name").build())
                        .user(User.builder().surname("s").firstname("f").lastname("l").id(1L).build())
                        .build()
        ));
        assertEquals(Apartment.builder()
                .id(1L)
                .number(100)
                .section(Section.builder().id(1L).name("section").build())
                .floor(Floor.builder().id(1L).name("floor").build())
                .bankBook(BankBook.builder().id(1L).number("000001").build())
                .rate(Rate.builder().id(1L).name("Name").build())
                .area(20.01)
                .house(House.builder().id(1L).name("RC name").build())
                .user(User.builder().surname("s").firstname("f").lastname("l").id(1L).build())
                .build(), apartmentService.findById(1L));
    }

    @Test
    void getAllApartmentResponseByUserId() {
        when(apartmentSpecificationForMasterApplication.getApartmentByUserId(new ApartmentMastersApplicationRequest(1L,"Test", 1))).thenReturn(new Specification<Apartment>() {
            @Override
            public Predicate toPredicate(Root<Apartment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    if (nonNull(1L)) {
                        predicates.add(criteriaBuilder.or(
                                criteriaBuilder.equal(root.get("user").get("id"), 1L)
                        ));
                    }
                    query.orderBy(criteriaBuilder.asc(root.get("id")));
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        });
        when(apartmentRepository.findAll((Specification<Apartment>) any(), any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(
                Apartment.builder().house(House.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).user(User.builder().build()).build(),
                Apartment.builder().house(House.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).user(User.builder().build()).build(),
                Apartment.builder().house(House.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).user(User.builder().build()).build(),
                Apartment.builder().house(House.builder().build()).floor(Floor.builder().build()).section(Section.builder().build()).user(User.builder().build()).build())
        ));

        Page<ApartmentResponseForMastersApplication> apartmentResponseForMastersApplications = apartmentService.getAllApartmentResponseByUserId(new ApartmentMastersApplicationRequest(1L,"Test", 1));
        assertEquals(4, apartmentResponseForMastersApplications.getTotalElements());
    }

    @Test
    void getAllApartmentResponseByHouseId() {
        when(apartmentRepository.findAllByHouse_Id(1L)).thenReturn(List.of(
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).build()
        ));

        assertEquals(4, apartmentService.getAllApartmentResponseByHouseId(1L).size());
    }

    @Test
    void getAllApartmentResponseByHouseIdAndSectionId() {
        when(apartmentRepository.findAllByHouse_IdAndSection_IdOrderById(1L,1L)).thenReturn(List.of(
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).build()
        ));

        assertEquals(4, apartmentService.getAllApartmentResponseByHouseIdAndSectionId(1L,1L).size());
    }

    @Test
    void getAllApartmentResponseByHouseIdForBill() {
        when(apartmentRepository.findAllByHouse_Id(1L)).thenReturn(List.of(
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).bankBook(BankBook.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).bankBook(BankBook.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).bankBook(BankBook.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).bankBook(BankBook.builder().build()).build()
        ));

        assertEquals(4, apartmentService.getAllApartmentResponseByHouseIdForBill(1L).size());

    }

    @Test
    void getAllApartmentResponseByHouseIdAndSectionIdForBill() {
        when(apartmentRepository.findAllByHouse_IdAndSection_IdOrderById(1L,1L)).thenReturn(List.of(
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).bankBook(BankBook.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).bankBook(BankBook.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).bankBook(BankBook.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).bankBook(BankBook.builder().build()).build()
        ));

        assertEquals(4, apartmentService.getAllApartmentResponseByHouseIdAndSectionIdForBill(1L,1L).size());
    }

    @Test
    void getAllApartmentResponseForBill() {
        when(apartmentRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(List.of(
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).bankBook(BankBook.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).bankBook(BankBook.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).bankBook(BankBook.builder().build()).build(),
                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).bankBook(BankBook.builder().build()).build()
        ));

        assertEquals(4, apartmentService.getAllApartmentResponseForBill().size());
    }

//    @Test
//    void getAllApartmentResponse() {
//        when(apartmentRepository.findAll((Specification<Apartment>) any(), any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(
//                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).build(),
//                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).build(),
//                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).build(),
//                Apartment.builder().user(User.builder().build()).house(House.builder().build()).section(Section.builder().build()).build()
//        )));
//
//        assertEquals(4, apartmentService.getAllApartmentResponse(1,"Test",1L,1L).getTotalElements());
//
//    }

    @Test
    void apartmentForSelect() {
        ApartmentSpecificationForSelect apartmentSpecification = ApartmentSpecificationForSelect.builder()
                .idSection(1L)
                .idHouse(1L)
                .idFloor(1L)
                .duty(true)
                .build();
        when(apartmentRepository.findAll(apartmentSpecification)).thenReturn(List.of(
                Apartment.builder().build(),
                Apartment.builder().build(),
                Apartment.builder().build(),
                Apartment.builder().build()
        ));

        assertEquals(4, apartmentService.apartmentForSelect(1L,1L,1L,true).size());
    }

    @Test
    void findAllApartmentByHouse() {
        when(apartmentRepository.findAllByHouse_Id(1L)).thenReturn(List.of(
                Apartment.builder().build(),
                Apartment.builder().build(),
                Apartment.builder().build(),
                Apartment.builder().build()
        ));

        assertEquals(4, apartmentService.findAllApartmentByHouse(1L).size());
    }

    @Test
    void apartmentListForSelect() {
        when(apartmentRepository.findAll()).thenReturn(List.of(
                Apartment.builder().build(),
                Apartment.builder().build(),
                Apartment.builder().build(),
                Apartment.builder().build()
        ));

        assertEquals(4, apartmentService.apartmentListForSelect().size());
    }

    @Test
    void count() {
        when(apartmentRepository.count()).thenReturn(1L);

        assertEquals(1L, apartmentService.count());
    }

    @Test
    void apartmentListForMessage() {
        ApartmentSpecificationForMailing apartmentSpecification = ApartmentSpecificationForMailing.builder()
                .idSection(1L)
                .idHouse(1L)
                .idFloor(1L)
                .idApartment(1L)
                .debt(true)
                .build();
        when(apartmentRepository.findAll(apartmentSpecification)).thenReturn(List.of(
                Apartment.builder().build(),
                Apartment.builder().build(),
                Apartment.builder().build(),
                Apartment.builder().build()
        ));

        assertEquals(4, apartmentService.apartmentListForMessage(1L,1L,1L,1L,true).size());
    }
}
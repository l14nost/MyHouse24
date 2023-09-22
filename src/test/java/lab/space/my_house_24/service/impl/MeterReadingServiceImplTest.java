package lab.space.my_house_24.service.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.enums.MeterReadingStatus;
import lab.space.my_house_24.model.apartment.ApartmentMastersApplicationRequest;
import lab.space.my_house_24.model.apartment.ApartmentResponseForTable;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.meterReading.*;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.service.ServiceResponseForSelect;
import lab.space.my_house_24.repository.MeterReadingRepository;
import lab.space.my_house_24.specification.MeterReadingApartmentSpecification;
import lab.space.my_house_24.specification.MeterReadingSpecification;
import lab.space.my_house_24.specification.MeterReadingSpecificationForBill;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeterReadingServiceImplTest {
    @Mock
    private MeterReadingRepository meterReadingRepository;
    @Mock
    private MeterReadingSpecificationForBill meterReadingSpecificationForBill;
    @InjectMocks
    private MeterReadingServiceImpl meterReadingService;

    @Test
    void findAllForMain() {
        MeterReadingRequestForMainPage meterReadingRequestForMainPage = MeterReadingRequestForMainPage.builder()
                .page(1)
                .build();
        MeterReadingSpecification meterReadingSpecification = MeterReadingSpecification.builder()
                .meterReadingRequestForMainPage(meterReadingRequestForMainPage)
                .build();

        Page<MeterReading> meterReadingPage = new PageImpl<>(List.of(
                MeterReading.builder().service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().section(Section.builder().build()).house(House.builder().build()).build()).build(),
                MeterReading.builder().service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().section(Section.builder().build()).house(House.builder().build()).build()).build(),
                MeterReading.builder().service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().section(Section.builder().build()).house(House.builder().build()).build()).build(),
                MeterReading.builder().service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().section(Section.builder().build()).house(House.builder().build()).build()).build()
        ));

        when(meterReadingRepository.findAll(meterReadingSpecification, PageRequest.of(1,10))).thenReturn(meterReadingPage);

        assertEquals(4, meterReadingService.findAllForMain(meterReadingRequestForMainPage).getTotalElements());
        assertEquals(MeterReadingResponseForMain.class, meterReadingService.findAllForMain(meterReadingRequestForMainPage).iterator().next().getClass());
    }

    @Test
    void findAllForApartment() {
        MeterReadingRequestForApartmentPage meterReadingRequestForMainPage = MeterReadingRequestForApartmentPage.builder()
                .page(1)
                .build();
        MeterReadingApartmentSpecification meterReadingSpecification = MeterReadingApartmentSpecification.builder()
                .meterReadingRequestForApartmentPage(meterReadingRequestForMainPage)
                .build();

        Page<MeterReading> meterReadingPage = new PageImpl<>(List.of(
                MeterReading.builder().service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().section(Section.builder().build()).house(House.builder().build()).build()).status(MeterReadingStatus.CONSIDERED).date(Instant.now()).build(),
                MeterReading.builder().service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().section(Section.builder().build()).house(House.builder().build()).build()).status(MeterReadingStatus.CONSIDERED).date(Instant.now()).build(),
                MeterReading.builder().service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().section(Section.builder().build()).house(House.builder().build()).build()).status(MeterReadingStatus.CONSIDERED).date(Instant.now()).build(),
                MeterReading.builder().service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().section(Section.builder().build()).house(House.builder().build()).build()).status(MeterReadingStatus.CONSIDERED).date(Instant.now()).build()
        ));

        when(meterReadingRepository.findAll(meterReadingSpecification, PageRequest.of(1,10))).thenReturn(meterReadingPage);
        assertEquals(4, meterReadingService.findAllForApartment(meterReadingRequestForMainPage).getTotalElements());
        assertEquals(MeterReadingResponseForApartment.class, meterReadingService.findAllForApartment(meterReadingRequestForMainPage).iterator().next().getClass());

    }

    @Test
    void save() {
        MeterReadingRequestForAdd meterReadingRequestForAdd = MeterReadingRequestForAdd.builder()
                .service(1L)
                .status(MeterReadingStatus.CONSIDERED)
                .date(LocalDate.of(2021,12,12))
                .count(123.1)
                .section(1L)
                .build();

        meterReadingService.save(meterReadingRequestForAdd);
        verify(meterReadingRepository).save(MeterReading.builder()
                .status(meterReadingRequestForAdd.status())
                .date(meterReadingRequestForAdd.date().atStartOfDay(ZoneId.systemDefault()).toInstant())
                .apartment(Apartment.builder().id(meterReadingRequestForAdd.apartment()).build())
                .count(meterReadingRequestForAdd.count())
                .service(lab.space.my_house_24.entity.Service.builder().id(meterReadingRequestForAdd.service()).build())
                .build());
    }

    @Test
    void update() {
        MeterReadingRequestForEdit meterReadingRequestForEdit = MeterReadingRequestForEdit.builder()
                .service(1L)
                .status(MeterReadingStatus.CONSIDERED)
                .date(LocalDate.of(2021,12,12))
                .count(123.1)
                .section(1L)
                .build();

        when(meterReadingRepository.findById(1L)).thenReturn(Optional.of(MeterReading.builder().build()));

        meterReadingService.update(meterReadingRequestForEdit, 1L);
        MeterReading meterReading = MeterReading.builder().build();
        meterReading.setService(lab.space.my_house_24.entity.Service.builder().id(meterReadingRequestForEdit.service()).build());
        meterReading.setApartment(Apartment.builder().id(meterReadingRequestForEdit.apartment()).build());
        meterReading.setDate(meterReadingRequestForEdit.date().atStartOfDay(ZoneId.systemDefault()).toInstant());
        meterReading.setCount(meterReadingRequestForEdit.count());
        meterReading.setStatus(meterReadingRequestForEdit.status());
        verify(meterReadingRepository, times(1)).save(meterReading);
    }

    @Test
    void count() {
        when(meterReadingRepository.count()).thenReturn(4L);
        assertEquals(4L, meterReadingService.count());
    }

    @Test
    void findByIdEdit() {

        when(meterReadingRepository.findById(1L)).thenReturn(Optional.of(MeterReading.builder().id(1L).service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().section(Section.builder().build()).house(House.builder().build()).build()).status(MeterReadingStatus.CONSIDERED).date(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant()).build()));
        assertEquals(MeterReadingResponseEdit.builder()
                .number("000000001")
                .section(SectionResponseForTable.builder().build())
                .house(HouseResponseForTable.builder().build())
                .apartment(ApartmentResponseForTable.builder().build())
                .service(ServiceResponseForSelect.builder().build())
                .date(LocalDate.of(2021,12,12))
                .status(MeterReadingStatus.CONSIDERED)
                .build(),meterReadingService.findByIdEdit(1L));
    }

    @Test
    void findByIdForApartmentAdd() {
        when(meterReadingRepository.findAllByApartment_Id(1L)).thenReturn(List.of(MeterReading.builder().id(1L).service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().section(Section.builder().build()).house(House.builder().build()).build()).status(MeterReadingStatus.CONSIDERED).date(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant()).build()));
        assertEquals(MeterReadingResponseEdit.builder()
                .number("000000001")
                .section(SectionResponseForTable.builder().build())
                .house(HouseResponseForTable.builder().build())
                .apartment(ApartmentResponseForTable.builder().build())
                .service(ServiceResponseForSelect.builder().build())
                .date(LocalDate.of(2021,12,12))
                .status(MeterReadingStatus.CONSIDERED)
                .build(),meterReadingService.findByIdForApartmentAdd(1L,0L));
    }
    @Test
    void findByIdForApartmentAdd_ApartmentEmpty() {
        when(meterReadingRepository.findAllByApartment_Id(1L)).thenReturn(List.of());
        assertNull(meterReadingService.findByIdForApartmentAdd(1L, 0L));
    }

    @Test
    void findByIdForApartmentAdd_Service() {
        when(meterReadingRepository.findAllByApartment_IdAndService_id(1L,1L)).thenReturn(List.of(MeterReading.builder().id(1L).service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().section(Section.builder().build()).house(House.builder().build()).build()).status(MeterReadingStatus.CONSIDERED).date(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant()).build()));
        assertEquals(MeterReadingResponseEdit.builder()
                .number("000000001")
                .section(SectionResponseForTable.builder().build())
                .house(HouseResponseForTable.builder().build())
                .apartment(ApartmentResponseForTable.builder().build())
                .service(ServiceResponseForSelect.builder().build())
                .date(LocalDate.of(2021,12,12))
                .status(MeterReadingStatus.CONSIDERED)
                .build(),meterReadingService.findByIdForApartmentAdd(1L,1L));
    }

    @Test
    void findByIdForApartmentAdd_ServiceEmpty() {
        when(meterReadingRepository.findAllByApartment_IdAndService_id(1L,1L)).thenReturn(List.of());
        assertNull(meterReadingService.findByIdForApartmentAdd(1L, 1L));
    }

    @Test
    void findById() {
        when(meterReadingRepository.findById(1L)).thenReturn(Optional.of(MeterReading.builder().id(1L).service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().section(Section.builder().build()).house(House.builder().build()).build()).status(MeterReadingStatus.CONSIDERED).date(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant()).build()));
        assertEquals(
                MeterReading.builder().id(1L).service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().section(Section.builder().build()).house(House.builder().build()).build()).status(MeterReadingStatus.CONSIDERED).date(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant()).build(),
                meterReadingService.findById(1L)
        );
    }

    @Test
    void delete() {
        meterReadingService.delete(1L);
        verify(meterReadingRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAllMeterReadingResponseByRequest() {
        when(meterReadingSpecificationForBill.getMeterReadingByRequest(new MeterReadingRequestForBill(1, 1L, 1L, 1L))).thenReturn(new Specification<MeterReading>() {
            @Override
            public Predicate toPredicate(Root<MeterReading> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (nonNull(1L)) {
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("apartment").get("id"), 1L)
                    ));
                }
                if (nonNull(1L)) {
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("apartment").get("section").get("id"), 1L)
                    ));
                }
                if (nonNull(1L)) {
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("apartment").get("house").get("id"), 1L)
                    ));
                }
                query.orderBy(criteriaBuilder.desc(root.get("id")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

            }
        });

        when(meterReadingRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(
                MeterReading.builder().service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().number(123).section(Section.builder().build()).house(House.builder().build()).build()).status(MeterReadingStatus.CONSIDERED).date(Instant.now()).build(),
                MeterReading.builder().service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().number(123).section(Section.builder().build()).house(House.builder().build()).build()).status(MeterReadingStatus.CONSIDERED).date(Instant.now()).build(),
                MeterReading.builder().service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().number(123).section(Section.builder().build()).house(House.builder().build()).build()).status(MeterReadingStatus.CONSIDERED).date(Instant.now()).build(),
                MeterReading.builder().service(Service.builder().unit(Unit.builder().build()).build()).apartment(Apartment.builder().number(123).section(Section.builder().build()).house(House.builder().build()).build()).status(MeterReadingStatus.CONSIDERED).date(Instant.now()).build()
        )));

        assertEquals(4, meterReadingService.getAllMeterReadingResponseByRequest(new MeterReadingRequestForBill(1, 1L, 1L, 1L)).getTotalElements());
        assertEquals(MeterReadingResponseForBill.class, meterReadingService.getAllMeterReadingResponseByRequest(new MeterReadingRequestForBill(1, 1L, 1L, 1L)).iterator().next().getClass());



    }
}
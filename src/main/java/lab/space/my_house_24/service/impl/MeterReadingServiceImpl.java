package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.MeterReading;
import lab.space.my_house_24.enums.MeterReadingStatus;
import lab.space.my_house_24.mapper.MeterReadingMapper;
import lab.space.my_house_24.model.meterReading.*;
import lab.space.my_house_24.repository.MeterReadingRepository;
import lab.space.my_house_24.service.MeterReadingService;
import lab.space.my_house_24.specification.MeterReadingApartmentSpecification;
import lab.space.my_house_24.specification.MeterReadingSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeterReadingServiceImpl implements MeterReadingService {
    private final MeterReadingRepository meterReadingRepository;
    @Override
    public Page<MeterReadingResponseForMain> findAllForMain(MeterReadingRequestForMainPage meterReadingRequestForMainPage) {
        MeterReadingSpecification meterReadingSpecification = MeterReadingSpecification.builder().meterReadingRequestForMainPage(meterReadingRequestForMainPage).build();
        return meterReadingRepository.findAll(meterReadingSpecification, PageRequest.of(meterReadingRequestForMainPage.page(), 10)).map(MeterReadingMapper::entityToDtoForMain);
    }

    @Override
    public Page<MeterReadingResponseForApartment> findAllForApartment(MeterReadingRequestForApartmentPage meterReadingRequestForApartmentPage) {
        MeterReadingApartmentSpecification apartmentSpecification = MeterReadingApartmentSpecification.builder().meterReadingRequestForApartmentPage(meterReadingRequestForApartmentPage).build();
        return meterReadingRepository.findAll(apartmentSpecification, PageRequest.of(meterReadingRequestForApartmentPage.page(), 10)).map(MeterReadingMapper::entityToDtoForApartment);
    }

    @Override
    public void save(MeterReadingRequestForAdd meterReadingRequestForAdd) {
        MeterReading meterReading = MeterReading.builder()
                .status(meterReadingRequestForAdd.status())
                .date(meterReadingRequestForAdd.date().atStartOfDay(ZoneId.systemDefault()).toInstant())
                .apartment(Apartment.builder().id(meterReadingRequestForAdd.apartment()).build())
                .count(meterReadingRequestForAdd.count())
                .service(lab.space.my_house_24.entity.Service.builder().id(meterReadingRequestForAdd.service()).build())
                .build();
        meterReadingRepository.save(meterReading);
    }

    @Override
    public void update(MeterReadingRequestForEdit meterReadingRequestForEdit, Long id) {
        MeterReading meterReading = findById(id);
        meterReading.setService(lab.space.my_house_24.entity.Service.builder().id(meterReadingRequestForEdit.service()).build());
        meterReading.setApartment(Apartment.builder().id(meterReadingRequestForEdit.apartment()).build());
        meterReading.setDate(meterReadingRequestForEdit.date().atStartOfDay(ZoneId.systemDefault()).toInstant());
        meterReading.setCount(meterReadingRequestForEdit.count());
        meterReading.setStatus(meterReadingRequestForEdit.status());
        meterReadingRepository.save(meterReading);
    }

    @Override
    public Long count() {
        return meterReadingRepository.count();
    }

    @Override
    public MeterReadingResponseEdit findByIdEdit(Long id) {
        return MeterReadingMapper.entityToDtoForEdit(meterReadingRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Meter reading by id "+id+" is not found")));
    }

    @Override
    public MeterReadingResponseEdit findByIdForApartmentAdd(Long id,Long idService) {
        if (idService!=0L){
            List<MeterReading> meterReadingList = meterReadingRepository.findAllByApartment_IdAndService_id(id,idService);
            if (!meterReadingList.isEmpty()) {
                return MeterReadingMapper.entityToDtoForEdit(meterReadingList.get(meterReadingList.size() - 1));
            }
            else return null;
        }
        else {
            List<MeterReading> meterReadingList = meterReadingRepository.findAllByApartment_Id(id);
            if (!meterReadingList.isEmpty()) {
                return MeterReadingMapper.entityToDtoForEdit(meterReadingList.get(meterReadingList.size() - 1));

            }
            else return null;

        }
    }

    @Override
    public MeterReading findById(Long id) {
        return meterReadingRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Meter reading by id "+id+" is not found"));
    }

    @Override
    public void delete(Long id) {
        meterReadingRepository.deleteById(id);
    }
}

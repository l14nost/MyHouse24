package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.MeterReading;
import lab.space.my_house_24.mapper.MeterReadingMapper;
import lab.space.my_house_24.model.meterReading.*;
import lab.space.my_house_24.repository.MeterReadingRepository;
import lab.space.my_house_24.service.MeterReadingService;
import lab.space.my_house_24.specification.MeterReadingApartmentSpecification;
import lab.space.my_house_24.specification.MeterReadingSpecification;
import lab.space.my_house_24.specification.MeterReadingSpecificationForBill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MeterReadingServiceImpl implements MeterReadingService {
    private final MeterReadingRepository meterReadingRepository;
    private final MeterReadingSpecificationForBill meterReadingSpecificationForBill;
    @Override
    public Page<MeterReadingResponseForMain> findAllForMain(MeterReadingRequestForMainPage meterReadingRequestForMainPage) {
        log.info("Try to find all meter reading dto for main page");
        MeterReadingSpecification meterReadingSpecification = MeterReadingSpecification.builder().meterReadingRequestForMainPage(meterReadingRequestForMainPage).build();
        return meterReadingRepository.findAll(meterReadingSpecification, PageRequest.of(meterReadingRequestForMainPage.page(), 10)).map(MeterReadingMapper::entityToDtoForMain);
    }

    @Override
    public Page<MeterReadingResponseForApartment> findAllForApartment(MeterReadingRequestForApartmentPage meterReadingRequestForApartmentPage) {
        log.info("Try to find all for apartment page");
        MeterReadingApartmentSpecification apartmentSpecification = MeterReadingApartmentSpecification.builder().meterReadingRequestForApartmentPage(meterReadingRequestForApartmentPage).build();
        return meterReadingRepository.findAll(apartmentSpecification, PageRequest.of(meterReadingRequestForApartmentPage.page(), 10)).map(MeterReadingMapper::entityToDtoForApartment);
    }

    @Override
    public void save(MeterReadingRequestForAdd meterReadingRequestForAdd) {
        log.info("Try to save new meter reading");
        MeterReading meterReading = MeterReading.builder()
                .status(meterReadingRequestForAdd.status())
                .date(meterReadingRequestForAdd.date().atStartOfDay(ZoneId.systemDefault()).toInstant())
                .apartment(Apartment.builder().id(meterReadingRequestForAdd.apartment()).build())
                .count(meterReadingRequestForAdd.count())
                .service(lab.space.my_house_24.entity.Service.builder().id(meterReadingRequestForAdd.service()).build())
                .build();
        meterReadingRepository.save(meterReading);
        log.info("Meter reading was save");
    }

    @Override
    public void update(MeterReadingRequestForEdit meterReadingRequestForEdit, Long id) {
        log.info("Try to update meter reading by id: "+id);
        MeterReading meterReading = findById(id);
        meterReading.setService(lab.space.my_house_24.entity.Service.builder().id(meterReadingRequestForEdit.service()).build());
        meterReading.setApartment(Apartment.builder().id(meterReadingRequestForEdit.apartment()).build());
        meterReading.setDate(meterReadingRequestForEdit.date().atStartOfDay(ZoneId.systemDefault()).toInstant());
        meterReading.setCount(meterReadingRequestForEdit.count());
        meterReading.setStatus(meterReadingRequestForEdit.status());
        meterReadingRepository.save(meterReading);
        log.info("Meter reading was update");
    }

    @Override
    public Long count() {
        log.info("Try to get count of all meter readings");
        return meterReadingRepository.count();
    }

    @Override
    public MeterReadingResponseEdit findByIdEdit(Long id) {
        log.info("Try to get meter reading dto for edit page by id: "+id);
        return MeterReadingMapper.entityToDtoForEdit(meterReadingRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Meter reading by id "+id+" is not found")));
    }

    @Override
    public MeterReadingResponseEdit findByIdForApartmentAdd(Long id,Long idService) {
        log.info("Try to get meter reading dto for apartment add page by idApartment: "+id+" and idService: "+idService);
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
        log.info("Try to get meter reading by id: "+id);
        return meterReadingRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Meter reading by id "+id+" is not found"));
    }

    @Override
    public void delete(Long id) {
        log.info("Try to delete meter reading by id: "+id);
        meterReadingRepository.deleteById(id);
        log.info("Meter reading was delete");
    }

    @Override
    public Page<MeterReadingResponseForBill> getAllMeterReadingResponseByRequest(MeterReadingRequestForBill request) {
        log.info("Try to get all meter reading dto for bills");
        return meterReadingRepository.findAll(meterReadingSpecificationForBill.getMeterReadingByRequest(request),
                PageRequest.of(request.pageIndex(), 10)).map(MeterReadingMapper::toMeterReadingResponseForBill);
    }
}

package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.MeterReading;
import lab.space.my_house_24.model.meterReading.MeterReadingResponseEdit;
import lab.space.my_house_24.model.meterReading.MeterReadingResponseForApartment;
import lab.space.my_house_24.model.meterReading.MeterReadingResponseForMain;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MeterReadingMapper {

    public static MeterReadingResponseForMain entityToDtoForMain(MeterReading meterReading){
        return MeterReadingResponseForMain.builder()
                .id(meterReading.getId())
                .section(meterReading.getApartment().getSection().getName())
                .apartment("№"+meterReading.getApartment().getNumber()+", "+meterReading.getApartment().getHouse().getName())
                .house(meterReading.getApartment().getHouse().getName())
                .count(meterReading.getCount())
                .service(meterReading.getService().getName())
                .unit(meterReading.getService().getUnit().getName())
                .idApartment(meterReading.getApartment().getId())
                .idService(meterReading.getService().getId())
                .build();
    }
    public static MeterReadingResponseForApartment entityToDtoForApartment(MeterReading meterReading){
        return MeterReadingResponseForApartment.builder()
                .id(meterReading.getId())
                .status(EnumMapper.toSimpleDto(meterReading.getStatus().name(), meterReading.getStatus().getMeterReadingStatus(LocaleContextHolder.getLocale())))
                .section(meterReading.getApartment().getSection().getName())
                .apartment("№"+meterReading.getApartment().getNumber())
                .house(meterReading.getApartment().getHouse().getName())
                .count(meterReading.getCount())
                .service(meterReading.getService().getName())
                .unit(meterReading.getService().getUnit().getName())
                .date(meterReading.getDate().atZone(ZoneId.systemDefault()).toLocalDate())
                .month(meterReading.getDate().atZone(ZoneId.systemDefault()).getMonth()+", "+meterReading.getDate().atZone(ZoneId.systemDefault()).getYear())
                .build();
    }

    public static MeterReadingResponseEdit entityToDtoForEdit(MeterReading meterReading){
        return MeterReadingResponseEdit.builder()
                .number(String.format("%09d", meterReading.getId()))
                .section(SectionMapper.entityToDtoForTable(meterReading.getApartment().getSection()))
                .house(HouseMapper.entityToDtoForTable(meterReading.getApartment().getHouse()))
                .apartment(ApartmentMapper.entityToDtoForTable(meterReading.getApartment()))
                .status(meterReading.getStatus())
                .count(meterReading.getCount())
                .service(ServiceMapper.entityToDtoForSelect(meterReading.getService()))
                .date(meterReading.getDate().atZone(ZoneId.systemDefault()).toLocalDate())
                .build();
    }
}

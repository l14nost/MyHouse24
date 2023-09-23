package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.MastersApplication;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.model.masters_application.MastersApplicationResponse;
import lab.space.my_house_24.model.masters_application.MastersApplicationSaveRequest;
import lab.space.my_house_24.model.masters_application.MastersApplicationUpdateRequest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.util.Objects.nonNull;

public interface MastersApplicationMapper {
    static MastersApplicationResponse toMastersApplicationResponse(MastersApplication mastersApplication) {
        return MastersApplicationResponse.builder()
                .id(mastersApplication.getId())
                .createAt(mastersApplication.getCreateAt().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .description(mastersApplication.getDescription())
                .comment(mastersApplication.getComment())
                .master(EnumMapper.toSimpleDto(mastersApplication.getMaster().name(),
                        mastersApplication.getMaster().getMaster(LocaleContextHolder.getLocale())))
                .mastersApplicationStatus(EnumMapper.toSimpleDto(mastersApplication.getMastersApplicationStatus().name(),
                        mastersApplication.getMastersApplicationStatus().getMastersApplicationStatus(LocaleContextHolder.getLocale())))
                .staff(nonNull(mastersApplication.getStaff()) ? StaffMapper.toStaffResponse(mastersApplication.getStaff()) : null)
                .date(mastersApplication.getDateTime())
                .time(mastersApplication.getDateTime())
                .apartment(ApartmentMapper.entityToResponseForMastersApplication(mastersApplication.getApartment()))
                .build();
    }

    static MastersApplicationResponse toMastersApplicationResponseForTable(MastersApplication mastersApplication) {
        return MastersApplicationResponse.builder()
                .id(mastersApplication.getId())
                .description(mastersApplication.getDescription())
                .master(EnumMapper.toSimpleDto(mastersApplication.getMaster().name(),
                        mastersApplication.getMaster().getMaster(LocaleContextHolder.getLocale())))
                .mastersApplicationStatus(EnumMapper.toSimpleDto(mastersApplication.getMastersApplicationStatus().name(),
                        mastersApplication.getMastersApplicationStatus().getMastersApplicationStatus(LocaleContextHolder.getLocale())))
                .staff(nonNull(mastersApplication.getStaff()) ? StaffMapper.toStaffResponse(mastersApplication.getStaff()) : null)
                .fullDate(mastersApplication.getDateTime())
                .apartment(ApartmentMapper.entityToResponseForMastersApplicationTable(mastersApplication.getApartment()))
                .build();
    }

    static MastersApplication toMastersApplication(MastersApplicationUpdateRequest request, MastersApplication mastersApplication,
                                                   Staff staff, User user, Apartment apartment) {
        return mastersApplication
                .setDescription(request.description())
                .setComment(request.comment())
                .setMaster(request.master())
                .setMastersApplicationStatus(request.mastersApplicationStatus())
                .setDateTime(LocalDateTime.of(request.date(), request.time()))
                .setStaff(staff)
                .setUser(user)
                .setApartment(apartment);
    }

    static MastersApplication toMastersApplication(MastersApplicationSaveRequest request, Staff staff, User user, Apartment apartment) {
        return MastersApplication.builder()
                .description(request.description())
                .comment(request.comment())
                .master(request.master())
                .mastersApplicationStatus(request.mastersApplicationStatus())
                .dateTime(LocalDateTime.of(request.date(), request.time()))
                .staff(staff)
                .user(user)
                .apartment(apartment)
                .build();
    }
}

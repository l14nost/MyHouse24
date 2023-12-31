package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.MastersApplication;
import lab.space.my_house_24.enums.Master;
import lab.space.my_house_24.enums.MastersApplicationStatus;
import lab.space.my_house_24.mapper.EnumMapper;
import lab.space.my_house_24.mapper.MastersApplicationMapper;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.masters_application.MastersApplicationRequest;
import lab.space.my_house_24.model.masters_application.MastersApplicationResponse;
import lab.space.my_house_24.model.masters_application.MastersApplicationSaveRequest;
import lab.space.my_house_24.model.masters_application.MastersApplicationUpdateRequest;
import lab.space.my_house_24.repository.MastersApplicationRepository;
import lab.space.my_house_24.service.ApartmentService;
import lab.space.my_house_24.service.MastersApplicationService;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.service.UserService;
import lab.space.my_house_24.specification.MastersApplicationSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class MastersApplicationServiceImpl implements MastersApplicationService {

    private final MastersApplicationRepository repository;
    private final StaffService staffService;
    private final UserService userService;
    private final ApartmentService apartmentService;
    private final MastersApplicationSpecification specification;

    @Override
    public MastersApplication getMastersApplicationById(Long id) throws EntityNotFoundException {
        log.info("Try to find MastersApplication");
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MastersApplication not found by id " + id));
    }

    @Override
    public MastersApplicationResponse getMastersApplicationResponseById(Long id) throws EntityNotFoundException {
        log.info("Try to get MastersApplicationResponse");
        return MastersApplicationMapper.toMastersApplicationResponse(getMastersApplicationById(id));
    }

    @Override
    public void updateMastersApplicationByRequest(MastersApplicationUpdateRequest request) throws EntityNotFoundException {
        log.info("Try to update MastersApplication by Update Request");
        saveMastersApplication(MastersApplicationMapper.toMastersApplication(
                request,
                getMastersApplicationById(request.id()),
                nonNull(request.staffId()) ? staffService.getStaffById(request.staffId()) : null,
                userService.findEntityById(request.userId()),
                apartmentService.findById(request.apartmentId()))
        );
        log.info("Success update MastersApplication by Update Request");
    }

    @Override
    public void saveMastersApplicationByRequest(MastersApplicationSaveRequest request) throws EntityNotFoundException {
        log.info("Try to save MastersApplication by Save Request");
        saveMastersApplication(MastersApplicationMapper.toMastersApplication(
                request,
                nonNull(request.staffId()) ? staffService.getStaffById(request.staffId()) : null,
                userService.findEntityById(request.userId()),
                apartmentService.findById(request.apartmentId()))
        );
        log.info("Success save MastersApplication by Save Request");
    }

    @Override
    public void saveMastersApplication(MastersApplication mastersApplication) {
        log.info("Try to save MastersApplication");
        repository.save(mastersApplication);
        log.info("Success save MastersApplication");
    }

    @Override
    public void deleteMastersApplicationById(Long id) throws EntityNotFoundException {
        log.info("Try to delete MastersApplication");
        repository.delete(getMastersApplicationById(id));
        log.info("Success delete MastersApplication by id " + id);
    }

    @Override
    public Page<MastersApplicationResponse> getAllMastersApplication(MastersApplicationRequest request) {
        final int DEFAULT_PAGE_SIZE = 10;
        return repository.findAll(specification.getMastersApplicationByRequest(request),
                PageRequest.of(request.pageIndex(), DEFAULT_PAGE_SIZE)).map(MastersApplicationMapper::toMastersApplicationResponseForTable);
    }

    @Override
    public List<EnumResponse> getAllStatus() {
        log.info("Try to get all MastersApplicationStatus");
        return Arrays.stream(MastersApplicationStatus.values())
                .map(status -> EnumMapper.toSimpleDto(
                                status.name(),
                                status.getMastersApplicationStatus(LocaleContextHolder.getLocale())
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<EnumResponse> getAllTypeMaster() {
        log.info("Try to get all TypeMaster");
        return Arrays.stream(Master.values())
                .map(master -> EnumMapper.toSimpleDto(
                                master.name(),
                                master.getMaster(LocaleContextHolder.getLocale())
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public Long countByStatus(MastersApplicationStatus mastersApplicationStatus) {
        return repository.countByMastersApplicationStatus(mastersApplicationStatus);
    }
}

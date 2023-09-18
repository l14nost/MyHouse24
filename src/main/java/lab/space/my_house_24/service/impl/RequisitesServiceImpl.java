package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Requisites;
import lab.space.my_house_24.mapper.RequisitesMapper;
import lab.space.my_house_24.model.requites.RequisitesRequest;
import lab.space.my_house_24.model.requites.RequisitesResponse;
import lab.space.my_house_24.repository.RequisitesRepository;
import lab.space.my_house_24.service.RequisitesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequisitesServiceImpl implements RequisitesService {
    private final RequisitesRepository requisitesRepository;
    @Override
    public Requisites findById(Long id) {
        log.info("Try to find requisites by id: "+id);
        return requisitesRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Requisites by id "+id+" is not found"));
    }

    @Override
    public RequisitesResponse findByIdResponse(Long id) {
        log.info("Try to find requisites dto by id: "+id);
        return RequisitesMapper.entityToResponse(findById(id));

    }

    @Override
    public void update(RequisitesRequest requisitesRequest) {
        log.info("Try to update requisites");
        Requisites requisites = findById(1L);
        requisites.setName(requisitesRequest.name());
        requisites.setInfo(requisitesRequest.info());
        requisitesRepository.save(requisites);
        log.info("Requisites was update");
    }
}

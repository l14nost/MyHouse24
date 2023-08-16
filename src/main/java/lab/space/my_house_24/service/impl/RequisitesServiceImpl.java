package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Requisites;
import lab.space.my_house_24.mapper.RequisitesMapper;
import lab.space.my_house_24.model.requites.RequisitesRequest;
import lab.space.my_house_24.model.requites.RequisitesResponse;
import lab.space.my_house_24.repository.RequisitesRepository;
import lab.space.my_house_24.service.RequisitesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequisitesServiceImpl implements RequisitesService {
    private final RequisitesRepository requisitesRepository;
    @Override
    public Requisites findById(Long id) {
        return requisitesRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Requisites by id "+id+" is not found"));
    }

    @Override
    public RequisitesResponse findByIdResponse(Long id) {
        return RequisitesMapper.entityToResponse(requisitesRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Requisites by id "+id+" is not found")));

    }

    @Override
    public void update(RequisitesRequest requisitesRequest) {
        Requisites requisites = findById(1L);
        requisites.setName(requisitesRequest.name());
        requisites.setInfo(requisitesRequest.info());
        requisitesRepository.save(requisites);
    }
}

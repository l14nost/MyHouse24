package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Requisites;
import lab.space.my_house_24.model.requites.RequisitesRequest;
import lab.space.my_house_24.model.requites.RequisitesResponse;

public interface RequisitesService {

    Requisites findById(Long id);
    RequisitesResponse findByIdResponse(Long id);
    void update(RequisitesRequest requisitesRequest);
}

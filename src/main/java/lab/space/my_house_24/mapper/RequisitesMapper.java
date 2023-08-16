package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Requisites;
import lab.space.my_house_24.model.requites.RequisitesResponse;

public class RequisitesMapper {

    public static RequisitesResponse entityToResponse(Requisites requisites){
        return RequisitesResponse.builder()
                .info(requisites.getInfo())
                .name(requisites.getName())
                .build();
    }

}

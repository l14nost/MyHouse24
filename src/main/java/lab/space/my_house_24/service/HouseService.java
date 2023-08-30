package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.model.house.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HouseService {
    List<HouseResponseForTable> houseListForTable();
    House findById(Long id);

    Page<HouseResponseForMain> finaAllForMain(HouseRequestForMainPage houseRequestForMainPage);

    void deleteById(Long id);

    HouseResponseForCard findByIdForCard(Long id);

    void save(HouseRequestForAddPage houseRequestForAddPage);

    HouseResponseForEdit findByIdForEdit(Long id);

    void update(HouseRequestForEditPage houseRequestForEditPage,Long id);

    Page<HouseResponseForTable> houseResponseForSelect(Integer page, String search);

    List<House> findAll();

    Long count();
}

package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.model.house.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HouseService {

    List<House> findAll();

    List<HouseResponseForTable> houseListForTable();

    Page<HouseResponseForTable> houseListForTable(Integer pageIndex, String search);

    House findById(Long id);

    Page<HouseResponseForMain> findAllForMain(HouseRequestForMainPage houseRequestForMainPage);

    void deleteById(Long id);

    HouseResponseForCard findByIdForCard(Long id);

    void save(HouseRequestForAddPage houseRequestForAddPage);

    void save(House house);

    HouseResponseForEdit findByIdForEdit(Long id);

    void update(HouseRequestForEditPage houseRequestForEditPage, Long id);

    Page<HouseResponseForTable> houseResponseForSelect(Integer page, String search);

    Long count();
}

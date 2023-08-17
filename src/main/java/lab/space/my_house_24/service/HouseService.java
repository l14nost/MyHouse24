package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.model.house.HouseRequestForMainPage;
import lab.space.my_house_24.model.house.HouseResponseForCard;
import lab.space.my_house_24.model.house.HouseResponseForMain;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HouseService {
    List<HouseResponseForTable> houseListForTable();
    House findById(Long id);

    Page<HouseResponseForMain> finaAllForMain(HouseRequestForMainPage houseRequestForMainPage);

    void deleteById(Long id);

    HouseResponseForCard findByIdForCard(Long id);
}

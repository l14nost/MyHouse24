package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.mapper.HouseMapper;
import lab.space.my_house_24.model.house.HouseRequestForMainPage;
import lab.space.my_house_24.model.house.HouseResponseForCard;
import lab.space.my_house_24.model.house.HouseResponseForMain;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.repository.HouseRepository;
import lab.space.my_house_24.service.HouseService;
import lab.space.my_house_24.specification.HouseSpecification;
import lab.space.my_house_24.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;


    @Override
    public List<HouseResponseForTable> houseListForTable() {
        return houseRepository.findAll().stream().map(HouseMapper::entityToDtoForTable).toList();
    }
    @Override
    public House findById(Long id){
        return houseRepository.findById(id).orElseThrow(()->new EntityNotFoundException("House by id "+id+" is not found"));
    }

    @Override
    public Page<HouseResponseForMain> finaAllForMain(HouseRequestForMainPage houseRequestForMainPage) {
        HouseSpecification houseSpecification = HouseSpecification.builder().houseRequestForMainPage(houseRequestForMainPage).build();
        return houseRepository.findAll(houseSpecification, PageRequest.of(houseRequestForMainPage.page(),10)).map(HouseMapper::entityToDtoForMain);
    }

    @Override
    public void deleteById(Long id) {
        House house = findById(id);
        if (house.getImage1()!=null){
            FileHandler.deleteFile(house.getImage1());
        }
        if (house.getImage2()!=null){
            FileHandler.deleteFile(house.getImage2());
        }
        if (house.getImage3()!=null){
            FileHandler.deleteFile(house.getImage3());
        }
        if (house.getImage4()!=null){
            FileHandler.deleteFile(house.getImage4());
        }
        if (house.getImage5()!=null){
            FileHandler.deleteFile(house.getImage5());
        }
        houseRepository.deleteById(id);
    }

    @Override
    public HouseResponseForCard findByIdForCard(Long id) {
        return HouseMapper.entityToDtoForCard(findById(id));
    }
}

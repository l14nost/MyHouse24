package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Floor;
import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.entity.Section;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.mapper.HouseMapper;
import lab.space.my_house_24.model.house.*;
import lab.space.my_house_24.repository.HouseRepository;
import lab.space.my_house_24.service.HouseService;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.specification.HouseSpecification;
import lab.space.my_house_24.specification.HouseSpecificationForSelect;
import lab.space.my_house_24.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;
    private final StaffService staffService;


    @Override
    public List<House> findAll() {
        log.info("Try to get All houses");
        return houseRepository.findAll();
    }

    @Override
    public List<HouseResponseForTable> houseListForTable() {
        log.info("Try to get house dto for table");
        return houseRepository.findAll().stream().map(HouseMapper::entityToDtoForTable).toList();
    }

    @Override
    public Page<HouseResponseForTable> houseListForTable(Integer pageIndex, String search) {
        log.info("Try to get houses dto for table");
        return houseRepository.findAll(HouseSpecificationForSelect.builder().search(search).build(), PageRequest.of(pageIndex, 10)).map(HouseMapper::entityToDtoForTable);
    }

    @Override
    public House findById(Long id) {
        log.info("Try to get house by id: " + id);
        return houseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("House by id " + id + " is not found"));
    }

    @Override
    public Page<HouseResponseForMain> findAllForMain(HouseRequestForMainPage houseRequestForMainPage) {
        log.info("Try to find all house dto for main page");
        HouseSpecification houseSpecification = HouseSpecification.builder().houseRequestForMainPage(houseRequestForMainPage).build();
        return houseRepository.findAll(houseSpecification, PageRequest.of(houseRequestForMainPage.page(), 10)).map(HouseMapper::entityToDtoForMain);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Try to delete by id: " + id);
        House house = findById(id);
        if (house.getImage1() != null) {
            FileHandler.deleteFile(house.getImage1());
        }
        if (house.getImage2() != null) {
            FileHandler.deleteFile(house.getImage2());
        }
        if (house.getImage3() != null) {
            FileHandler.deleteFile(house.getImage3());
        }
        if (house.getImage4() != null) {
            FileHandler.deleteFile(house.getImage4());
        }
        if (house.getImage5() != null) {
            FileHandler.deleteFile(house.getImage5());
        }
        houseRepository.deleteById(id);
        log.info("House was delete ");
    }

    @Override
    public HouseResponseForCard findByIdForCard(Long id) {
        log.info("Try to find house dto for card page by id: " + id);
        return HouseMapper.entityToDtoForCard(findById(id));
    }

    @Override
    public void save(HouseRequestForAddPage houseRequestForAddPage) {
        log.info("Try to save new house");
        House house = House.builder()
                .address(houseRequestForAddPage.address())
                .name(houseRequestForAddPage.name())
                .staffList(new HashSet<>())
                .build();

        if (!houseRequestForAddPage.image1().isEmpty()) {
            house.setImage1(FileHandler.saveFile(houseRequestForAddPage.image1()));
        } else house.setImage1("");
        if (!houseRequestForAddPage.image2().isEmpty()) {
            house.setImage2(FileHandler.saveFile(houseRequestForAddPage.image2()));
        } else house.setImage2("");
        if (!houseRequestForAddPage.image3().isEmpty()) {
            house.setImage3(FileHandler.saveFile(houseRequestForAddPage.image3()));
        } else house.setImage3("");
        if (!houseRequestForAddPage.image4().isEmpty()) {
            house.setImage4(FileHandler.saveFile(houseRequestForAddPage.image4()));
        } else house.setImage4("");
        if (!houseRequestForAddPage.image5().isEmpty()) {
            house.setImage5(FileHandler.saveFile(houseRequestForAddPage.image5()));
        } else house.setImage5("");
        List<Floor> floorList = new ArrayList<>();
        for (int i = 0; i < houseRequestForAddPage.floorNameList().size(); i++) {
            floorList.add(Floor.builder().name(houseRequestForAddPage.floorNameList().get(i)).house(house).build());
        }
        List<Section> sectionList = new ArrayList<>();
        for (int i = 0; i < houseRequestForAddPage.sectionNameList().size(); i++) {
            sectionList.add(Section.builder().name(houseRequestForAddPage.sectionNameList().get(i)).house(house).build());
        }
        for (int i = 0; i < houseRequestForAddPage.userList().size(); i++) {
            Staff staff = staffService.getStaffById(houseRequestForAddPage.userList().get(i));
            house.addStaff(staff);
        }

        house.setFloorList(floorList);
        house.setSectionList(sectionList);

        houseRepository.save(house);
        log.info("House was save");
    }

    @Override
    public void save(House house) {
        log.info("Try to save house");
        houseRepository.save(house);
        log.info("House was saved");
    }

    @Override
    public HouseResponseForEdit findByIdForEdit(Long id) {
        log.info("Try to find house dto for edit page by id: " + id);
        return HouseMapper.entityToDtoForEditPage(findById(id));
    }

    @Override
    public void update(HouseRequestForEditPage houseRequestForEditPage, Long id) {
        log.info("Try to update house by id: " + id);
        House house = findById(id);
        house.setName(houseRequestForEditPage.name());
        house.setAddress(houseRequestForEditPage.address());
        for (int i = 0; i < houseRequestForEditPage.deleteSectionList().size(); i++) {
            for (int j = 0; j < house.getSectionList().size(); j++) {
                if (house.getSectionList().get(j).getId().equals(houseRequestForEditPage.deleteSectionList().get(i))) {
                    house.getSectionList().remove(j);
                }
            }
        }

        for (int i = 0; i < houseRequestForEditPage.deleteFloorList().size(); i++) {
            for (int j = 0; j < house.getFloorList().size(); j++) {
                if (house.getFloorList().get(j).getId().equals(houseRequestForEditPage.deleteFloorList().get(i))) {
                    house.getFloorList().remove(j);
                }
            }
        }

        if (!houseRequestForEditPage.image1().isEmpty()) {
            house.setImage1(FileHandler.saveFile(houseRequestForEditPage.image1()));
        }
        if (!houseRequestForEditPage.image2().isEmpty()) {
            house.setImage2(FileHandler.saveFile(houseRequestForEditPage.image2()));
        }
        if (!houseRequestForEditPage.image3().isEmpty()) {
            house.setImage3(FileHandler.saveFile(houseRequestForEditPage.image3()));
        }
        if (!houseRequestForEditPage.image4().isEmpty()) {
            house.setImage4(FileHandler.saveFile(houseRequestForEditPage.image4()));
        }
        if (!houseRequestForEditPage.image5().isEmpty()) {
            house.setImage5(FileHandler.saveFile(houseRequestForEditPage.image5()));
        }


        for (int i = 0; i < houseRequestForEditPage.sectionNameList().size(); i++) {
            if (i < house.getSectionList().size()) {
                house.getSectionList().get(i).setName(houseRequestForEditPage.sectionNameList().get(i));
            } else {
                house.getSectionList().add(Section.builder().name(houseRequestForEditPage.sectionNameList().get(i)).house(house).build());
            }
        }


        for (int i = 0; i < houseRequestForEditPage.floorNameList().size(); i++) {
            if (i < house.getFloorList().size()) {
                house.getFloorList().get(i).setName(houseRequestForEditPage.floorNameList().get(i));
            } else {
                house.getFloorList().add(Floor.builder().name(houseRequestForEditPage.sectionNameList().get(i)).house(house).build());
            }
        }
        house.removeAllFromStaffList();
        for (Long idStaff : houseRequestForEditPage.userList()) {
            house.addStaff(staffService.getStaffById(idStaff));
        }

        houseRepository.save(house);
        log.info("House was update");
    }

    @Override
    public Page<HouseResponseForTable> houseResponseForSelect(Integer page, String search) {
        log.info("Try to find all house dto for select");
        HouseSpecificationForSelect houseSpecificationForSelect = HouseSpecificationForSelect.builder().search(search).build();
        return houseRepository.findAll(houseSpecificationForSelect, PageRequest.of(page, 5)).map(HouseMapper::entityToDtoForTable);
    }

    @Override
    public Long count() {
        log.info("Try to get count of all houses");
        return houseRepository.count();
    }
}

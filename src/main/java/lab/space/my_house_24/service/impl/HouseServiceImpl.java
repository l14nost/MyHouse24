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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;
    private final StaffService staffService;


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

    @Override
    public void save(HouseRequestForAddPage houseRequestForAddPage) {
        Set<Long> staffSet = new HashSet<>();
        for (int i = 0;i<houseRequestForAddPage.userList().size();i++){
            staffSet.add(houseRequestForAddPage.userList().get(i));
        }
        List<Long> staffList = new ArrayList<>();
        for (Long i: staffSet){
            staffList.add(i);
        }
        House house = House.builder()
                .address(houseRequestForAddPage.address())
                .name(houseRequestForAddPage.name())
                .build();

        if (!houseRequestForAddPage.image1().isEmpty()){
            house.setImage1(FileHandler.saveFile(houseRequestForAddPage.image1()));
        }
        else house.setImage1("");
        if (!houseRequestForAddPage.image2().isEmpty()){
            house.setImage2(FileHandler.saveFile(houseRequestForAddPage.image2()));
        }
        else house.setImage2("");
        if (!houseRequestForAddPage.image3().isEmpty()){
            house.setImage3(FileHandler.saveFile(houseRequestForAddPage.image3()));
        }
        else house.setImage3("");
        if (!houseRequestForAddPage.image4().isEmpty()){
            house.setImage4(FileHandler.saveFile(houseRequestForAddPage.image4()));
        }
        else house.setImage4("");
        if (!houseRequestForAddPage.image5().isEmpty()){
            house.setImage5(FileHandler.saveFile(houseRequestForAddPage.image5()));
        }
        else house.setImage5("");
        List<Floor> floorList = new ArrayList<>();
        for (int i = 0;i<houseRequestForAddPage.floorNameList().size();i++){
            floorList.add(Floor.builder().name(houseRequestForAddPage.floorNameList().get(i)).house(house).build());
        }
        List<Section> sectionList = new ArrayList<>();
        for (int i = 0;i<houseRequestForAddPage.sectionNameList().size();i++){
            sectionList.add(Section.builder().name(houseRequestForAddPage.sectionNameList().get(i)).house(house).build());
        }
        List<Staff> staffListForAdd = new ArrayList<>();
        for (int i = 0;i<staffList.size();i++){
            Staff staff = staffService.getStaffById(staffList.get(i));
            staff.getHouseList().add(house);
            staffListForAdd.add(staff);
        }

        house.setFloorList(floorList);
        house.setSectionList(sectionList);
        house.setStaffList(staffListForAdd);

        houseRepository.save(house);
    }

    @Override
    public HouseResponseForEdit findByIdForEdit(Long id) {
        return HouseMapper.entityToDtoForEditPage(findById(id));
    }

    @Override
    public void update(HouseRequestForEditPage houseRequestForEditPage, Long id) {
        House house = findById(id);
        house.setName(houseRequestForEditPage.name());
        house.setAddress(houseRequestForEditPage.address());
        for (int i = 0; i<houseRequestForEditPage.deleteSectionList().size();i++){
            for (int j = 0; j<house.getSectionList().size();j++){
                if (house.getSectionList().get(j).getId().equals(houseRequestForEditPage.deleteSectionList().get(i))){
                    house.getSectionList().remove(j);
                }
            }
        }

        for (int i = 0; i<houseRequestForEditPage.deleteFloorList().size();i++){
            for (int j = 0; j<house.getFloorList().size();j++){
                if (house.getFloorList().get(j).getId().equals(houseRequestForEditPage.deleteFloorList().get(i))){
                    house.getFloorList().remove(j);
                }
            }
        }
        for (int i = 0; i<houseRequestForEditPage.deleteStaffList().size();i++){
            for (int j = 0; j<house.getStaffList().size();j++){
                if (house.getStaffList().get(j).getId().equals(houseRequestForEditPage.deleteStaffList().get(i))){
                    house.getStaffList().remove(j);
                    Staff staff = staffService.getStaffById(houseRequestForEditPage.deleteStaffList().get(i));
                    for (int k = 0; k<staff.getHouseList().size(); k++){
                        if (staff.getHouseList().get(k).getId().equals(id)){
                            staff.getHouseList().remove(k);
                        }
                    }
                }
            }
        }

        if (!houseRequestForEditPage.image1().isEmpty()){
            house.setImage1(FileHandler.saveFile(houseRequestForEditPage.image1()));
        }
        if (!houseRequestForEditPage.image2().isEmpty()){
            house.setImage2(FileHandler.saveFile(houseRequestForEditPage.image2()));
        }
        if (!houseRequestForEditPage.image3().isEmpty()){
            house.setImage3(FileHandler.saveFile(houseRequestForEditPage.image3()));
        }
        if (!houseRequestForEditPage.image4().isEmpty()){
            house.setImage4(FileHandler.saveFile(houseRequestForEditPage.image4()));
        }
        if (!houseRequestForEditPage.image5().isEmpty()){
            house.setImage5(FileHandler.saveFile(houseRequestForEditPage.image5()));
        }


        for (int i = 0; i<houseRequestForEditPage.sectionNameList().size();i++){
            if (i<house.getSectionList().size()){
                house.getSectionList().get(i).setName(houseRequestForEditPage.sectionNameList().get(i));
            }
            else {
                house.getSectionList().add(Section.builder().name(houseRequestForEditPage.sectionNameList().get(i)).house(house).build());
            }
        }


        for (int i = 0; i<houseRequestForEditPage.floorNameList().size();i++){
            if (i<house.getFloorList().size()){
                house.getFloorList().get(i).setName(houseRequestForEditPage.floorNameList().get(i));
            }
            else {
                house.getFloorList().add(Floor.builder().name(houseRequestForEditPage.sectionNameList().get(i)).house(house).build());
            }
        }

        List<Long> currentStaffList = new ArrayList<>();

        for (Staff staff: house.getStaffList()){
            currentStaffList.add(staff.getId());
        }

        for (int i = 0; i<houseRequestForEditPage.userList().size();i++){
            boolean check = false;
            for (int j = 0; j<currentStaffList.size();j++){
                if (currentStaffList.get(j).equals(houseRequestForEditPage.userList().get(i))){
                    check = true;
                }
            }
            if (!check && i>=currentStaffList.size()){
                Staff staff = staffService.getStaffById(houseRequestForEditPage.userList().get(i));
                staff.getHouseList().add(house);
                house.getStaffList().add(staff);
            }
            else if (!check && i<currentStaffList.size()){
                Staff oldStaff = staffService.getStaffById(currentStaffList.get(i));
                for (int k = 0; k<oldStaff.getHouseList().size();k++){
                    if (oldStaff.getHouseList().get(k).getId().equals(id)){
                        oldStaff.getHouseList().remove(k);
                    }
                }

                Staff staff = staffService.getStaffById(houseRequestForEditPage.userList().get(i));
                house.getStaffList().set(i, staff);
                staff.getHouseList().add(house);
            }
        }

        houseRepository.save(house);



    }

    @Override
    public Page<HouseResponseForTable> houseResponseForSelect(Integer page, String search) {
        HouseSpecificationForSelect houseSpecificationForSelect = HouseSpecificationForSelect.builder().search(search).build();
        return houseRepository.findAll(houseSpecificationForSelect,PageRequest.of(page,5)).map(HouseMapper::entityToDtoForTable);
    }

    @Override
    public List<House> findAll() {
        return houseRepository.findAll();
    }
}

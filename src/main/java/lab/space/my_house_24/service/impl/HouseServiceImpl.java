package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.mapper.HouseMapper;
import lab.space.my_house_24.mapper.UserMapper;
import lab.space.my_house_24.model.house.HouseResponseForUserPage;
import lab.space.my_house_24.model.user.UserMainPageRequest;
import lab.space.my_house_24.model.user.UserResponse;
import lab.space.my_house_24.repository.HouseRepository;
import lab.space.my_house_24.repository.UserRepository;
import lab.space.my_house_24.service.HouseService;
import lab.space.my_house_24.service.UserService;
import lab.space.my_house_24.specification.UserSpecification;
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
    public List<HouseResponseForUserPage> houseListForUserPage() {
        return houseRepository.findAll().stream().map(HouseMapper::entityToDtoForUserPage).toList();
    }
}

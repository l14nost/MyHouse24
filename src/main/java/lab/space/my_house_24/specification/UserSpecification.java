package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.*;
import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.user.UserMainPageRequest;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;


@Builder
public class UserSpecification implements Specification<User> {
    private UserMainPageRequest userMainPageRequest;


    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);

        List<Predicate> predicates = new ArrayList<>();
        if (userMainPageRequest.id()!=null){
            predicates.add(criteriaBuilder.equal(root.get("id"),userMainPageRequest.id()));
        }
        if (userMainPageRequest.number()!=null&&!userMainPageRequest.number().isEmpty()){
            predicates.add(criteriaBuilder.equal(root.get("number"),userMainPageRequest.number()));
        }
        if (userMainPageRequest.email()!=null&&!userMainPageRequest.email().isEmpty()){
            predicates.add(criteriaBuilder.like(root.get("email"),"%"+userMainPageRequest.email()+"%"));
        }
        if (userMainPageRequest.fullName()!=null&&!userMainPageRequest.fullName().isEmpty()){
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("firstname"),"%"+userMainPageRequest.fullName()+"%"),
                    criteriaBuilder.like(root.get("lastname"),"%"+userMainPageRequest.fullName()+"%"),
                    criteriaBuilder.like(root.get("surname"),"%"+userMainPageRequest.fullName()+"%")
            ));
        }
        if (userMainPageRequest.addDate()!=null){
            predicates.add(criteriaBuilder.like(root.get("addDate"),"%"+userMainPageRequest.addDate()+"%"));
        }

        if (userMainPageRequest.house()!=null&&!userMainPageRequest.house().isEmpty()){
            Join<User, Apartment> apartmentJoin = root.join("apartmentList", JoinType.INNER);
            Join<Apartment, House> houseJoin = apartmentJoin.join("house", JoinType.INNER);
            predicates.add(criteriaBuilder.like(houseJoin.get("text"),"%"+userMainPageRequest.house()+"%"));
        }

        if (userMainPageRequest.apartmentNumber()!=null){
            Join<User, Apartment> apartmentJoin = root.join("apartmentList", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(apartmentJoin.get("number"),userMainPageRequest.apartmentNumber()));
        }

        if (userMainPageRequest.duty()!=null){
            predicates.add(criteriaBuilder.equal(root.get("duty"),userMainPageRequest.duty()));
        }
        if (userMainPageRequest.status()!=null && !userMainPageRequest.status().isEmpty()){
            predicates.add(criteriaBuilder.equal(root.get("userStatus"), UserStatus.valueOf(userMainPageRequest.status())));
        }
        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        return predicate;


    }
}

package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.*;
import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.apartment.ApartmentRequestForMainPage;
import lab.space.my_house_24.model.user.UserMainPageRequest;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Builder
public class ApartmentSpecification implements Specification<Apartment> {
    private ApartmentRequestForMainPage apartmentRequestForMainPage;


    @Override
    public Predicate toPredicate(Root<Apartment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        List<Predicate> predicates = new ArrayList<>();
        if (apartmentRequestForMainPage.number()!=null){
            predicates.add(criteriaBuilder.equal(root.get("number"),apartmentRequestForMainPage.number()));
        }
        if (apartmentRequestForMainPage.house()!=null&&!apartmentRequestForMainPage.house().isEmpty()){
            Join<Apartment, House> houseJoin = root.join("house", JoinType.INNER);
            predicates.add(criteriaBuilder.like(houseJoin.get("name"),"%"+apartmentRequestForMainPage.house()+"%"));
        }
        if (apartmentRequestForMainPage.section()!=null&&!apartmentRequestForMainPage.section().isEmpty()){
            Join<Apartment, Section> sectionJoin = root.join("section", JoinType.INNER);
            predicates.add(criteriaBuilder.like(sectionJoin.get("name"),"%"+apartmentRequestForMainPage.section()+"%"));
        }
        if (apartmentRequestForMainPage.floor()!=null&&!apartmentRequestForMainPage.floor().isEmpty()){
            Join<Apartment, Floor> sectionJoin = root.join("floor", JoinType.INNER);
            predicates.add(criteriaBuilder.like(sectionJoin.get("name"),"%"+apartmentRequestForMainPage.floor()+"%"));
        }
        if (apartmentRequestForMainPage.owner()!=null&&!apartmentRequestForMainPage.owner().isEmpty()){
            String[] ownerFullName = apartmentRequestForMainPage.owner().split(" ");
            Join<Apartment, User> userJoin = root.join("user", JoinType.INNER);
            predicates.add(criteriaBuilder.and(
                    criteriaBuilder.like(userJoin.get("firstname"),"%"+ownerFullName[1]+"%"),
                    criteriaBuilder.like(userJoin.get("lastname"),"%"+ownerFullName[0]+"%"),
                    criteriaBuilder.like(userJoin.get("surname"),"%"+ownerFullName[2]+"%")
            ));
        }
        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(root.get("id")));
        return predicate;


    }
}

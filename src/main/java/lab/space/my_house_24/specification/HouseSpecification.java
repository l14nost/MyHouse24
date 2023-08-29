package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.*;
import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.model.apartment.ApartmentRequestForMainPage;
import lab.space.my_house_24.model.house.HouseRequestForMainPage;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


@Builder
public class HouseSpecification implements Specification<House> {
    private HouseRequestForMainPage houseRequestForMainPage;


    @Override
    public Predicate toPredicate(Root<House> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        List<Predicate> predicates = new ArrayList<>();
        if (houseRequestForMainPage.id()!=null){
            predicates.add(criteriaBuilder.equal(root.get("id"), houseRequestForMainPage.id()));
        }
        if (houseRequestForMainPage.address()!=null&&!houseRequestForMainPage.address().isEmpty()){
            predicates.add(criteriaBuilder.like(root.get("address"), "%"+houseRequestForMainPage.address()+"%"));
        }
        if (houseRequestForMainPage.name()!=null&&!houseRequestForMainPage.name().isEmpty()){
            predicates.add(criteriaBuilder.like(root.get("name"), "%"+houseRequestForMainPage.name()+"%"));
        }
        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(root.get("id")));
        return predicate;


    }
}

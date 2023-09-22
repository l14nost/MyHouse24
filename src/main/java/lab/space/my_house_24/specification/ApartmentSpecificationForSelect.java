package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.*;
import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.model.apartment.ApartmentRequestForMainPage;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Builder
@EqualsAndHashCode
public class ApartmentSpecificationForSelect implements Specification<Apartment> {
    private Long idHouse;
    private Long idSection;
    private Long idFloor;
    private Boolean duty;


    @Override
    public Predicate toPredicate(Root<Apartment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        List<Predicate> predicates = new ArrayList<>();
        if (idHouse!=null&&idHouse>0L){
            Join<Apartment, House> houseJoin = root.join("house", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(houseJoin.get("id"),idHouse));
        }
        if (idFloor!=null&&idFloor>0L){
            Join<Apartment, Floor> floorJoin = root.join("floor", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(floorJoin.get("id"),idFloor));
        }
        if (idSection!=null&&idSection>0L){
            Join<Apartment, Floor> sectionJoin = root.join("section", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(sectionJoin.get("id"),idSection));
        }
        if (duty!=null&& duty) {
            predicates.add(criteriaBuilder.lessThan(root.get("bankBook").get("totalPrice"), BigDecimal.ZERO));
        }
        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        return predicate;


    }
}

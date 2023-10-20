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
public class FloorSpecification implements Specification<Floor> {
    private Long houseId;
    private String name;


    @Override
    public Predicate toPredicate(Root<Floor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        System.out.println(houseId);
        System.out.println(name);
        query.distinct(true);
        List<Predicate> predicates = new ArrayList<>();
        if (houseId!=null){
            predicates.add(criteriaBuilder.equal(root.get("house").get("id"),houseId));
        }
        if (name !=null){
            predicates.add(criteriaBuilder.like(root.get("name"), "%"+name+"%"));
        }
        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.asc(root.get("name")));
        return predicate;
    }
}

package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lab.space.my_house_24.entity.Apartment;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Builder
@EqualsAndHashCode
public class ApartmentSpecificationForBankBook implements Specification<Apartment> {
    private Long houseId;
    private Long sectionId;
    private String search;


    @Override
    public Predicate toPredicate(Root<Apartment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (nonNull(houseId) && houseId > 0L) {
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.equal(root.get("house").get("id"), houseId)
            ));
        }
        if (nonNull(search) && Objects.equals(search, "")) {
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("number").as(String.class), "%" + search + "%")
            ));
        }
        if (nonNull(sectionId) && sectionId > 0L) {
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.equal(root.get("section").get("id"), sectionId)
            ));
        }
        query.orderBy(criteriaBuilder.asc(root.get("number")));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

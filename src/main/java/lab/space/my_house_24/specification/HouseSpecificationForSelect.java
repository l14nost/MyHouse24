package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lab.space.my_house_24.entity.House;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;


@Builder
@EqualsAndHashCode
public class HouseSpecificationForSelect implements Specification<House> {
    private String search;


    @Override
    public Predicate toPredicate(Root<House> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        List<Predicate> predicates = new ArrayList<>();
        if (nonNull(search) && !Objects.equals(search, "")) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"), "%" + search + "%")));
        }
        query.orderBy(criteriaBuilder.asc(root.get("name")));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lab.space.my_house_24.entity.Service;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;


@Builder
public class ServiceSpecificationForSelect implements Specification<Service> {

    private String search;
    private Boolean isActive;

    @Override
    public Predicate toPredicate(Root<Service> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        List<Predicate> predicates = new ArrayList<>();
        if (nonNull(search) && !Objects.equals(search, "")){
            predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + search + "%")));
        }
        if (nonNull(isActive)){
            predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("isActive"), isActive)));
        }
        query.orderBy(criteriaBuilder.asc(root.get("name")));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

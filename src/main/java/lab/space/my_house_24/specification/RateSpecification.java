package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.Predicate;
import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.model.rate.RateRequest;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RateSpecification {
    public Specification<Rate> getRateByRequest(RateRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Rate> getRateForSelect(String search) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(root.get("name"),"%"+search+"%"));
            query.orderBy(criteriaBuilder.asc(root.get("name")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.model.house.HouseRequestForMainPage;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


@Builder
public class HouseSpecificationForSelect implements Specification<House> {
    private String search;


    @Override
    public Predicate toPredicate(Root<House> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = criteriaBuilder.and(criteriaBuilder.like(root.get("name"), "%"+search+"%"));
        return predicate;


    }
}

package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lab.space.my_house_24.entity.User;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;


@Builder
@EqualsAndHashCode
public class UserSpecificationForTable implements Specification<User> {
    private String search;


    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        Predicate predicate;
        if (!search.isEmpty()) {
            predicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get("firstname"), "%"+search+"%"),
                    criteriaBuilder.like(root.get("lastname"), "%"+search+"%"),
                    criteriaBuilder.like(root.get("surname"), "%"+search+"%")
            );
        }
        else {
            predicate = criteriaBuilder.greaterThan(root.get("id"),0);
        }
        query.orderBy(criteriaBuilder.asc(root.get("lastname")));

        query.orderBy(criteriaBuilder.asc(root.get("lastname")));

        return predicate;


    }
}

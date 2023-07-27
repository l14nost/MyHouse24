package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.model.user.UserMainPageRequest;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;


@Builder
public class UserSpecification implements Specification<User> {
    private UserMainPageRequest userMainPageRequest;


    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        Predicate predicate = criteriaBuilder.or(
                criteriaBuilder.isFalse(root.get("duty")),
                criteriaBuilder.isTrue(root.get("duty"))
       );

        return predicate;


    }
}

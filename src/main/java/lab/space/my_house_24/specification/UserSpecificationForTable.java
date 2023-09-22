package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.*;
import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.user.UserMainPageRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


@Builder
@EqualsAndHashCode
public class UserSpecificationForTable implements Specification<User> {
    private String search;


    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        Predicate predicate;
        if (!search.isEmpty()&&search!=null) {
            predicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get("firstname"), search),
                    criteriaBuilder.like(root.get("lastname"), search),
                    criteriaBuilder.like(root.get("surname"), search)
            );
        }
        else {
            predicate = criteriaBuilder.greaterThan(root.get("id"),0);
        }

        return predicate;


    }
}

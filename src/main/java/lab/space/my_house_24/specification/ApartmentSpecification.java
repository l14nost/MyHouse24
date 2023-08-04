package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.model.apartment.ApartmentRequestForMainPage;
import lab.space.my_house_24.model.user.UserMainPageRequest;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;


@Builder
public class ApartmentSpecification implements Specification<Apartment> {
    private ApartmentRequestForMainPage apartmentRequestForMainPage;


    @Override
    public Predicate toPredicate(Root<Apartment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        Predicate predicate = criteriaBuilder.greaterThan(root.get("id"),0);

        return predicate;


    }
}

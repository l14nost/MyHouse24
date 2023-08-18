package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.Predicate;
import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.model.apartment.ApartmentMastersApplicationRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Component
public class ApartmentSpecificationForMasterApplication {
    public Specification<Apartment> getApartmentByUserId(ApartmentMastersApplicationRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nonNull(request.id())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("user").get("id"), request.id())
                ));
            }
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

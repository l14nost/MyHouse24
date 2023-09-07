package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.Predicate;
import lab.space.my_house_24.entity.MeterReading;
import lab.space.my_house_24.model.meterReading.MeterReadingRequestForBill;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Component
public class MeterReadingSpecificationForBill {
    public Specification<MeterReading> getMeterReadingByRequest(MeterReadingRequestForBill request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nonNull(request.apartmentId())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("apartment").get("id"), request.apartmentId())
                ));
            }
            if (nonNull(request.sectionId())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("apartment").get("section").get("id"), request.sectionId())
                ));
            }
            if (nonNull(request.houseId())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("apartment").get("house").get("id"), request.houseId())
                ));
            }
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

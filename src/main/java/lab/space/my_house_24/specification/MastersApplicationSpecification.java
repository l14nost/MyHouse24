package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lab.space.my_house_24.entity.MastersApplication;
import lab.space.my_house_24.model.masters_application.MastersApplicationRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Component
public class MastersApplicationSpecification {

    public Specification<MastersApplication> getMastersApplicationByRequest(MastersApplicationRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nonNull(request.idQuery()) && !Objects.equals(request.idQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("id").as(String.class), "%" + request.idQuery() + "%")
                ));
            }
            if (nonNull(request.dateTime()) && !Objects.equals(request.dateTime(), "")) {
                Expression<String> formattedDateTime = criteriaBuilder.function(
                        "DATE_FORMAT",
                        String.class,
                        root.get("dateTime"),
                        criteriaBuilder.literal("%d.%m.%Y - %H:%i")
                );
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(formattedDateTime, "%" + request.dateTime() + "%")
                ));
            }
            if (nonNull(request.typeMaster())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("master"), request.typeMaster())
                ));
            }
            if (nonNull(request.descriptionQuery()) && !Objects.equals(request.descriptionQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + request.descriptionQuery().toLowerCase() + "%")
                ));
            }
            if (nonNull(request.apartmentQuery()) && !Objects.equals(request.apartmentQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(
                                criteriaBuilder.concat(
                                        criteriaBuilder.concat(
                                                criteriaBuilder.lower(root.get("apartment").get("number").as(String.class)),
                                                ", "
                                        ),
                                        criteriaBuilder.lower(root.get("apartment").get("house").get("name"))
                                ),
                                "%" + request.apartmentQuery().toLowerCase() + "%")
                ));
            }
            if (nonNull(request.ownerIdQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("user").get("id"), request.ownerIdQuery())
                ));
            }
            if (nonNull(request.phoneQuery()) && !Objects.equals(request.phoneQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("user").get("number"), "%" + request.phoneQuery() + "%")
                ));
            }
            if (nonNull(request.staffIdQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("staff").get("id"), request.staffIdQuery())
                ));
            }
            if (nonNull(request.statusQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("mastersApplicationStatus"), request.statusQuery())
                ));
            }
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

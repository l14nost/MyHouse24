package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.Predicate;
import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.model.bankBook.BankBookRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Component
public class BankBookSpecification {
    public Specification<BankBook> getBankBookByRequest(BankBookRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nonNull(request.numberQuery()) && !Objects.equals(request.numberQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("number"), "%" + request.numberQuery() + "%")
                ));
            }

            if (nonNull(request.statusQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("bankBookStatus"), request.statusQuery())
                ));
            }
            if (nonNull(request.apartmentQuery()) && !Objects.equals(request.apartmentQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("apartment").get("number").as(String.class), "%" + request.apartmentQuery() + "%")
                ));
            }
            if (nonNull(request.houseIdQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("apartment").get("house").get("id"), request.houseIdQuery())
                ));
            }
            if (nonNull(request.sectionIdQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("apartment").get("section").get("id"), request.sectionIdQuery())
                ));
            }
            if (nonNull(request.ownerIdQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("apartment").get("user").get("id"), request.ownerIdQuery())
                ));
            }
            if (nonNull(request.balanceQuery())) {

            }
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

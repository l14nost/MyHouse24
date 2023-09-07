package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lab.space.my_house_24.entity.CashBox;
import lab.space.my_house_24.enums.ArticleType;
import lab.space.my_house_24.model.cash_box.CashBoxRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Component
public class CashBoxSpecification {
    public Specification<CashBox> getCashBoxByRequest(CashBoxRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nonNull(request.numberQuery()) && !Objects.equals(request.numberQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("number"), "%" + request.numberQuery() + "%")
                ));
            }
            if (nonNull(request.dateQuery()) && !Objects.equals(request.dateQuery(), "")) {
                Expression<String> formattedDateTime = criteriaBuilder.function(
                        "DATE_FORMAT",
                        String.class,
                        root.get("createAt"),
                        criteriaBuilder.literal("%d.%m.%Y")
                );
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(formattedDateTime, "%" + request.dateQuery() + "%")
                ));
            }
            if (nonNull(request.draftQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("draft"), request.draftQuery())
                ));
            }
            if (nonNull(request.articleIdQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("articles").get("id"), request.articleIdQuery())
                ));
            }
            if (nonNull(request.bankBookNumberQuery()) && !Objects.equals(request.bankBookNumberQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("bankBook").get("number"), "%" + request.bankBookNumberQuery() + "%")
                ));
            }
            if (nonNull(request.ownerIdQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("bankBook").get("apartment").get("user").get("id"), request.ownerIdQuery())
                ));
            }
            if (nonNull(request.typeQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("type"), (request.typeQuery() == ArticleType.INCOME))
                ));
            }
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lab.space.my_house_24.entity.Bill;
import lab.space.my_house_24.model.bill.BillRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.Objects.nonNull;

@Component
public class BillSpecification {
    public Specification<Bill> getBillByRequest(BillRequest request) {
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
            if (nonNull(request.monthQuery()) && !Objects.equals(request.monthQuery(), "")) {
                Expression<String> formattedDateTime = criteriaBuilder.function(
                        "DATE_FORMAT",
                        String.class,
                        root.get("periodOf"),
                        criteriaBuilder.literal("%M, %Y")
                );
                if (LocaleContextHolder.getLocale().toLanguageTag().equals("uk")) {
                    String month = request.monthQuery();
                    String secondPart = "";
                    if (request.monthQuery().contains(",")) {
                        String[] parts = request.monthQuery().split(",");
                        month = parts.length > 0 ? parts[0] : "";
                        secondPart = parts.length > 1 ? "," + parts[1] : ",";
                    }
                    Map<String, String> translations = new HashMap<>() {
                        {
                            put("січня", "january");
                            put("лютого", "february");
                            put("березня", "march");
                            put("квітня", "april");
                            put("травня", "may");
                            put("червеня", "june");
                            put("липня", "july");
                            put("серпня", "august");
                            put("вересня", "september");
                            put("жовтня", "october");
                            put("листопада", "november");
                            put("грудня", "december");
                        }
                    };

                    int i = 0;
                    for (Map.Entry<String, String> entry : translations.entrySet()) {
                        if (entry.getKey().contains(month.toLowerCase())) {
                            predicates.add(criteriaBuilder.or(
                                    criteriaBuilder.like(formattedDateTime, "%" + entry.getValue() + secondPart + "%")
                            ));
                            i++;
                        } else if (entry.getValue().contains(month.toLowerCase())) {
                            i--;
                        }
                    }
                    if (i == 0) {
                        predicates.add(criteriaBuilder.or(
                                criteriaBuilder.like(formattedDateTime, "%" + month + secondPart + "%")
                        ));
                    } else if (i < 0) {
                        predicates.add(criteriaBuilder.or(
                                criteriaBuilder.equal(formattedDateTime, "%" + i + "%")
                        ));
                    }

                } else {
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.like(formattedDateTime, "%" + request.monthQuery().toUpperCase() + "%")
                    ));
                }
            }
            if (nonNull(request.apartmentQuery()) && !Objects.equals(request.apartmentQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(
                                criteriaBuilder.concat(
                                        criteriaBuilder.concat(
                                                criteriaBuilder.lower(root.get("bankBook").get("apartment").get("number").as(String.class)),
                                                ", "
                                        ),
                                        criteriaBuilder.lower(root.get("bankBook").get("apartment").get("house").get("name"))
                                ),
                                "%" + request.apartmentQuery().toLowerCase() + "%")
                ));
            }
            if (nonNull(request.ownerIdQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("bankBook").get("apartment").get("user").get("id"), request.ownerIdQuery())
                ));
            }
            if (nonNull(request.statusQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("status"), request.statusQuery())
                ));
            }
            if (nonNull(request.draftQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("draft"), request.draftQuery())
                ));
            }
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

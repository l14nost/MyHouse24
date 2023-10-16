package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.Predicate;
import lab.space.my_house_24.entity.Section;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Controller
public class SectionSpecification {
    public Specification<Section> getSection(Long houseId, String name) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nonNull(houseId)) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("house").get("id"), houseId)
                ));
            }
            if (nonNull(name) && !Objects.equals(name, "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("name"), "%" + name + "%")
                ));
            }

            query.orderBy(criteriaBuilder.asc(root.get("name")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

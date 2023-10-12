package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.Predicate;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.staff.StaffMasterRequest;
import lab.space.my_house_24.model.staff.StaffRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Component
public class StaffSpecification {
    public Specification<Staff> getStaffByRequest(StaffRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nonNull(request.idQuery()) && !Objects.equals(request.idQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("id").as(String.class), "%" + request.idQuery() + "%")
                ));
            }
            if (nonNull(request.staffQuery()) && !Objects.equals(request.staffQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(
                                criteriaBuilder.concat(
                                        criteriaBuilder.concat(
                                                criteriaBuilder.lower(root.get("firstname")),
                                                " "
                                        ),
                                        criteriaBuilder.lower(root.get("lastname"))
                                ),
                                "%" + request.staffQuery().toLowerCase() + "%")
                ));
            }
            if (nonNull(request.jobTitleQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("role").get("jobTitle"), request.jobTitleQuery())
                ));
            }
            if (nonNull(request.phoneQuery()) && !Objects.equals(request.phoneQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + request.phoneQuery().toLowerCase() + "%")
                ));
            }
            if (nonNull(request.emailQuery()) && !Objects.equals(request.emailQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + request.emailQuery().toLowerCase() + "%")
                ));
            }
            if (nonNull(request.statusQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("staffStatus"), request.statusQuery())
                ));
            }
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public Specification<Staff> getStaffMaster(StaffMasterRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nonNull(request.role())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("role").get("jobTitle"), request.role())
                ));
            } else {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("role").get("jobTitle"), JobTitle.ELECTRIC),
                        criteriaBuilder.equal(root.get("role").get("jobTitle"), JobTitle.PLUMBER)
                ));
            }
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.equal(root.get("staffStatus"), UserStatus.ACTIVE)
            ));
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Staff> getStaffManager(String fullName) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("role").get("jobTitle"), JobTitle.DIRECTOR),
                        criteriaBuilder.equal(root.get("role").get("jobTitle"), JobTitle.ACCOUNTANT),
                        criteriaBuilder.equal(root.get("role").get("jobTitle"), JobTitle.MANAGER)
                ));
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.equal(root.get("staffStatus"), UserStatus.ACTIVE)
            ));
            if (nonNull(fullName) && !Objects.equals(fullName, "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(
                                criteriaBuilder.concat(
                                        criteriaBuilder.concat(
                                                criteriaBuilder.lower(root.get("firstname")),
                                                " "
                                        ),
                                        criteriaBuilder.lower(root.get("lastname"))
                                ),
                                "%" + fullName.toLowerCase() + "%")
                ));
            }
            query.orderBy(criteriaBuilder.asc(root.get("lastname")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

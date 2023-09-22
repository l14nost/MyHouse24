package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.*;
import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.model.apartment.ApartmentRequestForMainPage;
import lab.space.my_house_24.model.meterReading.MeterReadingRequestForMainPage;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Builder
@EqualsAndHashCode
public class MeterReadingSpecification implements Specification<MeterReading> {
    private MeterReadingRequestForMainPage meterReadingRequestForMainPage;


    @Override
    public Predicate toPredicate(Root<MeterReading> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        List<Predicate> predicates = new ArrayList<>();

        if (meterReadingRequestForMainPage.house()!=null){
            Join<MeterReading, Apartment> apartmentJoin = root.join("apartment", JoinType.INNER);
            Join<Apartment, House> houseJoin = apartmentJoin.join("house", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(houseJoin.get("id"),meterReadingRequestForMainPage.house()));
        }
        if (meterReadingRequestForMainPage.section()!=null){
            Join<MeterReading, Apartment> apartmentJoin = root.join("apartment", JoinType.INNER);
            Join<Apartment, Section> sectionJoin = apartmentJoin.join("section", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(sectionJoin.get("id"),meterReadingRequestForMainPage.section()));
        }
        if (meterReadingRequestForMainPage.apartment()!=null){
            Join<MeterReading, Apartment> apartmentJoin = root.join("apartment", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(apartmentJoin.get("number"),meterReadingRequestForMainPage.apartment()));
        }
        if (meterReadingRequestForMainPage.service()!=null){
            Join<MeterReading, Service> serviceJoin = root.join("service", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(serviceJoin.get("id"),meterReadingRequestForMainPage.service()));
        }

        Subquery<Long> subquery = query.subquery(Long.class);
        Root<MeterReading> subqueryRoot = subquery.from(MeterReading.class);

        subquery.select(criteriaBuilder.max(subqueryRoot.get("id")))
                .groupBy(subqueryRoot.get("apartment").get("id"), subqueryRoot.get("service").get("id"));

        CriteriaBuilder.In<Long> inExpression = criteriaBuilder.in(root.get("id"));
        inExpression.value(subquery);

        predicates.add(inExpression);

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(root.get("id")));

        return predicate;


    }
}

package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.*;
import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.enums.MeterReadingStatus;
import lab.space.my_house_24.model.meterReading.MeterReadingRequestForApartmentPage;
import lab.space.my_house_24.model.meterReading.MeterReadingRequestForMainPage;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Builder
public class MeterReadingApartmentSpecification implements Specification<MeterReading> {
    private MeterReadingRequestForApartmentPage meterReadingRequestForApartmentPage;



    @Override
    public Predicate toPredicate(Root<MeterReading> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        List<Predicate> predicates = new ArrayList<>();
        if (meterReadingRequestForApartmentPage.idService()!=0){
            predicates.add(criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("apartment").get("id"), meterReadingRequestForApartmentPage.idApartment()),
                    criteriaBuilder.equal(root.get("service").get("id"), meterReadingRequestForApartmentPage.idService())
            ));
        }
        else {
            predicates.add(
                    criteriaBuilder.equal(root.get("apartment").get("id"), meterReadingRequestForApartmentPage.idApartment())
            );
        }

        if (meterReadingRequestForApartmentPage.id()!=null){
            predicates.add(criteriaBuilder.equal(root.get("id"), meterReadingRequestForApartmentPage.id()));
        }
        if (meterReadingRequestForApartmentPage.status()!=null && !meterReadingRequestForApartmentPage.status().isEmpty()){
            predicates.add(criteriaBuilder.equal(root.get("status"), MeterReadingStatus.valueOf(meterReadingRequestForApartmentPage.status())));
        }
        if (meterReadingRequestForApartmentPage.date()!=null&&!meterReadingRequestForApartmentPage.date().isEmpty()){
            String startDate = meterReadingRequestForApartmentPage.date().substring(0,10);
            String endDate = meterReadingRequestForApartmentPage.date().substring(14,24);
            String[] startSplit = startDate.split("-");
            String[] endSplit = endDate.split("-");
            LocalDate start = LocalDate.of(Integer.parseInt(startSplit[0]),Integer.parseInt(startSplit[1]),Integer.parseInt(startSplit[2]));
            LocalDate end = LocalDate.of(Integer.parseInt(endSplit[0]),Integer.parseInt(endSplit[1]),Integer.parseInt(endSplit[2]));
            predicates.add(criteriaBuilder.between(root.get("date"),start,end));
        }
        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(root.get("id")));

        return predicate;


    }
}

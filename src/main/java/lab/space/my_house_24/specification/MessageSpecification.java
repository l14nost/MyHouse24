package lab.space.my_house_24.specification;

import jakarta.persistence.criteria.*;
import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.model.apartment.ApartmentRequestForMainPage;
import lab.space.my_house_24.model.message.MessageMainPageRequest;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


@Builder
public class MessageSpecification implements Specification<Message> {
    private MessageMainPageRequest mainPageRequest;


    @Override
    public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        Predicate predicate = criteriaBuilder.and(criteriaBuilder.like(root.get("title"), "%"+mainPageRequest.keyWord()+"%"));
        query.orderBy(criteriaBuilder.desc(root.get("id")));
        return predicate;


    }
}

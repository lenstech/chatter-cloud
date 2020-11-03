package com.lens.chatter.repository.specifications;

import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.model.entity.Product;
import com.lens.chatter.model.other.SearchCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.lens.chatter.constant.GeneralConstants.DTO_DATE_TIME_FORMAT;

/**
 * Created by Emir Gökdemir
 * on 1 May 2020
 */
@NoArgsConstructor
@Getter
public class ProductSpecification implements Specification<Product> {

    private List<SearchCriteria> list = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    public void addAll(List<SearchCriteria> criteria) {
        list.addAll(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicates = new ArrayList<>();

        if (list.isEmpty()) {
            throw new BadRequestException(ErrorConstants.SEARCH_PARAMETER_NOT_FOUND);
        }
        for (SearchCriteria criteria : list) {

            switch (criteria.getOperation()) {
                case MATCH:
                    predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case MATCH_IN_CATEGORY:
                    //Specific Case
                    predicates.add(builder.like(builder.lower(root.get(criteria.getKey()).get("name")), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case EQUAL:
                    predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case FROM:
                    predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), ZonedDateTime.parse(criteria.getValue().toString())));
                    break;
                case TO:
                    predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), ZonedDateTime.parse(criteria.getValue().toString())));
                    break;
                case GREATER_THAN:
                    predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case LESS_THAN:
                    predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case GREATER_THAN_EQUAL:
                    predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case LESS_THAN_EQUAL:
                    predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case NOT_EQUAL:
                    predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case MATCH_END:
                    predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case MATCH_START:
                    predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase()));
                    break;
                case IN:
                    predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
                    break;
                case NOT_IN:
                    predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
                    break;
                case BETWEEN_TIME:
                    SimpleDateFormat formatter = new SimpleDateFormat(DTO_DATE_TIME_FORMAT);
                    try {
                        ZonedDateTime startTime = ZonedDateTime.ofInstant(formatter.parse(criteria.getStartTime()).toInstant(), ZoneId.systemDefault());
                        ZonedDateTime endTime = ZonedDateTime.ofInstant(formatter.parse(criteria.getEndTime()).toInstant(), ZoneId.systemDefault());
                        predicates.add(builder.between(root.get(criteria.getKey()), startTime, endTime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        throw new BadRequestException(ErrorConstants.DATE_FORMAT_IS_NOT_CORRECT);
                    }
                    break;
                default:
                    break;
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}

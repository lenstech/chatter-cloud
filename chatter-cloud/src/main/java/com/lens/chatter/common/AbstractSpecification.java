package com.lens.chatter.common;


import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.constant.GeneralConstants;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.model.other.SearchCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Emir Gökdemir
 * on 1 May 2020
 */
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractSpecification<T> implements Specification<T> {

    private List<SearchCriteria> list = new ArrayList<>();
    private com.lens.chatter.enums.Predicate andOr = com.lens.chatter.enums.Predicate.AND;
    private String specificObject;

    public void add(SearchCriteria criteria) {
        if (criteria.getValue() == null && criteria.getStart() == null && criteria.getEnd() == null) {
            return;
        }
        list.add(criteria);
    }

    public void addAll(List<SearchCriteria> criterias) {
        if (!CollectionUtils.isEmpty(criterias)) {
            for (SearchCriteria criteria : criterias) {
                add(criteria);
            }
        }
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> restrictions = new ArrayList<>();

        if (list.isEmpty()) {
            throw new BadRequestException(ErrorConstants.SEARCH_PARAMETER_NOT_FOUND);
        }
        for (SearchCriteria criteria : list) {
            switch (criteria.getOperation()) {
                case MATCH:
                    restrictions.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case MATCH_FOR_SPECIFIC:
                    restrictions.add(builder.like(builder.lower(root.get(specificObject).get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case MATCH_DEFECT_TYPE_KEY:
                    restrictions.add(builder.like(builder.lower(root.get("defectType").get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case MATCH_PRODUCT_TYPE_KEY:
                    restrictions.add(builder.like(builder.lower(root.get("productType").get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case MATCH_DEFECTS_DEFECT_TYPE_KEY:
                    restrictions.add(builder.like(builder.lower(root.join("defects", JoinType.LEFT).get("defectType").get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case EQUAL_DEFECTS_DEFECT_TYPE_KEY:
                    restrictions.add(builder.equal(root.join("defects", JoinType.LEFT).get("defectType").get(criteria.getKey()),  criteria.getValue()));
                    break;
                case MATCH_DEFECT_KEY:
                    restrictions.add(builder.like(builder.lower(root.get("defect").get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case EQUAL:
                    restrictions.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case EQUAL_FOR_SPECIFIC:
                    restrictions.add(builder.equal(root.get(specificObject).get(criteria.getKey()), criteria.getValue()));
                    break;
                case FROM:
                    restrictions.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), ZonedDateTime.parse(criteria.getValue().toString())));
                    break;
                case TO:
                    restrictions.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), ZonedDateTime.parse(criteria.getValue().toString())));
                    break;
                case GREATER_THAN:
                    restrictions.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case LESS_THAN:
                    restrictions.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case GREATER_THAN_EQUAL:
                    restrictions.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case LESS_THAN_EQUAL:
                    restrictions.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case NOT_EQUAL:
                    restrictions.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case MATCH_END:
                    restrictions.add(builder.like(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case MATCH_START:
                    restrictions.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase()));
                    break;
                case IN:
                    restrictions.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
                    break;
                case NOT_IN:
                    restrictions.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
                    break;
                case BETWEEN_AREA:
                    if (criteria.getStart() == null) {
                        criteria.setStart("0");
                    }
                    if (criteria.getEnd() == null) {
                        criteria.setEnd("1000");
                    }
                    restrictions.add(builder.between(root.get(criteria.getKey()), Double.valueOf(criteria.getStart()), Double.valueOf(criteria.getEnd())));
                    break;
                case BETWEEN_AREA_DEFECTS:
                    if (criteria.getStart() == null) {
                        criteria.setStart("0");
                    }
                    if (criteria.getEnd() == null) {
                        criteria.setEnd("1000");
                    }
                    restrictions.add(builder.between(root.join("defects").get(criteria.getKey()), Double.valueOf(criteria.getStart()), Double.valueOf(criteria.getEnd())));
                    break;
                case BETWEEN_TIME:
                    if (criteria.getStart() == null) {
                        criteria.setStart("1970-01-01 00:00:00");
                    }
                    if (criteria.getEnd() == null) {
                        criteria.setEnd("2100-01-01 00:00:00");
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat(GeneralConstants.DTO_DATE_TIME_FORMAT);
                    try {
                        ZonedDateTime startTime = ZonedDateTime.ofInstant(formatter.parse(criteria.getStart()).toInstant(), ZoneId.systemDefault());
                        ZonedDateTime endTime = ZonedDateTime.ofInstant(formatter.parse(criteria.getEnd()).toInstant(), ZoneId.systemDefault());
                        restrictions.add(builder.between(root.get(criteria.getKey()), startTime, endTime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        throw new BadRequestException(ErrorConstants.DATE_FORMAT_IS_NOT_CORRECT);
                    }
                    break;
                default:
                    break;
            }
        }

        if (andOr == com.lens.chatter.enums.Predicate.AND) {
            return builder.and(restrictions.toArray(new Predicate[0]));
        } else {
            return builder.or(restrictions.toArray(new Predicate[0]));
        }
    }
}

package com.lens.chatter.repository.specifications;

import com.lens.chatter.common.AbstractSpecification;
import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.model.entity.Defect;
import com.lens.chatter.model.entity.Product;
import com.lens.chatter.model.other.SearchCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
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
public class ProductSpecification extends AbstractSpecification<Product> implements Specification<Product> {

//    @Override
//    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//
//        List<Predicate> restrictions = new ArrayList<>();
//
//        if (list.isEmpty()) {
//            throw new BadRequestException(ErrorConstants.SEARCH_PARAMETER_NOT_FOUND);
//        }
//        for (SearchCriteria criteria : list) {
//
//            switch (criteria.getOperation()) {
//                case MATCH:
//                    restrictions.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
//                    break;
//                case MATCH_KEYS_NAME:
//                    //Specific Case
//                    restrictions.add(builder.like(builder.lower(root.get(criteria.getKey()).get("name")), "%" + criteria.getValue().toString().toLowerCase() + "%"));
//                    break;
//                case MATCH_DEFECT_TYPES_KEY: {
//                    //Specific Case
//                    Join<Product, Defect> join = root.join("defects");
//                    restrictions.add(builder.like(join.get("defectType").get(criteria.getKey()), "%" + criteria.getValue().toString() + "%"));
//                    break;
//                }
//                case MATCH_PRODUCT_TYPES_KEY: {
//                    //Specific Case
//                    restrictions.add(builder.like(root.get("productType").get(criteria.getKey()), "%" + criteria.getValue().toString() + "%"));
//                    break;
//                }
//                case EQUAL:
//                    restrictions.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
//                    break;
//                case FROM:
//                    restrictions.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), ZonedDateTime.parse(criteria.getValue().toString())));
//                    break;
//                case TO:
//                    restrictions.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), ZonedDateTime.parse(criteria.getValue().toString())));
//                    break;
//                case GREATER_THAN:
//                    restrictions.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
//                    break;
//                case LESS_THAN:
//                    restrictions.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
//                    break;
//                case GREATER_THAN_EQUAL:
//                    restrictions.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
//                    break;
//                case LESS_THAN_EQUAL:
//                    restrictions.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
//                    break;
//                case NOT_EQUAL:
//                    restrictions.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
//                    break;
//                case MATCH_END:
//                    restrictions.add(builder.like(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase() + "%"));
//                    break;
//                case MATCH_START:
//                    restrictions.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase()));
//                    break;
//                case IN:
//                    restrictions.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
//                    break;
//                case NOT_IN:
//                    restrictions.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
//                    break;
//                case BETWEEN_TIME:
//                    SimpleDateFormat formatter = new SimpleDateFormat(DTO_DATE_TIME_FORMAT);
//                    try {
//                        ZonedDateTime startTime = ZonedDateTime.ofInstant(formatter.parse(criteria.getStartTime()).toInstant(), ZoneId.systemDefault());
//                        ZonedDateTime endTime = ZonedDateTime.ofInstant(formatter.parse(criteria.getEndTime()).toInstant(), ZoneId.systemDefault());
//                        restrictions.add(builder.between(root.get(criteria.getKey()), startTime, endTime));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                        throw new BadRequestException(ErrorConstants.DATE_FORMAT_IS_NOT_CORRECT);
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        return builder.and(restrictions.toArray(new Predicate[0]));
//    }
}

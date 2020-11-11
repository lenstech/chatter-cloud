package com.lens.chatter.service;

import com.lens.chatter.enums.StatisticsTime;
import com.lens.chatter.model.dto.product.SearchDto;
import com.lens.chatter.model.other.DefectTypeCount;
import com.lens.chatter.model.other.ProductTypeCount;
import com.lens.chatter.model.other.SearchCriteria;
import com.lens.chatter.repository.DefectRepository;
import com.lens.chatter.repository.ProductRepository;
import com.lens.chatter.repository.specifications.ProductSpecification;
import com.lens.chatter.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.lens.chatter.constant.GeneralConstants.PAGE_SIZE;

/**
 * Created by Emir Gökdemir
 * on 10 Kas 2020
 */

@Service
public class DashboardService {

    @Autowired
    private FilterService filterService;

    @Autowired
    private ProductRepository productRepository;
    //daily production

    @Autowired
    private DefectRepository defectRepository;

    public void getTimeFilteredProductionStatistics(int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo, PAGE_SIZE);
        SearchCriteria criteria = new SearchCriteria();
        List<SearchCriteria> criterias = new ArrayList<>();
        SearchDto searchDto = new SearchDto();
        ProductSpecification spec = new ProductSpecification();
        spec.addAll(searchDto.getFilterCriteria());
    }

    public List<DefectTypeCount> getTimeFilteredDefectTypeStatistics(StatisticsTime time) {
        Instant begin = findTheTime(time);
        float total = defectRepository.countDefectsByCreatedDateBetween(ZonedDateTime.ofInstant(begin, ZoneId.systemDefault()), ZonedDateTime.now());
        return defectRepository.getStatisticsByDefectType(ZonedDateTime.ofInstant(begin, ZoneId.systemDefault()), ZonedDateTime.now(), total == 0L ? 1 : total);
    }

    public List<ProductTypeCount> getTimeFilteredProductTypeStatistics(StatisticsTime time) {
        Instant begin = findTheTime(time);
        float total = productRepository.countProductsByCreatedDateBetween(ZonedDateTime.ofInstant(begin, ZoneId.systemDefault()), ZonedDateTime.now());
        return productRepository.getStatisticsByProductType(ZonedDateTime.ofInstant(begin, ZoneId.systemDefault()), ZonedDateTime.now(), total == 0L ? 1 : total);
    }

    private Instant findTheTime(StatisticsTime time) {
        switch (time) {
            case DAILY:
                return DateUtils.getTheBeginningOfDay();
            case WEEKLY:
                return DateUtils.getTheBeginningOfWeek();
            case MONTHLY:
                return DateUtils.getTheBeginningOfMonth();
            default:
                return Instant.now();
        }
    }
}

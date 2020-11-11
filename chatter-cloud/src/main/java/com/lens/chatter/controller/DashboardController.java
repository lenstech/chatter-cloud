package com.lens.chatter.controller;

import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.enums.Role;
import com.lens.chatter.enums.StatisticsTime;
import com.lens.chatter.model.other.DefectTypeCount;
import com.lens.chatter.model.other.ProductTypeCount;
import com.lens.chatter.service.DashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Emir Gökdemir
 * on 9 Kas 2020
 */

@RestController
@RequestMapping("/dashboard")
@Api(value = "Dashboard", tags = {"Dashboard Operations"})
public class DashboardController {

    @Autowired
    private DashboardService service;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @ApiOperation(value = "Get Time Filtered Defect Type Statistics, it can be seen by basic user.", response = DefectTypeCount.class, responseContainer = "List")
    @GetMapping("/defect-type")
    public ResponseEntity<List<DefectTypeCount>> getTimeFilteredDefectTypeStatistics(@RequestHeader("Authorization") String token,
                                                                        @RequestParam StatisticsTime statisticsTime) {
        logger.info(String.format("Requesting getDepartmentsOfBranch statisticsTime: %s.", statisticsTime));
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(service.getTimeFilteredDefectTypeStatistics(statisticsTime));
    }
    
    @ApiOperation(value = "Get Time Filtered Product Type Statistics, it can be seen by basic user.", response = ProductTypeCount.class, responseContainer = "List")
    @GetMapping("/product-type")
    public ResponseEntity<List<ProductTypeCount>> getTimeFilteredProductTypeStatistics(@RequestHeader("Authorization") String token,
                                                                                   @RequestParam StatisticsTime statisticsTime) {
        logger.info(String.format("Requesting getDepartmentsOfBranch statisticsTime: %s.", statisticsTime));
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(service.getTimeFilteredProductTypeStatistics(statisticsTime));
    }

    // todo:  defect-location oranları

}

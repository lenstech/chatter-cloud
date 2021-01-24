package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.enums.Role;
import com.lens.chatter.model.dto.product.FilterDto;
import com.lens.chatter.model.dto.product.SearchDto;
import com.lens.chatter.model.entity.Filter;
import com.lens.chatter.model.resource.product.FilterResource;
import com.lens.chatter.model.resource.product.ProductResource;
import com.lens.chatter.security.JwtResolver;
import com.lens.chatter.service.FilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.lens.chatter.constant.GeneralConstants.DTO_DATE_TIME_FORMAT;

/**
 * Created by Emir Gökdemir
 * on 9 Nis 2020
 */
@RestController
@RequestMapping("/filter")
@Api(value = "Filter", tags = {"Filter Operations"})
public class FilterController extends AbstractController<Filter, UUID, FilterDto, FilterResource> {

    @Autowired
    private FilterService service;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    @Autowired
    private JwtResolver jwtResolver;

    @Override
    protected AbstractService<Filter, UUID, FilterDto, FilterResource> getService() {
        return service;
    }

    @Override
    public Role getSaveRole() {
        return Role.BASIC_USER;
    }

    @Override
    public Role getGetRole() {
        return Role.BASIC_USER;
    }

    @Override
    public Role getGetAllRole() {
        return Role.BASIC_USER;
    }

    @Override
    public Role getUpdateRole() {
        return Role.BASIC_USER;
    }

    @Override
    public Role getDeleteRole() {
        return Role.BASIC_USER;
    }

    @ApiOperation(value = "Search for Product, time format should be " + DTO_DATE_TIME_FORMAT + "and operation should be:" +
            "   BETWEEN_AREA,\n" +
            "    BETWEEN_AREA_DEFECTS,\n" +
            "    BETWEEN_TIME,\n" +
            "    EQUAL,\n" +
            "    EQUAL_DEFECTS_DEFECT_TYPE_KEY,\n" +
            "    EQUAL_FOR_SPECIFIC,\n" +
            "    FROM,\n" +
            "    GREATER_THAN,\n" +
            "    GREATER_THAN_EQUAL,\n" +
            "    IN,\n" +
            "    LESS_THAN,\n" +
            "    LESS_THAN_EQUAL,\n" +
            "    MATCH,\n" +
            "    MATCH_DEFECTS_DEFECT_TYPE_KEY,\n" +
            "    MATCH_DEFECT_KEY,\n" +
            "    MATCH_DEFECT_TYPE_KEY,\n" +
            "    MATCH_END,\n" +
            "    MATCH_FOR_SPECIFIC,\n" +
            "    MATCH_PRODUCT_TYPE_KEY,\n" +
            "    MATCH_START,\n" +
            "    NOT_EQUAL,\n" +
            "    NOT_IN,\n" +
            "    TO", responseContainer = "List")
    @PostMapping("/search/{pageNo}")
    public ResponseEntity<Page<ProductResource>> searchProducts(@RequestHeader("Authorization") String token,
                                                                @PathVariable int pageNo,
                                                                @RequestBody @Valid SearchDto dto) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(service.search(dto, pageNo));
    }

    @ApiOperation(value = "Search for Product, time format should be " + DTO_DATE_TIME_FORMAT, responseContainer = "List")
    @PostMapping("/search-by-saved-filter/{pageNo}")
    public ResponseEntity<Page<ProductResource>> searchProductsBySavedFilter(@RequestHeader("Authorization") String token,
                                                                             @PathVariable int pageNo,
                                                                             @RequestParam UUID filterId) {
        UUID userId = authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(service.searchBySavedFilter(filterId, userId, pageNo));
    }
}

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
    public void setSaveRole() {
        super.saveRole = Role.BASIC_USER;
    }

    @Override
    public void setGetRole() {
        super.getRole = Role.BASIC_USER;
    }

    @Override
    public void setGetAllRole() {
        super.getAllRole = Role.BASIC_USER;
    }

    @Override
    public void setUpdateRole() {
        super.updateRole = Role.BASIC_USER;
    }

    @Override
    public void setDeleteRole() {
        super.deleteRole = Role.BASIC_USER;
    }

    @Override
    public void setEntityName() {
        super.entityName = "Filter";
    }

    @ApiOperation(value = "Search for Product, time format should be " + DTO_DATE_TIME_FORMAT, responseContainer = "List")
    @PostMapping("/search/{pageNo}")
    public ResponseEntity<Page<ProductResource>> searchProducts(@RequestHeader("Authorization") String token,
                                                                @PathVariable int pageNo,
                                                                @RequestBody @Valid SearchDto dto) {
        authorizationConfig.permissionCheck(token,Role.BASIC_USER);
        return ResponseEntity.ok(service.search(dto, pageNo));
    }

    @ApiOperation(value = "Search for Product, time format should be " + DTO_DATE_TIME_FORMAT, responseContainer = "List")
    @PostMapping("/search-by-saved-filter/{pageNo}")
    public ResponseEntity<Page<ProductResource>> searchProductsBySavedFilter(@RequestHeader("Authorization") String token,
                                                                @PathVariable int pageNo,
                                                                @RequestParam UUID filterId) {

        return ResponseEntity.ok(service.searchBySavedFilter(filterId, jwtResolver.getIdFromToken(token), pageNo));
    }
}

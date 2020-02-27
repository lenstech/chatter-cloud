package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.model.dto.DepartmentDto;
import com.lens.chatter.model.entity.Department;
import com.lens.chatter.model.resource.DepartmentResource;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 23 Şub 2020
 */
@Service
public class DepartmentService extends AbstractService<Department,UUID,DepartmentDto,DepartmentResource> {

}

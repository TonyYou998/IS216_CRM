package com.uit.crm.role.service.impl;

import com.uit.crm.common.utils.LoggerUtil;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.role.dto.RoleDto;
import com.uit.crm.role.model.Role;
import com.uit.crm.role.repository.RoleRepository;
import com.uit.crm.role.service.RoleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    ModelMapper mapper;
    @Override
    public List<RoleDto> getAllRoles() {
        try {
            List<Role> lstRole= SpringBeanUtil.getBean(RoleRepository.class).findAll();
            List<RoleDto> lstDto=new LinkedList<>();
            for(Role r:lstRole){
                RoleDto roleDto=mapper.map(r, RoleDto.class);
                lstDto.add(roleDto);

            }
            return lstDto;
        }
        catch (Exception ex){
            SpringBeanUtil.getBean(LoggerUtil.class).logger(RoleServiceImpl.class).info(ex.getMessage());
            return null;
        }
    }
}

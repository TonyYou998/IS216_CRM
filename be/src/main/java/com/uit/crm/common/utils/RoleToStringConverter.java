package com.uit.crm.common.utils;

import com.uit.crm.role.model.Role;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class RoleToStringConverter implements Converter<Role,String> {


    @Override
    public String convert(MappingContext<Role, String> context) {
        Role role=context.getSource();
        return role.getId().toString();
    }
}

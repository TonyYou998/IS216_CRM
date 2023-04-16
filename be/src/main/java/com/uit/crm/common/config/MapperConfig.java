package com.uit.crm.common.config;

import com.uit.crm.common.utils.RoleToStringConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
        @Bean
        public ModelMapper modelMapper(){
                ModelMapper modelMapper=new ModelMapper();
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//                modelMapper.addConverter(new RoleToStringConverter());
                return modelMapper;
        }
}

package com.uit.crm.common.helper;

import com.uit.crm.project.dto.ProjectEmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static  <T> ResponseEntity<Object> getResponse(T content, HttpStatus status){
        Map<String,Object> map=new HashMap<>();
        map.put("content",content);
        map.put("status", status.value());
        return new ResponseEntity<>( map,status);

    }

    public static <T> ResponseEntity<Object> getResponse(String error,HttpStatus status){
        Map<String,Object> map=new HashMap<>();
        map.put("error",error);
        map.put("status",status);
        return new ResponseEntity<>(map,status);
    }

//    public static <T>ResponseEntity<ProjectEmployeeDto> getResponse(Class<T> tClass) {
//        if(tClass!=null){
//            getResponse(tClass,)
//        }
//
//    }
}

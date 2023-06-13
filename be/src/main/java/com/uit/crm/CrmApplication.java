package com.uit.crm;

import com.uit.crm.task.model.Task;
import com.uit.crm.task.service.impl.TaskServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class CrmApplication {

    public static void main(String[] args) {


        ApplicationContext context= SpringApplication.run(CrmApplication.class, args);



    }

}

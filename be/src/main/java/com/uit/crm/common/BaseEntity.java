package com.uit.crm.common;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,updatable = false)
    protected Long id;
    @DateTimeFormat
    @CreatedDate
    LocalDateTime createAt;
    @DateTimeFormat
    @LastModifiedDate
    LocalDateTime updateAt;
}

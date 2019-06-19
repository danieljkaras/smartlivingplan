package com.danielkaras.smartlivingplan.model;

import com.danielkaras.smartlivingplan.control.LocalDateTimeAttributeConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@JsonIgnoreProperties(
        value = {"createTime", "lastModifiedTime"},
        allowGetters = true
)
public abstract class AuditModel implements Serializable {

    @Column(name = "createtime", nullable = false, updatable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @CreatedDate
    private LocalDateTime createTime;

    @Column(name = "lastmodifiedtime", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @LastModifiedDate
    private LocalDateTime lastModifiedTime;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public String toString() {
        return "AuditModel{" +
                "createTime=" + createTime +
                ", lastModifiedTime=" + lastModifiedTime +
                '}';
    }
}

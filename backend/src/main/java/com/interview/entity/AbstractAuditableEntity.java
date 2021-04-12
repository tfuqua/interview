package com.interview.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractAuditableEntity {
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @Version
    @Column(name = "version")
    private Long version;

    protected String getCreatedBy() {
        return createdBy;
    }

    protected void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    protected Date getCreatedDate() {
        return createdDate;
    }

    protected void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    protected String getLastModifiedBy() {
        return lastModifiedBy;
    }

    protected void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    protected Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    protected void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    protected Long getVersion() {
        return version;
    }

    protected void setVersion(Long version) {
        this.version = version;
    }
}
package com.khmersolution.moduler.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Vannaravuth Yo
 * Date : 8/23/2017, 10:13 AM
 * Email : ravuthz@gmail.com
 */

@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    private Date dateCreated;
    private Date lastUpdated;

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastUpdated = new Date();
        if (dateCreated == null) {
            dateCreated = new Date();
        }
    }
}

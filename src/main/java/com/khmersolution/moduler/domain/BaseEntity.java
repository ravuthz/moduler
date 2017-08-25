package com.khmersolution.moduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
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
    @JsonIgnore
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated entity ID")
    private Long id;

    @Version
    @JsonIgnore
    @ApiModelProperty(notes = "The auto-generated version of the entity")
    private Integer version;

    @JsonIgnore
    @ApiModelProperty(notes = "The auto-generated date of the entity creation")
    private Date dateCreated;

    @JsonIgnore
    @ApiModelProperty(notes = "The auto-generated date of the entity modification")
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

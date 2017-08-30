package com.khmersolution.moduler.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Vannaravuth Yo
 * Date : 8/30/2017, 3:44 PM
 * Email : ravuthz@gmail.com
 */

@Data
@Entity
@Table(name = "permissions")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Permission")
public class Permission extends BaseEntity {

    @NotEmpty
    @ApiModelProperty(notes = "Permission's name")
    private String name;

    @ApiModelProperty(notes = "Permission's note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    public Permission(String name) {
        this.name = name;
    }

    public Permission(String name, String note) {
        this.name = name;
        this.note = note;
    }

}

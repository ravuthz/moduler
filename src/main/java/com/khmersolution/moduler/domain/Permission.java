package com.khmersolution.moduler.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7085459662547835139L;

    @NotEmpty
    @ApiModelProperty(notes = "Permission's name")
    private String name;

    @ApiModelProperty(notes = "Permission's note")
    private String note;

    @ApiModelProperty(notes = "Permission's role")
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    public Permission(String name) {
        this.name = name;
    }

    public Permission(String name, String note) {
        this.name = name;
        this.note = note;
    }

}

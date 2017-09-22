package com.khmersolution.moduler.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vannaravuth Yo
 * Date : 8/23/2017, 10:12 AM
 * Email : ravuthz@gmail.com
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@ApiModel(value = "Role")
@ToString(exclude = "users")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -5403723535622562579L;

    @NotEmpty
    @ApiModelProperty(notes = "Role's name")
    private String name;

    @ApiModelProperty(notes = "Role's note")
    private String note;

    @ApiModelProperty(notes = "Roles's users")
    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    @ApiModelProperty(notes = "Role's permissions")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "rolePermission",
            joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "permissionId"))
    private List<Permission> permissions = new ArrayList<>();

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, String note) {
        this.name = name;
        this.note = note;
    }

}

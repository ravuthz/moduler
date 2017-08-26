package com.khmersolution.moduler.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vannaravuth Yo
 * Date : 8/23/2017, 10:12 AM
 * Email : ravuthz@gmail.com
 */

@Data
@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Role", description = "Role's model resource")
public class Role extends BaseEntity {

    @NotEmpty
    @ApiModelProperty(notes = "Role's name)")
    private String role;

    @ApiModelProperty(notes = "Roles's users")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
            (name = "user_role",
                    joinColumns = @JoinColumn(name = "role_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

    public Role(String role) {
        this.role = role;
    }

    public static Role staticRole(String role) {
        return new Role(role);
    }
}

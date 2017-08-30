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
@ApiModel(value = "Role")
public class Role extends BaseEntity {

    @NotEmpty
    @ApiModelProperty(notes = "Role's name")
    private String name;

    @ApiModelProperty(notes = "Role's note")
    private String note;

    @ApiModelProperty(notes = "Role's users")
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Permission> permissions = new ArrayList<>();

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, String note) {
        this.name = name;
        this.note = note;
    }

}

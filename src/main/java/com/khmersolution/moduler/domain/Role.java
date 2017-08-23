package com.khmersolution.moduler.domain;

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
public class Role extends BaseEntity {

    @NotEmpty
    private String role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
            (name = "user_role",
                    joinColumns = @JoinColumn(name = "role_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

    public Role(String role) {
        this.role = role;
    }
}

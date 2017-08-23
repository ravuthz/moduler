package com.khmersolution.moduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
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
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String username;

    @NotEmpty
    @JsonIgnore
    private String password;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @JsonIgnore
    private boolean enabled;

    @JsonIgnore
    private Integer failedLoginAttempts = 0;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
            (name = "user_role",
                    joinColumns = @JoinColumn(name = "role_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Role> roles = new ArrayList<>();

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setFullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public void addRole(Role role) {
        if (!this.getRoles().contains(role)) {
            this.getRoles().add(role);
        }
        if (!role.getUsers().contains(this)) {
            role.getUsers().add(this);
        }
    }

    public void removeRole(Role role) {
        this.getRoles().remove(role);
        role.getUsers().remove(this);
    }

}
package com.khmersolution.moduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
@ApiModel(value = "User")
public class User extends BaseEntity {

    public static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Email
    @NotEmpty
    @ApiModelProperty(notes = "User's email address", required = true)
    private String email;

    @NotEmpty
    @ApiModelProperty(notes = "User's username (Must be unique)", required = true)
    private String username;

    @NotEmpty
    @ApiModelProperty(notes = "User's password (Must be strong)", required = true)
    private String password;

    @NotEmpty
    @ApiModelProperty(notes = "User's first name (Or surname)", required = true)
    private String firstName;

    @NotEmpty
    @ApiModelProperty(notes = "User's last name (Or family's name)", required = true)
    private String lastName;

    @ApiModelProperty(notes = "User's account status")
    private boolean enabled;

    @ApiModelProperty(notes = "User's login attempts count")
    private Integer failedLoginAttempts = 0;

    @ApiModelProperty(notes = "User's roles")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Role> roles = new ArrayList<>();

    public User(User user) {
        this.email = user.email;
        this.username = user.username;
        this.password = user.password;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.enabled = user.enabled;
        this.failedLoginAttempts = user.failedLoginAttempts;
        this.roles = user.roles;
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static User staticUser(String firstName, String lastName) {
        User user = new User(firstName, lastName);
        user.setEmail(firstName + "@gmail.com");
        user.setUsername(firstName);
        user.setPassword(ENCODER.encode("123123"));
        user.setEnabled(true);
        return user;
    }

    public void setFullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @JsonIgnore
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
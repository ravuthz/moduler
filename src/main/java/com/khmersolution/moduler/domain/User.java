package com.khmersolution.moduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
@Table(name = "users")
@ApiModel(value = "User")
@ToString(exclude = "roles")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements Serializable {

    public static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private static final long serialVersionUID = -7721781417455120512L;

    @Email
    @NotEmpty
    @ApiModelProperty(notes = "User's email address", required = true)
    private String email;

    @NotEmpty
    @ApiModelProperty(notes = "User's username (Must be unique)", required = true)
    private String username;

    @NotEmpty
    @JsonIgnore
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

    @JsonIgnore
    @ApiModelProperty(notes = "User's roles")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userRole",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
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

}
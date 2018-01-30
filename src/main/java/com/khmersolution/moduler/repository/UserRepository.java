package com.khmersolution.moduler.repository;

import com.khmersolution.moduler.domain.Role;
import com.khmersolution.moduler.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by Vannaravuth Yo
 * Date : 8/23/2017, 10:15 AM
 * Email : ravuthz@gmail.com
 */

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByEmailAndEnabledTrue(@Param("email") String email);

    User findByUsernameAndEnabledTrue(@Param("username") String username);

    User findByEmail(@Param("email") String email);

    User findByUsername(@Param("username") String username);

    @RestResource(path = "first-name", rel = "first-name")
    List<User> findAllByFirstName(@Param("text") String text, Pageable pageable);

    @RestResource(path = "last-name", rel = "last-name")
    List<User> findAllByLastName(@Param("text") String text, Pageable pageable);

    @RestResource(path = "name", rel = "name")
    List<User> findAllByFirstNameOrLastNameContainsIgnoreCase(@Param("text") String text, Pageable pageable);

    @RestResource(path = "email", rel = "email")
    List<User> findAllByEmailContainsIgnoreCase(@Param("text") String text, Pageable pageable);

    @RestResource(path = "username", rel = "username")
    List<User> findAllByUsernameContainsIgnoreCase(@Param("text") String text, Pageable pageable);

    @Query(value = "select id, username, firstName, lastName from User")
    List<User> getQueryOnlyName(Pageable pageable);

    @RestResource(path = "equals-role", rel = "username")
    Page<User> findAllByRolesEquals(@Param("roles") List<Role> roles, Pageable pageable);

    @RestResource(path = "contains-role", rel = "username")
    Page<User> findAllByRolesContains(@Param("roles") List<Role> roles, Pageable pageable);

}

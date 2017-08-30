package com.khmersolution.moduler.repository;

import com.khmersolution.moduler.domain.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Vannaravuth Yo
 * Date : 8/23/2017, 10:15 AM
 * Email : ravuthz@gmail.com
 */

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    Role findByName(@Param("name") String role);

    List<Role> findAllByNameIsLike(@Param("name") String role);

    List<Role> findAllByNameContains(@Param("name") String role);
}

package com.khmersolution.moduler.repository;

import com.khmersolution.moduler.domain.Permission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Vannaravuth Yo
 * Date : 8/30/2017, 4:12 PM
 * Email : ravuthz@gmail.com
 */

@Repository
public interface PermissionRepository extends PagingAndSortingRepository<Permission, Long> {
    Permission findByName(@Param("name") String permission);
}

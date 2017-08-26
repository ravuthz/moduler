package com.khmersolution.moduler.service;

import com.khmersolution.moduler.domain.Role;
import com.khmersolution.moduler.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vannaravuth Yo
 * Date : 8/26/2017, 1:14 PM
 * Email : ravuthz@gmail.com
 */

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository repository;

    @Override
    public List<Role> getAll() {
        return (List<Role>) repository.findAll();
    }

    @Override
    public Page<Role> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Role getById(Serializable id) {
        return repository.findOne((Long) id);
    }

    @Override
    public Role save(Role entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Serializable id) {
        repository.delete((Long) id);
    }
}

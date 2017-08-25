package com.khmersolution.moduler.service;

import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vannaravuth Yo
 * Date : 8/24/2017, 2:23 PM
 * Email : ravuthz@gmail.com
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    public User getById(Serializable id) {
        return repository.findOne((Long) id);
    }

    @Override
    public User save(User entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Serializable id) {
        repository.delete((Long) id);
    }
}

package com.khmersolution.moduler.service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vannaravuth Yo
 * Date : 8/24/2017, 2:22 PM
 * Email : ravuthz@gmail.com
 */

public interface CRUDService<E> {
    List<E> getAll();

    E getById(Serializable id);

    E save(E entity);

    void delete(Serializable id);
}

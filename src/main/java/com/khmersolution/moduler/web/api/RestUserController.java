package com.khmersolution.moduler.web.api;

import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Vannaravuth Yo
 * Date : 1/26/2018, 4:51 PM
 * Email : ravuthz@gmail.com
 */

@RepositoryRestController
//@BasePathAwareController
@RequestMapping(value = "/custom/rest/users")
public class RestUserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Page<User> getUsers(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        return userRepository.findAll(new PageRequest(page, size));
    }
}

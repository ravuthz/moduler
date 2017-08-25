package com.khmersolution.moduler.web.api;

import com.khmersolution.moduler.configure.Route;
import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Vannaravuth Yo
 * Date : 8/24/2017, 10:10 AM
 * Email : ravuthz@gmail.com
 */

@RestController
@RequestMapping(value = Route.API_USERS, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            logger.debug("Users does not exists");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.debug("Found " + users.size() + " Users => " + users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = Route.API_ID, method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        if (user == null) {
            logger.debug("User with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("User with id " + id + " found => " + user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.save(user);
        logger.debug("User with id " + createdUser.getId() + " created => " + createdUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = Route.API_ID, method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") Long id) {
        User existing = userService.getById(id);
        if (existing == null) {
            logger.debug("User with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(id);
        User updatedUser = userService.save(user);
        logger.debug("User with id " + updatedUser.getId() + " updated => " + updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @RequestMapping(value = Route.API_ID, method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        if (user == null) {
            logger.debug("User with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        logger.debug("User with id " + id + " deleted => " + user);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
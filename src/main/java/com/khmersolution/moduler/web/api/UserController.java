package com.khmersolution.moduler.web.api;

import com.khmersolution.moduler.configure.Route;
import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vannaravuth Yo
 * Date : 8/24/2017, 10:10 AM
 * Email : ravuthz@gmail.com
 */

@Slf4j
@RestController
@RequestMapping(value = Route.API_USERS, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "UserController", description = "User restful resource with rest controller", tags = "Custom UserController")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<User>> getUsers(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<User> users = userService.getAll(new PageRequest(page, size));
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = Route.API_ID, method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        if (user == null) {
            log.debug("User with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.debug("User with id " + id + " found => " + user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.save(user);
        log.debug("User with id " + createdUser.getId() + " created => " + createdUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = Route.API_ID, method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") Long id) {
        User existing = userService.getById(id);
        if (existing == null) {
            log.debug("User with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(id);
        User updatedUser = userService.save(user);
        log.debug("User with id " + updatedUser.getId() + " updated => " + updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @RequestMapping(value = Route.API_ID, method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        if (user == null) {
            log.debug("User with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        log.debug("User with id " + id + " deleted => " + user);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
package com.khmersolution.moduler.web.api;

import com.khmersolution.moduler.configure.Route;
import com.khmersolution.moduler.domain.Role;
import com.khmersolution.moduler.service.RoleService;
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
 * Date : 8/26/2017, 1:11 PM
 * Email : ravuthz@gmail.com
 */

@Slf4j
@RestController
@RequestMapping(value = Route.API_ROLES, produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(params = {"page", "size"}, method = RequestMethod.GET)
    public ResponseEntity<Page<Role>> getRoles(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Role> users = roleService.getAll(new PageRequest(page, size));
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = Route.API_ID, method = RequestMethod.GET)
    public ResponseEntity<Role> getRole(@PathVariable("id") Long id) {
        Role user = roleService.getById(id);
        if (user == null) {
            log.debug("Role with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.debug("Role with id " + id + " found => " + user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Role> createRole(@RequestBody Role user) {
        Role createdRole = roleService.save(user);
        log.debug("Role with id " + createdRole.getId() + " created => " + createdRole);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = Route.API_ID, method = RequestMethod.PUT)
    public ResponseEntity<Role> updateRole(@RequestBody Role user, @PathVariable("id") Long id) {
        Role existing = roleService.getById(id);
        if (existing == null) {
            log.debug("Role with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(id);
        Role updatedRole = roleService.save(user);
        log.debug("Role with id " + updatedRole.getId() + " updated => " + updatedRole);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @RequestMapping(value = Route.API_ID, method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Long id) {
        Role user = roleService.getById(id);
        if (user == null) {
            log.debug("Role with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        roleService.delete(id);
        log.debug("Role with id " + id + " deleted => " + user);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

}

package com.khmersolution.moduler.service;

import com.khmersolution.moduler.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Vannaravuth Yo
 * Date : 8/26/2017, 8:52 AM
 * Email : ravuthz@gmail.com
 */


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Profile(value = "test")
@SuppressWarnings("SpringJavaAutowiringInspection")
public class UserServiceImplTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    private List<String> nameList = Arrays.asList("admin", "adminz", "ravuth", "ravuthz", "user");

    @Before
    public void init() {
        checkConnection();
        generateNewOrSaveUsers();
    }

    @Test
    public void getAll() throws Exception {
        List<User> userList1 = userService.getAll();
        log.debug("1 list users: ");
        log.debug(userList1.toString());
        userList1.forEach(user -> log.debug("user: " + user.getId() + " " + user.getFullName()));
        assertNotNull(userList1);
        assertTrue(userList1.size() > 0);

        Page<User> userList2 = userService.getAll(new PageRequest(0, 20));
        log.debug("2 list users: ");
        log.debug(userList2.toString());
        userList2.getContent().forEach(user -> log.debug("user: " + user.getId() + " " + user.getFullName()));
        assertNotNull(userList2);
        assertTrue(userList2.getContent().size() > 0);
    }

    @Test
    public void getById() throws Exception {
        User user = userService.getById(1L);
        log.debug("found user: " + user);
        assertNotNull(user);
    }

    @Test
    public void save() throws Exception {
        init();
        User user = new User("test", "user1");
        user.setEnabled(true);
        user.setUsername("test");
        user.setPassword("123123test");
        user.setEmail("test@gmail.com");
        User createdUser = userService.save(user);
        log.debug("create user: " + createdUser.toString());
        assertNotNull(createdUser);
        assertEquals(user.getUsername(), createdUser.getUsername());

        createdUser.setEnabled(false);
        createdUser.setUsername("test1");
        createdUser.setPassword("123123test1");
        createdUser.setEmail("test1@gmail.com");
        User updatedUser = userService.save(createdUser);
        log.debug("update user: " + createdUser.toString());
        assertNotNull(updatedUser);
        assertEquals(createdUser.getUsername(), updatedUser.getUsername());
    }

    @Test
    public void delete() throws Exception {
        User user = userService.getById(5L);
        log.debug("found user: " + user.toString());
        assertNotNull(user);
        userService.delete(user.getId());
        log.debug("user was deleted");
        user = userService.getById(5L);
        assertNull(user);
    }

    @Test
    public void crudAction() {
        User user = User.staticUser("crud", "user1");
        User createdUser = userService.save(user);
        assertNotNull(createdUser);
        assertEquals(user.getUsername(), createdUser.getUsername());

        User readUser = userService.getById(createdUser.getId());
        assertNotNull(readUser);
        assertEquals(user.getUsername(), readUser.getUsername());

        readUser.setFailedLoginAttempts(10);
        User updateUser = userService.save(readUser);
        assertNotNull(updateUser);
        assertEquals(new Integer(10), updateUser.getFailedLoginAttempts());
        assertEquals("10", updateUser.getFailedLoginAttempts().toString());

        userService.delete(readUser.getId());
        assertNull(userService.getById(readUser.getId()));
    }

    private void checkConnection() {
        try (Connection connection = dataSource.getConnection()) {
            log.info("catalog:" + connection.getCatalog());
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private List<User> generateNewOrSaveUsers() {
        userService.getAll().forEach(user -> System.out.println(user));

        List<User> userList = new ArrayList<>();
        for (int i = 1; i <= nameList.size(); i++) {
            User user = userService.getById((long) i);
            if (user == null) {
                user = User.staticUser(nameList.get(i - 1), "test");
                user.setId((long) i);
                userService.save(user);
            }
            userList.add(user);
        }
        log.debug("Users before each action: ");
        userList.forEach(user -> log.debug(user.getId() + " => " + user));
        return userList;
    }


}
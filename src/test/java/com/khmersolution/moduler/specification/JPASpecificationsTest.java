package com.khmersolution.moduler.specification;

import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JPASpecificationsTest {

    public static final String LAST_NAME = "lastName";
    public static final String FIRST_NAME = "firstName";

    public static final String SEARCH_OP = ":";
    public static final String SEARCH_LN = "jonh";
    public static final String SEARCH_FN = "tommy";

    public static final String QUERY_PATERN = "(\\w+?)(:|<|>)(\\w+?),";
    public static final String QUERY_SEARCH_1 = "firstName:jerry,lastName:tommy";
    public static final String QUERY_SEARCH_2 = "firstName:hello,lastName:motor";

    @Autowired
    private UserRepository userRepository;

    private User jonhAbc;
    private User jonhDoe;
    private User jerryTommy;
    private User jonhyTommy;

    @Before
    public void init() {
        jonhAbc = User.staticUser("jonh", "abc");
        jonhDoe = User.staticUser("jonh", "doe");
        jerryTommy = User.staticUser("jerry", "tommy");
        jonhyTommy = User.staticUser("jonhy", "tommy");
        userRepository.save(Arrays.asList(
                jonhAbc, jonhDoe, jerryTommy, jonhyTommy
        ));
    }

    @Test
    public void searchByLastName() {
        List<User> result = this.queryWithCriteria(FIRST_NAME, SEARCH_OP, SEARCH_FN);
        Assert.assertNotNull(result);
    }

    @Test
    public void searchByFirstName() {
        List<User> result = this.queryWithCriteria(LAST_NAME, SEARCH_OP, SEARCH_LN);
        Assert.assertNotNull(result);
    }

    @Test
    public void searchByBuilder1() {
        List<User> userList = this.queryWithBuilder(QUERY_PATERN, QUERY_SEARCH_1);
        Assert.assertNotNull(userList);
    }

    @Test
    public void searchByBuilder2() {
        List<User> userList = this.queryWithBuilder(QUERY_PATERN, QUERY_SEARCH_2);
        Assert.assertNull(userList);
    }

    private List<User> queryWithCriteria(String key, String operation, String value) {
        UserSpecification spec = new UserSpecification(new SearchCriteria(key, operation, value));
        List<User> results = userRepository.findAll(spec);
        log.debug("found list" + results);
        return results;
    }

    private List<User> queryWithBuilder(String patternString, String queryString) {
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(queryString + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<User> spec = builder.build();
        List<User> userList = userRepository.findAll(spec);
        log.debug("found list" + userList);
        return userList;

    }

}

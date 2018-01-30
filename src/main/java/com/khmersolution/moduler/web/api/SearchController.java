package com.khmersolution.moduler.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.UserRepository;
import com.khmersolution.moduler.specification.UserSpecificationsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/rest/api/search")
public class SearchController {

    private static final String PATTERN = "(\\w+?)(:|<|>)(\\w+?),";

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/usersKey")
    public Page<User> search(@RequestParam(value = "keyword") String search, Pageable pageable) {
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(search + ",");

        int found = 0;
        while (matcher.find()) {
            System.out.println("matcher found: " + (++found) + " start " + matcher.start() + " end " + matcher.end());
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<User> spec = builder.build();
        List<User> userList = userRepository.findAll(spec);

        return new PageImpl<>(userList, pageable, userList.size());
    }

    @RequestMapping("/users")
    public Page<User> searchQuery(@RequestParam("params") String params, Pageable pageable) {

        try {
            System.out.println("/users params: " + params);
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(params.toString(), User.class);
            System.out.println("Casted User" + user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userRepository.findAll(pageable);
    }

    @RequestMapping("/page/users")
    public Page<User> pageUsers(Pageable pageable) {
        List<User> userList = (List<User>) userRepository.findAll();
        System.out.println("findAll founds : " + userList.size());
        Page<User> pages = new PageImpl<>(userList, pageable, userList.size());

//        List<User> userList1 = userRepository.getQueryOnlyName(pageable);
//        System.out.println("getQueryOnlyName founds : " + userList1.size());
//        Page<User> pages= new PageImpl<>(userList1, pageable, userList1.size());
//

        return pages;
    }
}

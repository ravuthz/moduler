package com.khmersolution.moduler.web.api;

import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.UserRepository;
import com.khmersolution.moduler.specification.UserSpecificationsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/rest/api/search")
public class SearchController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users")
    public List<User> search(@RequestParam(value = "keyword") String search) {
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        int found = 0;
        while (matcher.find()) {
            System.out.println("matcher found: " + (++found) + " start " + matcher.start() + " end " + matcher.end());
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<User> spec = builder.build();
        return userRepository.findAll(spec);
    }

}

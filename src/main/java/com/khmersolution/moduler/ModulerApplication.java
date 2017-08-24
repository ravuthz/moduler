package com.khmersolution.moduler;

import com.khmersolution.moduler.domain.Role;
import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.RoleRepository;
import com.khmersolution.moduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;


@SpringBootApplication
@ComponentScan(basePackages = "com.khmersolution.moduler")
@EntityScan(basePackages = "com.khmersolution.moduler.domain")
@EnableJpaRepositories(basePackages = "com.khmersolution.moduler.repository")
public class ModulerApplication implements CommandLineRunner {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public ModulerApplication(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ModulerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");
        roleRepository.save(Arrays.asList(userRole, adminRole));

        User user = new User();
        user.setEnabled(true);
        user.setFullName("Ravuth", "Yo");
        user.setUsername("ravuthz");
        user.setPassword("123123");
        user.setEmail("ravuthz@gmail.com");
        user.addRole(userRole);

        User admin = new User();
        admin.setEnabled(true);
        admin.setFullName("Admin", "Mr");
        admin.setUsername("admin");
        admin.setPassword("123123");
        admin.setEmail("admin@gmail.com");
        admin.addRole(adminRole);

        userService.save(user);
        userService.save(admin);
    }
}

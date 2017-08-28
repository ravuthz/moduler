package com.khmersolution.moduler;

import com.khmersolution.moduler.configure.Package;
import com.khmersolution.moduler.domain.Role;
import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.RoleRepository;
import com.khmersolution.moduler.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
@EntityScan(basePackages = Package.ENTITY)
@ComponentScan(basePackages = Package.BASE)
@EnableJpaRepositories(basePackages = Package.REPOSITORY)
public class ModulerApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public ModulerApplication(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ModulerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        log.debug("Start creating roles ...");

        roleRepository.save(Arrays.asList(
                Role.staticRole("USER"),
                Role.staticRole("ADMIN")
        ));

        log.debug("Start creating users ...");

        userRepository.save(Arrays.asList(
                User.staticUser("ravuthz", "yo"),
                User.staticUser("adminz", "yes")

        ));

        log.debug("Start assign roles to users ...");

        Role userRole = roleRepository.findByRole("USER");
        Role adminRole = roleRepository.findByRole("ADMIN");

        User user = userRepository.findByUsername("ravuthz");
        User admin = userRepository.findByUsername("adminz");

        user.getRoles().add(userRole);
        admin.getRoles().add(adminRole);

        userRepository.save(Arrays.asList(user, admin));

        log.debug("Check all roles and users ...");

        log.debug(userRole.toString());
        log.debug(adminRole.toString());
        log.debug(user.toString());
        log.debug(admin.toString());
    }
}

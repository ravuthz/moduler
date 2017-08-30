package com.khmersolution.moduler;

import com.khmersolution.moduler.configure.Package;
import com.khmersolution.moduler.domain.Permission;
import com.khmersolution.moduler.domain.Role;
import com.khmersolution.moduler.domain.User;
import com.khmersolution.moduler.repository.PermissionRepository;
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

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
@EntityScan(basePackages = Package.ENTITY)
@ComponentScan(basePackages = Package.BASE)
@EnableJpaRepositories(basePackages = Package.REPOSITORY)
public class ModulerApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public ModulerApplication(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ModulerApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        initUsers();
        initRoles();
        initPermissions();

        List<Permission> permissionList = getPermissionList();
        List<Role> roleList = getRoleList();
        List<User> userList = getUserList();

        Role userRole = roleRepository.findByName("USER");
        Role adminRole = roleRepository.findByName("ADMIN");
        Role clientRole = roleRepository.findByName("CLIENT");

        User user = userRepository.findByUsername("ravuthz");
        User admin = userRepository.findByUsername("adminz");
        User client = userRepository.findByUsername("clientz");

        Permission viewApp = permissionRepository.findByName("VIEW_APP");
        Permission createApp = permissionRepository.findByName("CREATE_APP");
        Permission updateApp = permissionRepository.findByName("UPDATE_APP");
        Permission deleteApp = permissionRepository.findByName("DELETE_APP");

        log.debug("Start apply permissions to roles ...");
        userRole.getPermissions().add(viewApp);
        adminRole.getPermissions().add(viewApp);
        adminRole.getPermissions().add(createApp);
        adminRole.getPermissions().add(updateApp);
        adminRole.getPermissions().add(deleteApp);
        clientRole.getPermissions().add(viewApp);
        clientRole.getPermissions().add(createApp);
        clientRole.getPermissions().add(updateApp);
        roleRepository.save(Arrays.asList(userRole, adminRole, clientRole));

        userRole = roleRepository.findByName("USER");
        adminRole = roleRepository.findByName("ADMIN");
        clientRole = roleRepository.findByName("CLIENT");

        log.debug("Start assign roles to users ...");
        user.getRoles().add(userRole);
        admin.getRoles().add(adminRole);
        client.getRoles().add(clientRole);

        userRepository.save(Arrays.asList(user, admin, client));

//        log.debug("Check all roles ...");
//        log.debug(userRole.toString());
//        log.debug(adminRole.toString());
//        log.debug(clientRole.toString());
//
//        log.debug("Check all users ...");
//        log.debug(user.toString());
//        log.debug(admin.toString());
//        log.debug(client.toString());
//
//        log.debug("Check all permissions ...");
//        log.debug(viewApp.toString());
//        log.debug(createApp.toString());
//        log.debug(updateApp.toString());
//        log.debug(deleteApp.toString());

//        List<Permission> permissionList = (List<Permission>) permissionRepository.findAll();
//        permissionList.forEach(item -> log.debug(item.toString()));
//
//        List<Role> roleList = (List<Role>) roleRepository.findAll();
//        roleList.forEach(item -> log.debug(item.toString()));
//
//        List<User> userList = (List<User>) userRepository.findAll();
//        userList.forEach(item -> log.debug(item.toString()));
    }

    private void initRoles() {
        log.debug("Start creating roles ...");
        List<Role> roleList = Arrays.asList(
                new Role("USER", "Role as user"),
                new Role("ADMIN", "Role as admin"),
                new Role("CLIENT", "Role as client")
        );
        roleRepository.save(roleList);
    }

    private void initUsers() {
        log.debug("Start creating users ...");
        List<User> userList = Arrays.asList(
                User.staticUser("ravuthz", "yo"),
                User.staticUser("adminz", "yes"),
                User.staticUser("clientz", "trusted")
        );
        userRepository.save(userList);
    }

    private void initPermissions() {
        log.debug("Start creating permissions ...");
        permissionRepository.save(Arrays.asList(
                new Permission("VIEW_APP", "Can list and show application"),
                new Permission("CREATE_APP", "Can create application"),
                new Permission("UPDATE_APP", "Can update application"),
                new Permission("DELETE_APP", "Can delete application")
        ));
    }

    private List<Role> getRoleList() {
        return Arrays.asList(
                roleRepository.findByName("USER"),
                roleRepository.findByName("ADMIN"),
                roleRepository.findByName("CLIENT")
        );
    }

    private List<User> getUserList() {
        return Arrays.asList(
                userRepository.findByUsername("ravuthz"),
                userRepository.findByUsername("adminz"),
                userRepository.findByUsername("clientz")
        );
    }

    private List<Permission> getPermissionList() {
        return Arrays.asList(
                permissionRepository.findByName("VIEW_APP"),
                permissionRepository.findByName("CREATE_APP"),
                permissionRepository.findByName("UPDATE_APP"),
                permissionRepository.findByName("DELETE_APP")
        );
    }

}

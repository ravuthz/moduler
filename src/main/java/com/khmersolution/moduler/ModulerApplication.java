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
import java.util.ArrayList;
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

        log.debug("Start assign permissions to roles ...");
        Permission viewApp = permissionRepository.findByName("VIEW_APP");
        Permission createApp = permissionRepository.findByName("CREATE_APP");
        Permission updateApp = permissionRepository.findByName("UPDATE_APP");
        Permission deleteApp = permissionRepository.findByName("DELETE_APP");

        Permission viewApi = permissionRepository.findByName("VIEW_API");
        Permission crudApi = permissionRepository.findByName("CRUD_API");

        assignPermissionsToRole("USER", Arrays.asList(viewApi, viewApp));
        assignPermissionsToRole("ADMIN", Arrays.asList(viewApi, viewApp, createApp, updateApp, deleteApp));
        assignPermissionsToRole("CLIENT", Arrays.asList(viewApi, crudApi, viewApp, createApp, updateApp));

        log.debug("Start assign roles to users ...");
        assignRoleToUser("USER", "ravuthz");
        assignRoleToUser("ADMIN", "adminz");
        assignRoleToUser("CLIENT", "clientz");

        listAllData();
    }

    private void initRoles() {
        log.debug("Start creating roles ...");
        roleRepository.save(Arrays.asList(
                new Role("USER", "Role as user"),
                new Role("ADMIN", "Role as admin"),
                new Role("CLIENT", "Role as client")
        ));
    }

    private void initUsers() {
        log.debug("Start creating users ...");
        userRepository.save(Arrays.asList(
                User.staticUser("ravuthz", "yo"),
                User.staticUser("adminz", "yes"),
                User.staticUser("clientz", "trusted")
        ));
        List<User> userList = new ArrayList<>();
        for (int i=1; i<=100; i++) {
            userList.add(User.staticUser("test_" + i, "user"));
        }
        userRepository.save(userList);
    }

    private void initPermissions() {
        log.debug("Start creating permissions ...");
        permissionRepository.save(Arrays.asList(
                new Permission("CRUD_APP", "Can crud on application"),
                new Permission("VIEW_APP", "Can list and show application"),
                new Permission("CREATE_APP", "Can create application"),
                new Permission("UPDATE_APP", "Can update application"),
                new Permission("DELETE_APP", "Can delete application"),

                new Permission("CRUD_API", "Can management on api with full access"),
                new Permission("VIEW_API", "Can view and list all api"),
                new Permission("CREATE_API", "Can create api"),
                new Permission("UPDATE_API", "Can update api"),
                new Permission("DELETE_API", "Can delete api")
        ));
    }

    private void listAllData() {
        log.debug("Check all permissions ...");
        List<Permission> permissionList = (List<Permission>) permissionRepository.findAll();
        permissionList.forEach(item -> log.debug(item.toString()));

        log.debug("Check all roles ...");
        List<Role> roleList = (List<Role>) roleRepository.findAll();
        roleList.forEach(item -> log.debug(item.toString()));

        log.debug("Check all users ...");
        List<User> userList = (List<User>) userRepository.findAll();
        userList.forEach(item -> log.debug(item.toString()));
    }

    private void assignPermissionsToRole(String name, List<Permission> permissions) {
        Role role = roleRepository.findByName(name);
        if (role != null) {
            permissions.forEach(role.getPermissions()::add);
            roleRepository.save(role);
        }
    }

    private void assignRoleToUser(String roleName, String userName) {
        Role role = roleRepository.findByName(roleName);
        User user = userRepository.findByUsername(userName);
        if (user != null) {
            user.getRoles().add(role);
            userRepository.save(user);
            role.getUsers().add(user);
            roleRepository.save(role);
        }
    }

}

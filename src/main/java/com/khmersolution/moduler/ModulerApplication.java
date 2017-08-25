package com.khmersolution.moduler;

import com.khmersolution.moduler.configure.Package;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = Package.ENTITY)
@ComponentScan(basePackages = Package.BASE)
@EnableJpaRepositories(basePackages = Package.REPOSITORY)
public class ModulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModulerApplication.class, args);
    }
}

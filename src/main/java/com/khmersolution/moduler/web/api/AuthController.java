package com.khmersolution.moduler.web.api;

import com.khmersolution.moduler.configure.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping(Route.OAUTH_TEST)
public class AuthController {

    @GetMapping(value = Route.HELLO_WEB)
    public AuthResponse hello() {
        return new AuthResponse("Hello World!");
    }

    @GetMapping(value = Route.HELLO_API)
    @PreAuthorize("hasAuthority('VIEW_API')")
    public AuthResponse helloApi() {
        return new AuthResponse("Hello API!");
    }

    @GetMapping(value = Route.AUTH)
    @PreAuthorize("hasAuthority('VIEW_API')")
    public AuthResponse helloAuth(Principal principal) {
        log.info("login user: " + principal.getName().toString());
        printAuthenticationDetails();
        return new AuthResponse(String.format("Welcome '%s'!", principal.getName()));
    }

    @GetMapping(value = Route.USER)
    @PreAuthorize("hasAuthority('VIEW_API')")
    public AuthResponse helloUser(Principal principal) {
        log.info("login user: " + principal.getName().toString());
        printAuthenticationDetails();
        return new AuthResponse(String.format("Welcome '%s'!", principal.getName()));
    }

    @GetMapping(value = Route.ADMIN)
    @PreAuthorize("hasAuthority('VIEW_API')")
    public AuthResponse helloAdmin(Principal principal) {
        log.info("login user: " + principal.getName().toString());
        printAuthenticationDetails();
        return new AuthResponse(String.format("Welcome '%s'!", principal.getName()));
    }

    @GetMapping(value = Route.CLIENT)
    @PreAuthorize("hasAuthority('VIEW_API')")
    public AuthResponse helloClient(Principal principal) {
        log.info("login user: " + principal.getName().toString());
        printAuthenticationDetails();
        return new AuthResponse(String.format("Welcome '%s'!", principal.getName()));
    }

    private void printAuthenticationDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication.getName(): " + authentication.getName());
        System.out.println("authentication.getAuthorities(): " + authentication.getAuthorities());
        System.out.println("authentication.getCredentials(): " + authentication.getCredentials());
        System.out.println("authentication.getDetails(): " + authentication.getDetails());
        System.out.println("authentication.getPrincipal(): " + authentication.getPrincipal());
    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class AuthResponse {
    private String message;
}

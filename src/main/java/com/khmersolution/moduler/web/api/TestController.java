package com.khmersolution.moduler.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by Vannaravuth Yo
 * Date : 8/29/17, 9:36 PM
 * Email : ravuthz@gmail.com
 */

@Slf4j
@RestController
@RequestMapping("/rest/api")
public class TestController {

    @GetMapping(value = "/")
    public RestMsg index() {
        return new RestMsg("Hello World!");
    }

    @GetMapping(value = "/test")
    public RestMsg apiTest() {
        return new RestMsg("Hello apiTest!");
    }

    @GetMapping(value = "/hello", produces = "application/json")
    public RestMsg helloUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new RestMsg(String.format("Hello '%s'!", username));
    }

    @GetMapping(value = "/auth")
    @PreAuthorize("isAuthenticated()")
    public RestMsg helloAuth(Principal principal) {
        log.info("login user: " + principal.getName().toString());
        return new RestMsg(String.format("Welcome '%s'!", principal.getName()));
    }

    @GetMapping(value = "/user")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN') or hasRole('USER') or hasRole('ADMIN')")
    public RestMsg helloUser(Principal principal) {
        log.info("login user: " + principal.getName().toString());
        return new RestMsg(String.format("Welcome '%s'!", principal.getName()));
    }


    @GetMapping(value = "/admin")
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')")
    public RestMsg helloAdmin(Principal principal) {
        log.info("login user: " + principal.getName().toString());
        return new RestMsg(String.format("Welcome '%s'!", principal.getName()));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestMsg {
        private String message;
    }

}

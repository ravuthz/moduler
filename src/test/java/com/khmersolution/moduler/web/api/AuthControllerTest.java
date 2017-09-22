package com.khmersolution.moduler.web.api;

import lombok.Getter;
import lombok.Setter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.test.OAuth2ContextConfiguration;
import org.springframework.security.oauth2.client.test.OAuth2ContextSetup;
import org.springframework.security.oauth2.client.test.RestTemplateHolder;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import static com.khmersolution.moduler.configure.Route.*;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest implements RestTemplateHolder {

    @Rule
    public OAuth2ContextSetup context = OAuth2ContextSetup.standard(this);
    @Value("http://localhost:${local.server.port}")
    @Getter
    protected String host;
    @Getter
    @Setter
    private RestOperations restTemplate = new RestTemplate();

    @Before
    public void setUp() throws Exception {
        System.out.println("test host: " + host);
    }

    @Test
    public void hello() throws Exception {
        restGetAction(host + OAUTH_TEST + HELLO_WEB);
    }

    @Test
    public void helloApi() throws Exception {
        restGetAction(host + OAUTH_TEST + HELLO_API);
    }

    @Test
    @OAuth2ContextConfiguration(UserResource.class)
    public void helloAuth() throws Exception {
        restGetAction(host + OAUTH_TEST + AUTH);
    }

    @Test
    @OAuth2ContextConfiguration(UserResource.class)
    public void helloUser() throws Exception {
        restGetAction(host + OAUTH_TEST + USER);
    }

    @Test
    @OAuth2ContextConfiguration(AdminResource.class)
    public void helloAdmin() throws Exception {
        restGetAction(host + OAUTH_TEST + ADMIN);
    }

    @Test
    @OAuth2ContextConfiguration(ClientResource.class)
    public void helloClient() throws Exception {
        restGetAction(host + OAUTH_TEST + CLIENT);
    }

    private void restGetAction(String action) {
        try {
            ResponseEntity<AuthResponse> entity = restTemplate.getForEntity(action, AuthResponse.class);
            assertTrue(entity.toString(), entity.getStatusCode().is2xxSuccessful());
            System.out.println(entity.getBody());
        } catch (OAuth2AccessDeniedException auth) {
            System.out.println("auth: " + auth.getMessage());
        } catch (HttpClientErrorException http) {
            System.out.println("http: " + http.getMessage());
        }
    }

    static class TrustedAppResource extends ResourceOwnerPasswordResourceDetails {
        public TrustedAppResource(final Object obj) {
            AuthControllerTest it = (AuthControllerTest) obj;
            setAccessTokenUri(it.getHost() + "/oauth/token");
            setGrantType("password");
            setClientId("trusted-app");
            setClientSecret("secret");
            setPassword("123123");
        }
    }

    static class UserResource extends TrustedAppResource {
        public UserResource(Object obj) {
            super(obj);
            setUsername("ravuthz");
        }
    }

    static class AdminResource extends TrustedAppResource {
        public AdminResource(Object obj) {
            super(obj);
            setUsername("adminz");
        }
    }

    static class ClientResource extends TrustedAppResource {
        public ClientResource(Object obj) {
            super(obj);
            setUsername("clientz");
        }
    }

}




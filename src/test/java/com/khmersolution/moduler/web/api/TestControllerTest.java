package com.khmersolution.moduler.web.api;

import lombok.Getter;
import lombok.Setter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.test.OAuth2ContextConfiguration;
import org.springframework.security.oauth2.client.test.OAuth2ContextSetup;
import org.springframework.security.oauth2.client.test.RestTemplateHolder;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Vannaravuth Yo
 * Date : 9/1/17, 8:55 PM
 * Email : ravuthz@gmail.com
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerTest implements RestTemplateHolder {
    @Rule
    public OAuth2ContextSetup context = OAuth2ContextSetup.standard(this);
    @Value("http://localhost:9999")
    protected String host;
    @Getter
    @Setter
    private RestOperations restTemplate = new RestTemplate();

    @Test
    public void clientAccess() {
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
//        resourceDetails.setUsername("adminz");
//        resourceDetails.setPassword("123123");
        resourceDetails.setAccessTokenUri(host + "/oauth/token");
        resourceDetails.setClientId("trusted-app");
//        resourceDetails.setClientSecret("secret");
        resourceDetails.setGrantType("password");


        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();

        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
        restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));

        System.out.println("access_token => " + restTemplate.getAccessToken());

        final TestController.RestMsg message = restTemplate.getForObject(host + "/", TestController.RestMsg.class);

        System.out.println(message);


    }

    @Test
    public void adminAccess() {
        AdminDetails adminDetails = new AdminDetails(this);
        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();

        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(adminDetails, clientContext);
        restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));

        final TestController.RestMsg message = restTemplate.getForObject(host + "/", TestController.RestMsg.class);

        System.out.println(message);

    }

    @Test
    @OAuth2ContextConfiguration(ClientDetails.class)
    public void testAuth() {
        System.out.println("host: " + host);
        ResponseEntity<TestController.RestMsg> entity = restTemplate.getForEntity(host + "/rest/api/auth", TestController.RestMsg.class);
        assertTrue(entity.toString(), entity.getStatusCode().is2xxSuccessful());
        System.out.println(entity.getBody());
//        assertEquals("Hello user", entity.getBody());
    }

    @Test
    @OAuth2ContextConfiguration(UserDetails.class)
    public void testUser() {
        System.out.println("host: " + host);
        ResponseEntity<TestController.RestMsg> entity = restTemplate.getForEntity(host + "/rest/api/user", TestController.RestMsg.class);
        assertTrue(entity.toString(), entity.getStatusCode().is2xxSuccessful());
        System.out.println(entity.getBody());
//        assertEquals("Hello alice", entity.getBody());
    }

    @Test(expected = OAuth2AccessDeniedException.class)
    @OAuth2ContextConfiguration(AdminDetails.class)
    public void testAdmin() {
        System.out.println("host: " + host);
        ResponseEntity<TestController.RestMsg> entity = restTemplate.getForEntity(host + "/rest/api/admin", TestController.RestMsg.class);
        assertTrue(entity.toString(), entity.getStatusCode().is2xxSuccessful());
        System.out.println(entity.getBody());
//        restTemplate.getForEntity(host + "/hello", String.class);
    }
}


class UserDetails extends ResourceOwnerPasswordResourceDetails {
    public UserDetails(final Object obj) {
        TestControllerTest it = (TestControllerTest) obj;
        setAccessTokenUri(it.host + "/oauth/token");
        setGrantType("password");
        setClientId("trusted-app");
        setClientSecret("secret");
        setUsername("ravuthz");
        setPassword("123123");
//        setScope(Arrays.asList("read", "write"));
    }
}

class AdminDetails extends ResourceOwnerPasswordResourceDetails {
    public AdminDetails(final Object obj) {
        TestControllerTest it = (TestControllerTest) obj;
        setAccessTokenUri(it.host + "/oauth/token");
        setGrantType("password");
        setClientId("trusted-app");
        setClientSecret("secret");
        setUsername("adminz");
        setPassword("123123");
    }
}

class ClientDetails extends ResourceOwnerPasswordResourceDetails {
    public ClientDetails(final Object obj) {
        TestControllerTest it = (TestControllerTest) obj;
        setAccessTokenUri(it.host + "/oauth/token");
        setGrantType("password");
        setClientId("trusted-app");
        setClientSecret("secret");
        setUsername("clientz");
        setPassword("123123");
    }
}

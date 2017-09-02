package com.khmersolution.moduler.web.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Vannaravuth Yo
 * Date : 9/2/2017, 9:20 AM
 * Email : ravuthz@gmail.com
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class OAuthTest {
    @Autowired
    WebApplicationContext context;

    @Autowired
    FilterChainProxy springSecurityFilterChain;
    MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void shouldHavePermission() throws Exception {
        mvc.perform(get("/")
                .header("Authorization", "Bearer " + getAccessToken("adminz", "123123"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String getAccessToken(String username, String password) {
        String accessToken = "";
        MockHttpServletResponse response = null;
        try {
            response = mvc
                    .perform(post("/oauth/token")
                            .header("Authorization", "Basic "
                                    + new String(Base64Utils.encode(("trusted-app:secret")
                                    .getBytes())))
                            .param("username", username)
                            .param("password", password)
                            .param("grant_type", "password"))
                    .andReturn().getResponse();

            accessToken = new ObjectMapper()
                    .readValue(response.getContentAsByteArray(), OAuthToken.class)
                    .accessToken;
            System.out.println("accessToken: " + accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class OAuthToken {
        @JsonProperty("access_token")
        public String accessToken;

    }
}
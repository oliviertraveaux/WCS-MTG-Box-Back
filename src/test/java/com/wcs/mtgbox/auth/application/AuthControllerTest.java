package com.wcs.mtgbox.auth.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;
import com.wcs.mtgbox.auth.domain.entity.Role;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class
AuthControllerTest {

    @Value( "${test.user.username}" )
    private  String USER_USERNAME;

    @Value( "${test.user.password}" )
    private  String USER_PASSWORD;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TestLoginShouldSuccess() throws Exception {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("username", USER_USERNAME);
        jsonUser.put("password", USER_PASSWORD);

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/login")
                                .accept(MediaType.APPLICATION_JSON).content(jsonUser.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
        ;

        assertNotNull(result.getResponse().getContentType());
    }

    @Test
    public void TestLoginShouldFail() throws Exception {
        JSONObject jsonCredentials = new JSONObject();
        jsonCredentials.put("username", USER_USERNAME);
        jsonCredentials.put("password", "wrongCredentials");

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/login")
                                .accept(MediaType.APPLICATION_JSON).content(jsonCredentials.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void TestRegisterShouldSuccess() throws Exception {
        UserRegistrationDTO user = new UserRegistrationDTO("Jack", "jack@gmail.com", "password123", 75000, "Paris");
        UserDTO userResponse = new UserDTO(10L, "jack", "jack@gmail.com", true, false, 33000, "Bordeaux", LocalDateTime.now(),
                LocalDateTime.now(), new Role(1L, "USER"));
        ObjectMapper mapper = new ObjectMapper();



        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/register")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        System.out.println("result : " + result.getResponse().getContentAsString());
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

   @Test
    public void testRegister() throws Exception{
       JSONObject jsonUser = new JSONObject();
       jsonUser.put("email", "bob@doe.com");
       jsonUser.put("username", "bob");
       jsonUser.put("postCode", 31000);
       jsonUser.put("city", "Toulouse");
       jsonUser.put("password", "azerty");

        mockMvc
               .perform(MockMvcRequestBuilders.post("/api/v1/register")
                       .content(jsonUser.toString())
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("bob@doe.com"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("bob"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.postCode").value(31000))
               .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Toulouse"))
               ;

   }

    @Test
    public void TestRegisterShouldFail() throws Exception {
        UserRegistrationDTO user = new UserRegistrationDTO(USER_USERNAME, "john@doe.com", USER_PASSWORD, 31000, "Toulouse");

        ObjectMapper mapper = new ObjectMapper();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/register")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testLogin() throws Exception {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("username", USER_USERNAME);
        jsonUser.put("password", USER_PASSWORD);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/login")
                        .content(jsonUser.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                ;
System.out.println("token : " + result.getResponse().getContentAsString());
        assertNotNull(result.getResponse().getContentType());
    }
}





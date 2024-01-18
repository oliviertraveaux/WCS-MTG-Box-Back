package com.wcs.mtgbox.auth.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;
import com.wcs.mtgbox.auth.domain.entity.Role;
import com.wcs.mtgbox.auth.domain.service.UserRegistrationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Value("${test.user.username}")
    private String USER_USERNAME;

    @Value("${test.user.password}")
    private String USER_PASSWORD;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRegistrationService userRegistrationService;


    @Test
    public void TestLoginShouldSuccess() throws Exception {
        Credentials credentials = new Credentials(USER_USERNAME, USER_PASSWORD);
        ObjectMapper mapper = new ObjectMapper();

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/login")
                                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(credentials))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

    @Test
    public void TestLoginShouldFail() throws Exception {
        Credentials wrongCredentials = new Credentials(USER_USERNAME, "wrongPassword");
        ObjectMapper mapper = new ObjectMapper();

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/login")
                                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(wrongCredentials))
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

        Mockito.when(userRegistrationService.UserRegistration(Mockito.any(UserRegistrationDTO.class))).thenReturn(userResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/register")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void TestRegisterShouldFail() throws Exception {
        UserRegistrationDTO user = new UserRegistrationDTO("Jack", "jack@gmail.com", "password123", 75000, "Paris");

        ObjectMapper mapper = new ObjectMapper();

        DataIntegrityViolationException userRegistrationResponse = new DataIntegrityViolationException("Bad request") ;
        Mockito.when(userRegistrationService.UserRegistration(Mockito.any(UserRegistrationDTO.class))).thenThrow(userRegistrationResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/register")
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}


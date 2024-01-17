package com.wcs.mtgbox.auth.application;


import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Value( "${test.user.username}" )
    private  String USER_USERNAME;

    @Value( "${test.user.password}" )
    private  String USER_PASSWORD;

    @Autowired
    private MockMvc mockMvc;


    public String loginHelper(String username, String password) throws Exception {
        JSONObject jo = new JSONObject();
        jo.put("username", username);
        jo.put("password", password);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/login")
                        .content(jo.toString())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        MvcResult result = resultActions.andReturn();
        return result.getResponse().getContentAsString();
    }

    @Test
    public void TestReadUserShouldBeStatusOK() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/users/2")
                                .header("Authorization", "Bearer " + loginHelper(USER_USERNAME, USER_PASSWORD))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

    @Test
    public void TestReadUserShouldBeStatusNotFound() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/users/0")
                                .header("Authorization", "Bearer " + loginHelper(USER_USERNAME, USER_PASSWORD))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }
}
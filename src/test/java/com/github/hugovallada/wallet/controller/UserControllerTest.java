package com.github.hugovallada.wallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hugovallada.wallet.dto.UserDTO;
import com.github.hugovallada.wallet.entity.User;
import com.github.hugovallada.wallet.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final String EMAIL = "email@teste.com";
    private static final String NAME = "User Test";
    private static final String PASSWORD = "123456";
    private static final String URL = "/user";

    @MockBean
    UserService service;

    @Autowired
    MockMvc mvc;


    @Test
    public void testSave() throws Exception {
        BDDMockito.given(service.save(Mockito.any(User.class))).willReturn(getMockUser());
        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    private User getMockUser() {
        User u = new User();
        u.setEmail(EMAIL);
        u.setName(NAME);
        u.setPassword(PASSWORD);

        return u;
    }

    private String getJsonPayload() throws JsonProcessingException {
        UserDTO dto = new UserDTO();
        dto.setEmail(EMAIL);
        dto.setName(NAME);
        dto.setPassword(PASSWORD);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }
}
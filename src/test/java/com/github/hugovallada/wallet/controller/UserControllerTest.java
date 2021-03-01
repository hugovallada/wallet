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

    private static final Long ID = 1L;
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
        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, EMAIL, NAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(EMAIL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value(PASSWORD));
    }

    @Test
    public void testSaveInvalidUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, "emal", NAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("Email Invalido"));
    }

    private User getMockUser() {
        User u = new User();
        u.setId(ID);
        u.setEmail(EMAIL);
        u.setName(NAME);
        u.setPassword(PASSWORD);

        return u;
    }

    private String getJsonPayload(
            Long id,
            String email,
            String name,
            String password
    ) throws JsonProcessingException {
        UserDTO dto = new UserDTO();
        dto.setId(id);
        dto.setEmail(email);
        dto.setName(name);
        dto.setPassword(password);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }
}
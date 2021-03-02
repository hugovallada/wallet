package com.github.hugovallada.wallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hugovallada.wallet.dto.UserWalletDTO;
import com.github.hugovallada.wallet.entity.User;
import com.github.hugovallada.wallet.entity.UserWallet;
import com.github.hugovallada.wallet.entity.Wallet;
import com.github.hugovallada.wallet.service.UserWalletService;
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
public class UserWalletControllerTest {

    private static final Long ID = 1L;

    @MockBean
    UserWalletService service;

    @Autowired
    MockMvc mvc;

    @Test
    public void testSave() throws Exception {
        BDDMockito.given(service.save(Mockito.any(UserWallet.class))).willReturn(getUserWallet());
        mvc.perform(MockMvcRequestBuilders.post("/user-wallet").content(getJsonPayload(ID, 1L, 1L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(ID));
    }

    @Test
    public void testInvalidSave() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user-wallet").content(getJsonPayload(ID, null, 1L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("O usuario nao deve ser nulo"));
    }

    private UserWallet getUserWallet() {
        User user = new User();
        user.setId(1L);

        Wallet wallet = new Wallet();
        wallet.setId(1L);

        UserWallet uWallet = new UserWallet();
        uWallet.setId(ID);
        uWallet.setUsers(user);
        uWallet.setWallet(wallet);

        return uWallet;
    }

    private String getJsonPayload(Long id, Long userId, Long walletId) throws JsonProcessingException {
        UserWalletDTO dto = new UserWalletDTO();
        dto.setId(id);
        dto.setUser(userId);
        dto.setWallet(walletId);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }

}
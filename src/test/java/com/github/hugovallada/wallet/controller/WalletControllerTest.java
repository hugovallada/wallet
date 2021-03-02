package com.github.hugovallada.wallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hugovallada.wallet.dto.WalletDTO;
import com.github.hugovallada.wallet.entity.Wallet;
import com.github.hugovallada.wallet.service.WalletService;
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

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "WalletTest";
    private static final BigDecimal VALUE = BigDecimal.valueOf(100.90);

    @MockBean
    WalletService service;

    @Autowired
    MockMvc mvc;

    @Test
    public void testSave() throws Exception {

        BDDMockito.given(service.save(Mockito.any(Wallet.class))).willReturn(getMockWallet());
        mvc.perform(MockMvcRequestBuilders.post("/wallet").content(getJsonPayload(ID, NAME, VALUE))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.value").value(VALUE));

    }

    @Test
    public void testSaveInvalid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/wallet").content(getJsonPayload(ID, null, VALUE))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("Name nao pode ser nulo"));
    }

    private Wallet getMockWallet() {
        Wallet wallet = new Wallet();
        wallet.setId(ID);
        wallet.setName("WalletTest");
        wallet.setValue(BigDecimal.valueOf(100.90));

        return wallet;
    }

    private String getJsonPayload(Long id, String name, BigDecimal value) throws JsonProcessingException {
        WalletDTO dto = new WalletDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setValue(value);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(dto);

    }
}
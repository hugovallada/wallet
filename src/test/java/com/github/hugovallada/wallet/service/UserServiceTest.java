package com.github.hugovallada.wallet.service;

import com.github.hugovallada.wallet.entity.User;
import com.github.hugovallada.wallet.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    UserRepository repository;

    @Autowired
    UserService service;

    @BeforeEach
    public void setUp() {
        BDDMockito.given(repository.findByEmail(Mockito.anyString()))
                .willReturn(Optional.of(new User()));
    }

    @Test
    public void testFindByEmail() {
        Optional<User> user = service.findByEmail("email@test.com");

        Assertions.assertTrue(user.isPresent());
    }

}
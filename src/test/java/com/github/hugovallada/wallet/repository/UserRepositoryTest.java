package com.github.hugovallada.wallet.repository;


import com.github.hugovallada.wallet.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {

    private static final String EMAIL = "email@test.com";

    @Autowired
    UserRepository repository;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setName("Set up User");
        user.setPassword("123456");
        user.setEmail(EMAIL);

        repository.save(user);
    }


    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void testSave() {
        User u = new User();
        u.setName("Teste");
        u.setPassword("123456");
        u.setEmail("teste@example.com");

        User response = repository.save(u);

        Assertions.assertNotNull(response);
    }

    @Test
    public void testFindByEmail() {
        Optional<User> response = repository.findByEmail(EMAIL);

        Assertions.assertTrue(response.isPresent());
        Assertions.assertEquals(EMAIL, response.get().getEmail());
    }
}
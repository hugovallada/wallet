package com.github.hugovallada.wallet.repository;


import com.github.hugovallada.wallet.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserReposiotryTest {

    @Autowired
    UserRepositoory repository;

    @Test
    public void testSave() {
        User u = new User();
        u.setName("Teste");
        u.setPassword("123456");
        u.setEmail("teste@example.com");

        User response = repository.save(u);

        Assertions.assertNotNull(response);
    }
}
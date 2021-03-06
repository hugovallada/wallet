package com.github.hugovallada.wallet.controller;

import com.github.hugovallada.wallet.dto.UserDTO;
import com.github.hugovallada.wallet.entity.User;
import com.github.hugovallada.wallet.response.Response;
import com.github.hugovallada.wallet.service.UserService;
import com.github.hugovallada.wallet.util.Bcrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Response<UserDTO>> create(
            @RequestBody @Valid UserDTO dto,
            BindingResult result
    ) {

        Response<UserDTO> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        User user = userService.save(convertDTOToEntity(dto));

        response.setData(convertEntityToDTO(user));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    private User convertDTOToEntity(UserDTO dto) {
        User u = new User();
        u.setId(dto.getId());
        u.setEmail(dto.getEmail());
        u.setName(dto.getName());
        u.setPassword(Bcrypt.getHash(dto.getPassword()));

        return u;
    }

    private UserDTO convertEntityToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());

        return dto;
    }


}
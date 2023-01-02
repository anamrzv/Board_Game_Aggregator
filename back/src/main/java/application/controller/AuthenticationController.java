package application.controller;

import application.configuration.jwt.JwtProvider;
import application.domain.User;
import application.pojo.UserRequest;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping(value = "/auth",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<String> auth(@RequestBody UserRequest userRequest) {
        User user = userService.findByLoginAndPassword(userRequest.getLogin(), userRequest.getPassword());
        if (user != null) {
            String token = jwtProvider.generateToken(user.getUsername());
            return ResponseEntity.ok(token);
        } else return new ResponseEntity<>("Неверный логин и/или пароль", HttpStatus.NOT_FOUND);
    }

}


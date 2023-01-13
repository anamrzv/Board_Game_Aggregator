package application.controller;

import application.configuration.jwt.JwtProvider;
import application.domain.User;
import application.domain.UserRole;
import application.pojo.request.UserRequest;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game_aggregator")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping(value = "/auth/{auth_type}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<String> auth(@PathVariable(value = "auth_type") String authType, @RequestBody UserRequest userRequest) {
        UserRole role;
        switch (authType) {
            case "user":
                role = userService.getRole(1);
                break;
            case "admin":
                role = userService.getRole(2);
                break;
            case "shop":
                role = userService.getRole(3);
                break;
            default:
                return new ResponseEntity<>("Неверный параметр для логина", HttpStatus.NOT_FOUND);
        }

        User user = userService.findByLoginAndPasswordAndUserRole(userRequest.getLogin(), userRequest.getPassword(), role);
        if (user != null) {
            String token = jwtProvider.generateToken(user.getUsername());
            return ResponseEntity.ok(token);
        } else return new ResponseEntity<>("Неверный логин и/или пароль", HttpStatus.NOT_FOUND);
    }


}


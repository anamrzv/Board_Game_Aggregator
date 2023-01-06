package application.controller;

import application.domain.User;
import application.pojo.UserRequest;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game_aggregator")
@CrossOrigin(origins = "*")
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<String> register(@RequestBody UserRequest userRequest) {
        User user = userService.findByLogin(userRequest.getLogin());
        if (user == null) {
            userService.saveUser(userRequest.getLogin(), userRequest.getPassword(), userRequest.getMail(), userRequest.getMailPreferences());
            return ResponseEntity.ok("Вы успешно зарегистрированы");
        } else return new ResponseEntity<>("Логин уже существует", HttpStatus.BAD_REQUEST);
    }
}

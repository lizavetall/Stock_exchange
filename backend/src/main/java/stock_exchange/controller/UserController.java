package stock_exchange.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import stock_exchange.dto.CreateUserDTO;
import stock_exchange.dto.UserDTO;
import stock_exchange.model.User;
import stock_exchange.response.MessageResponse;
import stock_exchange.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity findAll() {

        return new ResponseEntity(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("find/id/{id}")
    public UserDTO findUser(@PathVariable(name = "id") int id) {
        return userService.findById(id);
    }

    @GetMapping("find/name/{email}")
    public UserDTO find(@PathVariable(name = "email") String email) {
        return userService.findByEmail(email);
    }

    @PostMapping("/create")
    public void create(@RequestBody UserDTO user) {
        userService.save(user);
    }

    @GetMapping("exist/{email}")
    public ResponseEntity existsByEmail(@PathVariable(name = "email") String email) {
        return new ResponseEntity(userService.existsByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/registration")
    public void register(@RequestBody CreateUserDTO user) {
        userService.register(user);
    }

    @GetMapping("/clients")
    public ResponseEntity findClients() {
        return new ResponseEntity(userService.findClients(), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<MessageResponse> update(@RequestParam(name = "id") int userId,
                                                  @RequestParam String role) {
        return new ResponseEntity(userService.update(userId, role), HttpStatus.OK);
    }
}

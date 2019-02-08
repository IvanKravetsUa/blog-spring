package ivan.kravets.controller;

import ivan.kravets.domain.UserDTO;
import ivan.kravets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")  // localhost:8080/users
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) {
        UserDTO userDTO = userService.saveUser(user);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED); //201
    }

    @GetMapping("{userId:[0-9]{1,5}}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long id) {
        UserDTO userDTO = userService.findUserById(id);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PutMapping("{userId:[0-9]{1,5}}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long id, @RequestBody UserDTO userToUpdate) {
        UserDTO user = userService.updateUser(id, userToUpdate);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

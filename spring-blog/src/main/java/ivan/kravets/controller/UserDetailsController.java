package ivan.kravets.controller;

import ivan.kravets.domain.UserDetailsDTO;
import ivan.kravets.service.UserDetalisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("userdetails")
public class UserDetailsController {


    @Autowired
    private UserDetalisService userDetalisService;

    @PostMapping("/user/{userId:[0-9]{1,5}}")
    public ResponseEntity<?> createUserDetails(@PathVariable("userId") Long id, @RequestBody UserDetailsDTO userDetails) {
        UserDetailsDTO userDetailsDTO = userDetalisService.saveUserDetails(id, userDetails);

        return new ResponseEntity<>(userDetailsDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getUserDetails() {
        List<UserDetailsDTO> userDetails = userDetalisService.findAllUserDetails();
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @GetMapping("/user/{userId:[0-9]{1,5}}")
    public ResponseEntity<?> findUserDetalisByUserId (@PathVariable("userId") Long idUser) {
        UserDetailsDTO userDetails = userDetalisService.findUserDetailsByIdUser(idUser);

        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

}

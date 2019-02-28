package ivan.kravets.controller;

import ivan.kravets.domain.request.SignupRequest;
import ivan.kravets.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignupRequest request) {
        authService.registerUser(request);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}

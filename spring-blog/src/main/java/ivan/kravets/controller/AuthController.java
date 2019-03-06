package ivan.kravets.controller;

import ivan.kravets.domain.request.SigninRequest;
import ivan.kravets.domain.request.SignupRequest;
import ivan.kravets.domain.response.SigninResponse;
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

    @PostMapping("signin")
    public ResponseEntity loginUser(@Valid @RequestBody SigninRequest request) {
        String token = authService.loginUser(request);
        return new ResponseEntity(SigninResponse.builder().token(token).build(),
                HttpStatus.OK);
    }
}

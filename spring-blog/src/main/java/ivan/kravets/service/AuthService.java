package ivan.kravets.service;

import ivan.kravets.domain.request.SignupRequest;

public interface AuthService {

    void registerUser(SignupRequest request);
}

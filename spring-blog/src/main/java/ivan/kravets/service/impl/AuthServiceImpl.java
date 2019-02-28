package ivan.kravets.service.impl;

import ivan.kravets.domain.request.SignupRequest;
import ivan.kravets.entity.RoleEntity;
import ivan.kravets.entity.UserEntity;
import ivan.kravets.exceptions.AlreadyExistsException;
import ivan.kravets.exceptions.NotFoundException;
import ivan.kravets.repository.RoleRepository;
import ivan.kravets.repository.UserRepository;
import ivan.kravets.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void registerUser(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("User with email: [" + request.getEmail() + "] already exist");
        }

        String password = request.getPassword();
        System.out.println("password: " +password);

        String encPassword = passwordEncoder.encode(password);
        System.out.println("Enc Password " + encPassword);

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(encPassword);
        userEntity.setSex(request.getSex());
        userEntity.setReputation(request.getReputation());
        userEntity.setImage("logo.png");

        RoleEntity roleEntity = roleRepository.findByRoleIgnoreCase("USER")
                .orElseThrow(() -> new NotFoundException("Role not found"));
        userEntity.setRoles(Arrays.asList(roleEntity));

        userRepository.save(userEntity);
    }
}

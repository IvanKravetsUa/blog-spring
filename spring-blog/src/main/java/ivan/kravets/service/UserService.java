package ivan.kravets.service;

import ivan.kravets.domain.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO saveUser(UserDTO user);

    List<UserDTO> findAllUsers();

    UserDTO findUserById(Long id);

    UserDTO updateUser(Long id, UserDTO userToUpdate);

    void addImageToUser(Long id, String fileName);

}

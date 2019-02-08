package ivan.kravets.service;

import ivan.kravets.domain.UserDetailsDTO;

import java.util.List;

public interface UserDetalisService {

    UserDetailsDTO saveUserDetails (Long idUser, UserDetailsDTO userDetails);

    List<UserDetailsDTO> findAllUserDetails ();

    UserDetailsDTO findUserDetailsByIdUser (Long idUser);
}

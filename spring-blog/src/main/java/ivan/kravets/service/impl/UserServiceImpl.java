package ivan.kravets.service.impl;

import ivan.kravets.domain.UserDTO;
import ivan.kravets.entity.UserEntity;
import ivan.kravets.exceptions.AlreadyExistsException;
import ivan.kravets.exceptions.NotFoundException;
import ivan.kravets.exceptions.ServerException;
import ivan.kravets.repository.UserRepository;
import ivan.kravets.service.UserService;
import ivan.kravets.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapperUtils objectMapper;

    @Override
    public UserDTO saveUser(UserDTO user) {

        if (user.getPassword().equals(user.getPasswordConfirm())) {
            UserEntity userEntity = objectMapper.map(user, UserEntity.class);  //dtoToEntityMapper(user);
            if (!userRepository.existsByEmail(userEntity.getEmail())){
                userRepository.save(userEntity);
                user.setId(userEntity.getId());
                return user;
            } else  {
                throw new ServerException("User with email" + user.getEmail()+ " exist");
            }

        } else {
            throw new ServerException("passwords not match");
        }
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDTO> userDTOS = objectMapper.mapAll(userEntities, UserDTO.class);

//        for (UserEntity userEntity : userEntities) {
//            UserDTO userDTO = entityToDTOMapper(userEntity);
//            userDTOS.add(userDTO);
//        }

        return userDTOS;
    }

    @Override
    public UserDTO findUserById(Long id) {
//        boolean exists = userRepository.existsById(id);
//
//        if (!exists) {
//            throw new NotFoundException("User with id [" +id+ "] not found");
//        }

        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id [" +id+ "] not found"));
        UserDTO userDTO = objectMapper.map(userEntity, UserDTO.class);

        return userDTO;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userToUpdate) {
//        boolean exists = userRepository.existsById(id);
//
//        if (!exists) {
//            throw new NotFoundException("User with id [" +id+ "] not found");
//        }
        UserEntity userEntity = objectMapper.map(userToUpdate, UserEntity.class);
        UserEntity userFromDB = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id [" +id+ "] not found"));
        userFromDB = userEntity;

        userRepository.save(userFromDB);
        return null;
    }

    @Override
    public void addImageToUser(Long id, String fileName) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userEntity.setImage(fileName);
        userRepository.save(userEntity);
    }









    //    private UserDTO entityToDTOMapper(UserEntity userEntity) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(userEntity.getId());
//        userDTO.setFirstName(userEntity.getFirstName());
//        userDTO.setLastName(userEntity.getLastName());
//        userDTO.setNickName(userEntity.getNickName());
//        userDTO.setAccountCreatedDate(userEntity.getAccountCreatedDate());
//
//        return userDTO;
//    }
//
//    private UserEntity dtoToEntityMapper(UserDTO userDTO) {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(userDTO.getId());
//        userEntity.setFirstName(userDTO.getFirstName());
//        userEntity.setLastName(userDTO.getLastName());
//        userEntity.setNickName(userDTO.getNickName());
//        userEntity.setAccountCreatedDate(userDTO.getAccountCreatedDate());
//
//        return userEntity;
//    }
}

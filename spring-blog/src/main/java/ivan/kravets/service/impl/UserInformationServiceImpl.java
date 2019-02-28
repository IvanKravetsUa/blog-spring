package ivan.kravets.service.impl;

import ivan.kravets.domain.UserDetailsDTO;
import ivan.kravets.entity.UserDetailsEntity;
import ivan.kravets.entity.UserEntity;
import ivan.kravets.exceptions.NotFoundException;
import ivan.kravets.repository.UserInformationRepository;
import ivan.kravets.repository.UserRepository;
import ivan.kravets.service.UserDetalisService;
import ivan.kravets.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInformationServiceImpl implements UserDetalisService {

    @Autowired
    private UserInformationRepository userDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapperUtils objectMapper;

    @Override
    public UserDetailsDTO saveUserDetails(Long idUser, UserDetailsDTO userDetails) {
//        boolean exists = userRepository.existsById(idUser);
//
//        if (!exists) {
//            return null;
//        }

        UserDetailsEntity userDetailsEntity = objectMapper.map(userDetails, UserDetailsEntity.class); //dtoToEntityMapper(userDetails);
        UserEntity userFromDB = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException("User with id [" +idUser+ "] not found"));
        userDetailsEntity.setUser(userFromDB);

        userDetailsRepository.save(userDetailsEntity);
        return userDetails;
    }

    @Override
    public List<UserDetailsDTO> findAllUserDetails() {
        List<UserDetailsEntity> userDetailsEntities = userDetailsRepository.findAll();
        List<UserDetailsDTO> userDetailsDTOS =  objectMapper.mapAll(userDetailsEntities, UserDetailsDTO.class);    //new ArrayList<>();

//        for (UserDetailsEntity detailsEntity : userDetailsEntities) {
//            UserDetailsDTO userDetailsDTO = entityToDtoMapper(detailsEntity);
//            userDetailsDTOS.add(userDetailsDTO);
//        }

        return userDetailsDTOS;
    }

    @Override
    public UserDetailsDTO findUserDetailsByIdUser(Long idUser) {
        boolean exists = userRepository.existsById(idUser);

        if (!exists) {
            throw new NotFoundException("User with id [" +idUser+ "] not found");
        }


        List<UserDetailsEntity> userDetailsEntities = userDetailsRepository.findAll();
        UserDetailsEntity userDetails = new UserDetailsEntity();
        for (UserDetailsEntity detailsEntity : userDetailsEntities) {
            if (detailsEntity.getUser().getId()==idUser){
               userDetails = detailsEntity;
            }
        }

        UserDetailsDTO userDetailsDTO = objectMapper.map(userDetails, UserDetailsDTO.class); // entityToDtoMapper(userDetails);

        return userDetailsDTO;
    }

//    private UserDetailsDTO entityToDtoMapper(UserDetailsEntity userDetailsEntity) {
//        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
//        userDetailsDTO.setDateOfBirth(userDetailsEntity.getDateOfBirth());
//        userDetailsDTO.setEmail(userDetailsEntity.getEmail());
//        userDetailsDTO.setHobby(userDetailsEntity.getHobby());
//        userDetailsDTO.setListOfTechnologies(userDetailsEntity.getListOfTechnologies());
//        userDetailsDTO.setMaritalStatus(userDetailsEntity.getMaritalStatus());
//
//        UserEntity userEntity = userDetailsEntity.getUser();
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(userEntity.getId());
//        userDTO.setFirstName(userEntity.getFirstName());
//        userDTO.setLastName(userEntity.getLastName());
//        userDTO.setNickName(userEntity.getNickName());
//        userDTO.setAccountCreatedDate(userEntity.getAccountCreatedDate());
//
//        userDetailsDTO.setUser(userDTO);
//
//        return userDetailsDTO;
//    }
//
//    private UserDetailsEntity dtoToEntityMapper(UserDetailsDTO userDetailsDTO) {
//        UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
//        userDetailsEntity.setEmail(userDetailsDTO.getEmail());
//        userDetailsEntity.setDateOfBirth(userDetailsDTO.getDateOfBirth());
//        userDetailsEntity.setHobby(userDetailsDTO.getHobby());
//        userDetailsEntity.setListOfTechnologies(userDetailsDTO.getListOfTechnologies());
//        userDetailsEntity.setMaritalStatus(userDetailsDTO.getMaritalStatus());
//
//        return userDetailsEntity;
//    }
}

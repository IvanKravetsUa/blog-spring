package ivan.kravets.service.impl;

import ivan.kravets.domain.CommentDTO;
import ivan.kravets.domain.PostDTO;
import ivan.kravets.domain.UserDTO;
import ivan.kravets.entity.CommentEntity;
import ivan.kravets.entity.PostEntity;
import ivan.kravets.entity.UserEntity;
import ivan.kravets.exceptions.NotFoundException;
import ivan.kravets.repository.CommentRepository;
import ivan.kravets.repository.PostRepository;
import ivan.kravets.repository.UserRepository;
import ivan.kravets.service.CommentService;
import ivan.kravets.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapperUtils objectMapper;

    @Override
    public CommentDTO saveComment(Long idUser, Long idPost, CommentDTO comment) {

        CommentEntity commentEntity = objectMapper.map(comment, CommentEntity.class);   // dtoToEntityMapper(comment);

//        boolean exists = userRepository.existsById(idUser);
//        if (!exists) {
//            return null;
//        }
//
//        boolean existsPost = postRepository.existsById(idPost);
//        if (!existsPost) {
//            return null;
//        }

        UserEntity userFromDB = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException("User with id [" +idUser+ "] not found"));
        commentEntity.setUser(userFromDB);

        PostEntity postFromDB = postRepository.findById(idPost).orElseThrow(() -> new NotFoundException("Post with id [" +idPost+ "] not found"));

        Set<CommentEntity> commentFromDB = postFromDB.getComments();
        commentFromDB.add(commentEntity);
        postFromDB.setComments(commentFromDB);

        postRepository.save(postFromDB);
        commentRepository.save(commentEntity);

        return comment;
    }

    @Override
    public List<CommentDTO> findAllComment() {

        List<CommentEntity> commentEntities = commentRepository.findAll();
        List<CommentDTO> commentDTOS = objectMapper.mapAll(commentEntities, CommentDTO.class); //new ArrayList<>();

//        for (CommentEntity commentEntity : commentEntities) {
//            CommentDTO commentDTO = entityToDtoMapper(commentEntity);
//            commentDTOS.add(commentDTO);
//        }

        return commentDTOS;
    }

//    private CommentDTO entityToDtoMapper(CommentEntity commentEntity) {
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(commentEntity.getId());
//        commentDTO.setBody(commentEntity.getBody());
//
//        UserEntity userEntity = commentEntity.getUser();
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(userEntity.getId());
//        userDTO.setFirstName(userEntity.getFirstName());
//        userDTO.setLastName(userEntity.getLastName());
//        userDTO.setNickName(userEntity.getNickName());
//        userDTO.setAccountCreatedDate(userEntity.getAccountCreatedDate());
//
//        commentDTO.setUser(userDTO);
//
//        Set<PostEntity> postFromDB = commentEntity.getPosts();
//        Set<PostDTO> postDTOS = new HashSet<>();
//
//        for (PostEntity postEntity : postFromDB) {
//            PostDTO postDTO = new PostDTO();
//            postDTO.setId(postEntity.getId());
//            postDTO.setTitle(postEntity.getTitle());
//
//            postDTOS.add(postDTO);
//
//        }
//        commentDTO.setPosts(postDTOS);
//
//        return commentDTO;
//    }

//    private CommentEntity dtoToEntityMapper(CommentDTO commentDTO) {
//        CommentEntity commentEntity = new CommentEntity();
//        commentEntity.setBody(commentDTO.getBody());
//
//        return commentEntity;
//    }
}

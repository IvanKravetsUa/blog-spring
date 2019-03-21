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

import java.util.*;

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


        return commentDTOS;
    }

    @Override
    public CommentDTO findCommentById(Long id) {

        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment with id" + id+ " not found"));
        CommentDTO commentDTO = objectMapper.map(commentEntity, CommentDTO.class);

        return commentDTO;
    }
}

package ivan.kravets.service.impl;

import ivan.kravets.domain.MarkDTO;
import ivan.kravets.entity.CommentEntity;
import ivan.kravets.entity.MarkEntity;
import ivan.kravets.entity.PostEntity;
import ivan.kravets.entity.UserEntity;
import ivan.kravets.exceptions.AlreadyExistsException;
import ivan.kravets.exceptions.NotFoundException;
import ivan.kravets.repository.CommentRepository;
import ivan.kravets.repository.MarkRepository;
import ivan.kravets.repository.PostRepository;
import ivan.kravets.repository.UserRepository;
import ivan.kravets.service.MarkSevice;
import ivan.kravets.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MarkServiceImpl implements MarkSevice {

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapperUtils objectMapper;

    @Override
    public MarkDTO saveLikePost(Long idUser, Long idPost, MarkDTO like) {

        MarkEntity markEntity = objectMapper.map(like, MarkEntity.class);
        PostEntity postFromDB = postRepository.findById(idPost).orElseThrow(() -> new NotFoundException("Post with id["+idPost+"] not found"));
        markEntity.setUser(userRepository.findById(idUser).orElseThrow(() -> new NotFoundException("User with id["+idUser+"] not found")));
        Set<MarkEntity> marksByPost = postFromDB.getMarks();
        for (MarkEntity mark : marksByPost) {
            UserEntity userEntity = mark.getUser();
            Long userIdByDB = userEntity.getId();
            if (userIdByDB == idUser) {
                throw new AlreadyExistsException("This user has already posted like");
            }
        }
        marksByPost.add(markEntity);
        postRepository.save(postFromDB);
        markRepository.save(markEntity);

        if (like.getMarkStatus() == 1) {
            UserEntity userFromDB = postFromDB.getUser();
            Integer reputation = userFromDB.getReputation();
            Integer reputationNew = reputation + 2;
            userFromDB.setReputation(reputationNew);
            userRepository.save(userFromDB);
        } else if (like.getMarkStatus() == 0) {
            UserEntity userFromDB = postFromDB.getUser();
            Integer reputation = userFromDB.getReputation();
            Integer reputationNew = reputation - 2;
            userFromDB.setReputation(reputationNew);
            userRepository.save(userFromDB);
        }
        return like;
    }

    @Override
    public MarkDTO saveLikeComment(Long idUser, Long idComment, MarkDTO like) {

        MarkEntity markEntity = objectMapper.map(like, MarkEntity.class);
        CommentEntity commentFromDB = commentRepository.findById(idComment).orElseThrow(() -> new NotFoundException("Comment with id["+idComment+"] not found"));
        Set<MarkEntity> marksByComment = commentFromDB.getMarks();
        marksByComment.add(markEntity);
        return like;
    }
}

package ivan.kravets.service.impl;

import ivan.kravets.domain.MarkDTO;
import ivan.kravets.entity.CommentEntity;
import ivan.kravets.entity.MarkEntity;
import ivan.kravets.entity.PostEntity;
import ivan.kravets.exceptions.NotFoundException;
import ivan.kravets.repository.CommentRepository;
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
        Set<MarkEntity> marksByPost = postFromDB.getMarks();
        marksByPost.add(markEntity);

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

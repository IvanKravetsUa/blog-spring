package ivan.kravets.service.impl;

import ivan.kravets.domain.MarkDTO;
import ivan.kravets.entity.MarkEntity;
import ivan.kravets.repository.CommentRepository;
import ivan.kravets.repository.PostRepository;
import ivan.kravets.repository.UserRepository;
import ivan.kravets.service.MarkSevice;
import ivan.kravets.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return null;
    }

    @Override
    public MarkDTO saveLikeComment(Long idUser, Long idComment, MarkDTO like) {
        return null;
    }
}

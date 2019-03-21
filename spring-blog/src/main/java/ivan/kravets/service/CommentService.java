package ivan.kravets.service;

import ivan.kravets.domain.CommentDTO;
import ivan.kravets.entity.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    CommentDTO saveComment(Long idUser, Long idPost, CommentDTO comment);

    CommentDTO findCommentById(Long id);

    List<CommentDTO> findAllComment();

}

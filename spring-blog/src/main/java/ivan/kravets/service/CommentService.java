package ivan.kravets.service;

import ivan.kravets.domain.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO saveComment(Long idUser, Long idPost, CommentDTO comment);

    List<CommentDTO> findAllComment();

}

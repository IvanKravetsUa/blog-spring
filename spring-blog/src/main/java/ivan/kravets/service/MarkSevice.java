package ivan.kravets.service;

import ivan.kravets.domain.MarkDTO;

public interface MarkSevice {

    MarkDTO saveLikePost(Long idUser, Long idPost, MarkDTO like);
    MarkDTO saveLikeComment(Long idUser, Long idComment, MarkDTO like);
}

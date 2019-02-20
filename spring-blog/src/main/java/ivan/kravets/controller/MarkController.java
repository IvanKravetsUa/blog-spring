package ivan.kravets.controller;

import ivan.kravets.domain.MarkDTO;
import ivan.kravets.service.MarkSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("marks")
public class MarkController {

    @Autowired
    private MarkSevice markSevice;

    @PostMapping("/{userId:[0-9]}/post/{postId:[0-9]{1,5}}")
    public ResponseEntity<?> saveLikePost(@Valid @PathVariable("userId") Long idUser, @PathVariable("postId") Long idPost, @RequestBody MarkDTO mark) {
        MarkDTO markDTO = markSevice.saveLikePost(idUser, idPost, mark);

        return new ResponseEntity<>(markDTO, HttpStatus.OK);
    }

    @PostMapping("/{userId:[0-9]}/comment/{commentId:[0-9]{1,5}}")
    public ResponseEntity<?> saveLikeComment(@Valid @PathVariable("userId") Long idUser, @PathVariable("commentId") Long idComment, @RequestBody MarkDTO mark) {
        MarkDTO markDTO = markSevice.saveLikeComment(idUser, idComment, mark);

        return new ResponseEntity<>(markDTO, HttpStatus.OK);
    }
}

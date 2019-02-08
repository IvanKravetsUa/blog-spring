package ivan.kravets.controller;

import ivan.kravets.domain.CommentDTO;
import ivan.kravets.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Запис Комента від певного користувача до певного блогу
    @PostMapping("{userId:[0-9]}/post/{postId:[0-9]{1,5}}")
    public ResponseEntity<?> createComment(@PathVariable("userId") Long idUser, @PathVariable("postId") Long idPost, @RequestBody CommentDTO comment) {
        CommentDTO commentDTO = commentService.saveComment(idUser, idPost, comment);

        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getComments() {
        List<CommentDTO> comments = commentService.findAllComment();

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}

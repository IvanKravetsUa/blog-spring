package ivan.kravets.controller;

import ivan.kravets.domain.PostDTO;
import ivan.kravets.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Створення поста від певного користувача
    @PostMapping("/user/{userId:[0-9]{1,5}}")
    public ResponseEntity<?> createPost(@PathVariable("userId") Long id, @RequestBody PostDTO post) {
        PostDTO postDTO = postService.savePost(id, post);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getPosts() {
        List<PostDTO> posts = postService.findAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("{postId:[0-9]{1,5}}")
    public ResponseEntity<?> getPostById(@PathVariable("postId") Long id) {
        PostDTO post = postService.findPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Оновлення обраного поста від користувача який його створив
    @PutMapping("/{postId:[0-9]}/user/{userId:[0-9]{1,5}}")
    public ResponseEntity<?> updatePost(@PathVariable("postId") Long idPost, @PathVariable("userId") Long idUser, @RequestBody PostDTO postToUpdate) {
        PostDTO post = postService.updatePost(idPost, idUser, postToUpdate);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Показ всіх постів певного користувача
    @GetMapping("/user/{userId:[0-9]{1,5}}")
    public ResponseEntity<?> findPostsByUserId(@PathVariable("userId") Long idUser) {
        List<PostDTO> posts = postService.findPostsByUserId(idUser);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}

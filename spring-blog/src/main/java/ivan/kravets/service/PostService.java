package ivan.kravets.service;

import ivan.kravets.domain.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO savePost(Long idUser, PostDTO post);

    List<PostDTO> findAllPosts();

    PostDTO findPostById(Long id);

    PostDTO updatePost(Long idPost, Long idUser, PostDTO postToUpdate);

    List<PostDTO> findPostsByUserId(Long idUser);
}

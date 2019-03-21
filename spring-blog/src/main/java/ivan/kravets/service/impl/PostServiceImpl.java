package ivan.kravets.service.impl;


import ivan.kravets.domain.PostDTO;
import ivan.kravets.domain.TagDTO;
import ivan.kravets.entity.PostEntity;
import ivan.kravets.entity.TagEntity;
import ivan.kravets.entity.UserEntity;
import ivan.kravets.exceptions.NoAccessException;
import ivan.kravets.exceptions.NotFoundException;
import ivan.kravets.repository.PostRepository;
import ivan.kravets.repository.TagRepository;
import ivan.kravets.repository.UserRepository;
import ivan.kravets.service.PostService;
import ivan.kravets.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ObjectMapperUtils objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public PostDTO savePost(Long idUser, PostDTO post) {

        PostEntity postEntity = objectMapper.map(post, PostEntity.class);

        UserEntity userEntity = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException("User with id [" +idUser+ "] not found"));
        postEntity.setUser(userEntity);
        postRepository.save(postEntity);
        return post;
    }

    @Override
    public List<PostDTO> findAllPosts() {
        List<PostEntity> posts = postRepository.findAll();
        List<PostDTO> postDTOS = objectMapper.mapAll(posts, PostDTO.class);

        return postDTOS;
    }

    @Override
    public PostDTO findPostById(Long id) {

        PostEntity postEntity = postRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id [" +id+ "] not found"));
        PostDTO postDTO = objectMapper.map(postEntity, PostDTO.class); //entityToDTOMapper(postEntity);

        return postDTO;
    }

    @Override
    public PostDTO updatePost(Long idPost, Long idUser, PostDTO postToUpdate) {
//        boolean exists = postRepository.existsById(idPost);
//
//        if (!exists) {
//            return null;
//        }

        PostEntity postFromDB = postRepository.findById(idPost).orElseThrow(() -> new NotFoundException("Post with id [" +idPost+ "] not found"));

        if (idUser != postFromDB.getUser().getId()) {
            throw new NoAccessException("User with id [" +idUser+ "] no access");
        }

        PostEntity postEntityToUpdate = objectMapper.map(postToUpdate, PostEntity.class); //dtoToEntityMapper(postToUpdate);
        postFromDB.setTitle(postEntityToUpdate.getTitle());
        postFromDB.setDescription(postEntityToUpdate.getDescription());

        postRepository.save(postFromDB);

        return postToUpdate;
    }

    @Override
    public List<PostDTO> findPostsByUserId(Long idUser) {

        List<PostEntity> posts = postRepository.findAll();
        List<PostDTO> postDTOS = objectMapper.mapAll(posts, PostDTO.class); //new ArrayList<>();

        return postDTOS;
    }

    @Override
    public Page<PostEntity> getUsersByPage(Pageable pageable) {
        Page<PostEntity> postFromDB = postRepository.findAll(pageable); // page = 0 size = 10

        return postFromDB;
    }

    @Override
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)){
            throw new NotFoundException("Post with id[" + id + "] not found");
        }
        postRepository.deleteById(id);
    }

    @Override
    public void addImageToPost(Long id, String fileName) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        postEntity.setImage(fileName);
        postRepository.save(postEntity);
    }


}

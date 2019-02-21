package ivan.kravets.service.impl;


import ivan.kravets.domain.PostDTO;
import ivan.kravets.entity.PostEntity;
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


import java.util.List;

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
//        boolean exists = postRepository.existsByTitleIgnoreCase(post.getTitle());
//
//        if (exists) {
//            return null;
//        }

        PostEntity postEntity = objectMapper.map(post, PostEntity.class); //dtoToEntityMapper(post);
        UserEntity userEntity = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException("User with id [" +idUser+ "] not found"));
        postEntity.setUser(userEntity);

        postRepository.save(postEntity);
        return post;
    }

    @Override
    public List<PostDTO> findAllPosts() {
        List<PostEntity> posts = postRepository.findAll();
        List<PostDTO> postDTOS = objectMapper.mapAll(posts, PostDTO.class); //new ArrayList<>();

//        for (PostEntity postEntity : posts) {
//            PostDTO postDTO = entityToDTOMapper(postEntity);
//            postDTOS.add(postDTO);
//        }

        return postDTOS;
    }

    @Override
    public PostDTO findPostById(Long id) {
//        boolean exists = postRepository.existsById(id);
//
//        if (!exists) {
//            return null;
//        }

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

//        for (PostEntity postEntity : posts) {
//            if (postEntity.getUser().getId().equals(idUser)) {
//                PostDTO postDTO = entityToDTOMapper(postEntity);
//                postDTOS.add(postDTO);
//            }
//        }

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

    //    public PostDTO entityToDTOMapper(PostEntity postEntity) {
//        PostDTO postDTO = new PostDTO();
//        postDTO.setId(postEntity.getId());
//        postDTO.setTitle(postEntity.getTitle());
//        postDTO.setDescription(postEntity.getDescription());
//        postDTO.setCreatedDate(postEntity.getCreatedDate());
//
//        UserEntity userEntity = postEntity.getUser();
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(userEntity.getId());
//        userDTO.setFirstName(userEntity.getFirstName());
//        userDTO.setLastName(userEntity.getLastName());
//        userDTO.setNickName(userEntity.getNickName());
//        userDTO.setAccountCreatedDate(userEntity.getAccountCreatedDate());
//
//        postDTO.setUser(userDTO);
//
//        Set<TagEntity> tagsEntity = postEntity.getTags();
//        Set<TagDTO> tagDTOS = new HashSet<>();
//        for (TagEntity tagEntity : tagsEntity) {
//            TagDTO tagDTO = new TagDTO();
//            tagDTO.setId(tagEntity.getId());
//            tagDTO.setName(tagEntity.getName());
//
//            Set<PostDTO> postDTOS = new HashSet<>();
//
//            for (PostEntity tagEntityPost : tagEntity.getPosts()) {
//                PostDTO postDTO1 = entityToDTOMapper(tagEntityPost);
//                postDTOS.add(postDTO1);
//            }
//            tagDTO.setPosts(postDTOS);
//
//            tagDTOS.add(tagDTO);
//        }
//
//        postDTO.setTags(tagDTOS);
//
//        Set<CommentEntity> commentEntities = postEntity.getComments();
//        Set<CommentDTO> commentDTOS = new HashSet<>();
//        for (CommentEntity commentEntity : commentEntities) {
//            CommentDTO commentDTO = new CommentDTO();
//            commentDTO.setId(commentEntity.getId());
//            commentDTO.setBody(commentEntity.getBody());
//
//            UserEntity userEntityComment = postEntity.getUser();
//            UserDTO userDtoComment = new UserDTO();
//            userDtoComment.setId(userEntityComment.getId());
//            userDtoComment.setFirstName(userEntityComment.getFirstName());
//            userDtoComment.setLastName(userEntityComment.getLastName());
//            userDtoComment.setNickName(userEntityComment.getNickName());
//            userDtoComment.setAccountCreatedDate(userEntity.getAccountCreatedDate());
//            commentDTO.setUser(userDtoComment);
//
//            commentDTOS.add(commentDTO);
//        }
//
//        postDTO.setComments(commentDTOS);
//
//        return postDTO;
//    }

//    private PostEntity dtoToEntityMapper(PostDTO postDTO) {
//        PostEntity postEntity = new PostEntity();
//        postEntity.setId(postDTO.getId());
//        postEntity.setTitle(postDTO.getTitle());
//        postEntity.setDescription(postDTO.getDescription());
//        postEntity.setCreatedDate(postDTO.getCreatedDate());

//        UserDTO userDTO = postDTO.getUser();
//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(userDTO.getId());
//        userEntity.setFirstName(userDTO.getFirstName());
//        userEntity.setLastName(userDTO.getLastName());
//        userEntity.setNickName(userDTO.getNickName());
//        userEntity.setAccountCreatedDate(userDTO.getAccountCreatedDate());
//
//        postEntity.setUser(userEntity);

//        Set<TagDTO> tagsDTOS = postDTO.getTags();
//        Set<TagEntity> tagEntities = new HashSet<>();
//        for (TagDTO tagDTO : tagsDTOS) {
//            TagEntity tagEntity = new TagEntity();
//            tagEntity.setId(tagDTO.getId());
//            tagEntity.setName(tagDTO.getName());
//
//            Set<PostEntity> postEntities = new HashSet<>();
//
//            for (PostDTO tagDTOPost : tagDTO.getPosts()) {
//                PostEntity postEntity1 = dtoToEntityMapper(tagDTOPost);
//                postEntities.add(postEntity1);
//            }
//            tagEntity.setPosts(postEntities);
//
//            tagEntities.add(tagEntity);
//        }
//
//        postEntity.setTags(tagEntities);
//
//        Set<CommentDTO>commentDTOS = postDTO.getComments();
//        Set<CommentEntity> commentEntities = new HashSet<>();
//        for (CommentDTO commentDTO : commentDTOS) {
//            CommentEntity commentEntity = new CommentEntity();
//            commentEntity.setId(commentDTO.getId());
//            commentEntity.setBody(commentDTO.getBody());
//
//            Set<PostEntity> postEntities = new HashSet<>();
//
//            for (PostDTO commentDTOPost : commentDTO.getPosts()) {
//                PostEntity postEntity1 = dtoToEntityMapper(commentDTOPost);
//                postEntities.add(postEntity1);
//            }
//            commentEntity.setPosts(postEntities);
//
//            commentEntities.add(commentEntity);
//        }
//
//        postEntity.setComments(commentEntities);
//
//        return postEntity;
//    }
}

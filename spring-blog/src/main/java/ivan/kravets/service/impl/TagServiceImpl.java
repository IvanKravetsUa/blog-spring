package ivan.kravets.service.impl;


import ivan.kravets.domain.TagDTO;
import ivan.kravets.entity.TagEntity;
import ivan.kravets.exceptions.AlreadyExistsException;
import ivan.kravets.exceptions.NotFoundException;
import ivan.kravets.repository.TagRepository;
import ivan.kravets.service.TagService;
import ivan.kravets.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ObjectMapperUtils objectMapper;

    @Override
    public TagDTO saveTag(TagDTO tag) {
        boolean exists = tagRepository.existsByName(tag.getName());

        if (exists) {
            throw new AlreadyExistsException("Tag with name" +tag.getName()+ "] already exists");
        }

        TagEntity tagEntity = objectMapper.map(tag, TagEntity.class);  //dtoToEntityMapper(tag);
        tagRepository.save(tagEntity);
        tag.setId(tagEntity.getId());
        return tag;
    }

    @Override
    public List<TagDTO> findAllTags() {

        List<TagEntity> tagEntities = tagRepository.findAll();
        List<TagDTO> tagDTOS = objectMapper.mapAll(tagEntities, TagDTO.class);   //new ArrayList<>();

//        for (TagEntity tagEntity : tagEntities) {
//            TagDTO tagDTO = entityToDtoMapper(tagEntity);
//            tagDTOS.add(tagDTO);
//
//        }
        return tagDTOS;
    }

    @Override
    public TagDTO findById(Long id) {
//        boolean exists = tagRepository.existsById(id);
//
//        if (!exists) {
//            return null;
//        }

        TagEntity tagEntity = tagRepository.findById(id).orElseThrow(() -> new NotFoundException("Tag with id [" +id+ "] not found"));
        TagDTO tagDTO = objectMapper.map(tagEntity, TagDTO.class); //entityToDtoMapper(tagEntity);

        return tagDTO;
    }

//    private TagDTO entityToDtoMapper(TagEntity tagEntity) {
//        TagDTO tagDTO = new TagDTO();
//        tagDTO.setId(tagEntity.getId());
//        tagDTO.setName(tagEntity.getName());
//
//        return tagDTO;
//    }
//
//    private TagEntity dtoToEntityMapper(TagDTO tagDTO) {
//        TagEntity tagEntity = new TagEntity();
//        tagEntity.setId(tagDTO.getId());
//        tagEntity.setName(tagDTO.getName());
//
//        return tagEntity;
//    }
}

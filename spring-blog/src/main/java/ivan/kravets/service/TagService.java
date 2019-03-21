package ivan.kravets.service;

import ivan.kravets.domain.TagDTO;

import java.util.List;

public interface TagService {

    TagDTO saveTag(Long id, TagDTO tag);

    List<TagDTO> findAllTags();

    TagDTO findById(Long id);
}

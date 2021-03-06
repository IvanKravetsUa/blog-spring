package ivan.kravets.controller;

import ivan.kravets.domain.TagDTO;
import ivan.kravets.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/{postId:[0-9]{1,5}}")
    public ResponseEntity<?> createTag(@Valid @PathVariable ("postId") Long id, @RequestBody TagDTO tag) {
        TagDTO tagDTO = tagService.saveTag(id, tag);
        return new ResponseEntity<>(tagDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getTags() {
        List<TagDTO> tags = tagService.findAllTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("{tagId:[0-9]{1,5}}")
    public ResponseEntity<?> getTagById(@PathVariable("tagId") Long id) {
        TagDTO tagDTO = tagService.findById(id);
        return new ResponseEntity<>(tagDTO, HttpStatus.OK);
    }
}

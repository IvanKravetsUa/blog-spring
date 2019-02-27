package ivan.kravets.controller;

import ivan.kravets.domain.UserDTO;
import ivan.kravets.service.FileStorageService;
import ivan.kravets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")  // localhost:8080/users
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO user) {
        UserDTO userDTO = userService.saveUser(user);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED); //201
    }

    @GetMapping("{userId:[0-9]{1,5}}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long id) {
        UserDTO userDTO = userService.findUserById(id);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PutMapping("{userId:[0-9]{1,5}}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long id, @RequestBody UserDTO userToUpdate) {
        UserDTO user = userService.updateUser(id, userToUpdate);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("{userId}/image")
    public ResponseEntity<?> uploadImage(
            @PathVariable("userId") Long id,
            @RequestParam("imageFile")MultipartFile file) {

        System.out.println(file.getOriginalFilename());

        fileStorageService.storeFile(file);
        userService.addImageToUser(id, file.getOriginalFilename());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("image")
    public ResponseEntity<?> getImage(
            @RequestParam("imageName") String name,
            HttpServletRequest servletRequest
    ) {

        Resource resource = fileStorageService.loadFile(name);

        String contentType = null;

        try {
            contentType = servletRequest
                    .getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()  //inline: filename = "file.png"
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\""+ resource.getFilename() + "\'")
                .body(resource);
    }
}

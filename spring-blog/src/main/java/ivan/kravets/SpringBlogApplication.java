package ivan.kravets;

import ivan.kravets.entity.PostEntity;
import ivan.kravets.entity.RoleEntity;
import ivan.kravets.entity.UserEntity;
import ivan.kravets.repository.PostRepository;
import ivan.kravets.repository.RoleRepository;
import ivan.kravets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class SpringBlogApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBlogApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.count() == 0) {
            RoleEntity roleAdmin = new RoleEntity();
            roleAdmin.setRole("ADMIN");

            RoleEntity roleUser = new RoleEntity();
            roleUser.setRole("USER");
            roleRepository.saveAll(Arrays.asList(roleAdmin, roleUser));
        }

        if (userRepository.count() == 0) {

            RoleEntity roleAdmin = roleRepository.findByRoleIgnoreCase("ADMIN").get();
            RoleEntity roleUser = roleRepository.findByRoleIgnoreCase("USER").get();


            UserEntity adminEntity = new UserEntity();
            adminEntity.setEmail("admin@gmail.com");
            adminEntity.setFirstName("ivan");
            adminEntity.setLastName("kravets");
            adminEntity.setPassword(passwordEncoder.encode("123456"));
            adminEntity.setSex("MALE");
            adminEntity.setReputation(1);
            adminEntity.setRoles(Arrays.asList(roleAdmin));

            userRepository.save(adminEntity);

            for (long i = 0; i < 50; i++) {
                UserEntity userEntity = new UserEntity();
                userEntity.setEmail("user" +i+"@gmail.com");
                userEntity.setFirstName("userfirstName" + i);
                userEntity.setLastName("userLastName" +i);
                userEntity.setPassword(passwordEncoder.encode("123456"));
                userEntity.setSex("MALE");
                userEntity.setReputation(2);
                userEntity.setRoles(Arrays.asList(roleUser));

                userRepository.save(userEntity);
            }
        }

        if (postRepository.count() == 0) {
            for (long i = 1; i<50; i++) {
                PostEntity postEntity = new PostEntity();
                postEntity.setTitle("titlePost+Id" +i);
                postEntity.setDescription("descriptionPost+id" + i + " ] Есть много вариантов Lorem Ipsum, но большинство из них имеет не всегда приемлемые модификации, например, юмористические вставки или слова, которые даже отдалённо не напоминают латынь. Если вам нужен Lorem Ipsum для серьёзного проекта, вы наверняка не хотите какой-нибудь шутки, скрытой в середине абзаца. Также все другие известные генераторы Lorem Ipsum ");
                UserEntity userEntity = userRepository.findById(i).get();
                postEntity.setUser(userEntity);
                postRepository.save(postEntity);
            }
        }
    }
}


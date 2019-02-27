package ivan.kravets;

import ivan.kravets.entity.PostEntity;
import ivan.kravets.entity.UserEntity;
import ivan.kravets.repository.PostRepository;
import ivan.kravets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {

            UserEntity adminEntity = new UserEntity();
            adminEntity.setEmail("admin@gmail.com");
            adminEntity.setFirstName("ivan");
            adminEntity.setLastName("kravets");
            adminEntity.setPassword("1234567");
            adminEntity.setSex("MALE");
            adminEntity.setReputation(1);

            userRepository.save(adminEntity);

            for (long i = 0; i < 50; i++) {
                UserEntity userEntity = new UserEntity();
                userEntity.setEmail("user" +i+"@gmail.com");
                userEntity.setFirstName("userfirstName" + i);
                userEntity.setLastName("userLastName" +i);
                userEntity.setPassword("1234567");
                userEntity.setSex("MALE");
                userEntity.setReputation(1);

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


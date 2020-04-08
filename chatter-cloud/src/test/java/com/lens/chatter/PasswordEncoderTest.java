package com.lens.chatter;

import com.lens.chatter.model.entity.User;
import com.lens.chatter.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static com.lens.chatter.constant.Role.BASIC_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by Emir Gökdemir
 * on 18 Şub 2020
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PasswordEncoderTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void passwordEncodeTest() {
        // given
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        User user=new User();
        user.setEmail("a@gmail.com");
        user.setName("Emir");
        user.setSurname("Gokdemir");
        user.setRole(BASIC_USER);
        user.setPassword(bCryptPasswordEncoder.encode("password"));
        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findUserByName(user.getName());

        // then
        assertThat(found.getName())
                .isEqualTo(user.getName());
    }

    // write test cases here

}

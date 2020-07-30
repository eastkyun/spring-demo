package com.community.domain;

import com.community.domain.emuns.BoardType;
import com.community.repository.BoardRepository;
import com.community.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaMappingTest {

    private final String email = "test@test.com";

    private final String boardTestTitle = "게시판 시험용 제목";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;


    @Before // 각 테스트가 실행되기 전에 실행될 메서드를 선언합니다
    public void init(){
        User user = userRepository.save(User.builder()
                                    .name("kang")
                                    .password("test")
                                    .email(email)
                                    .createdDate(LocalDateTime.now())
                                    .updatedDate(LocalDateTime.now())
                                    .build());

        boardRepository.save(Board.builder()
        .title(boardTestTitle)
        .subTitle("서브 타이틀")
        .content("콘텐츠")
        .boardType(BoardType.free)
        .createdDate(LocalDateTime.now())
        .updatedDate(LocalDateTime.now())
                .user(user)
        .build());
    }

    @Test
    public void 제대로_생성됐는지_테스트(){
        User user = userRepository.findByEmail(email);
        assertThat(user.getName(), is("kang"));
        assertThat(user.getPassword(), is("test"));
        assertThat(user.getEmail(), is(email));

        Board board = boardRepository.findByUser(user);
        assertThat(board.getTitle(), is(boardTestTitle));
        assertThat(board.getSubTitle(), is("서브 타이틀"));
        assertThat(board.getContent(), is("콘텐츠"));
//        assertThat(board.getBoardType(), is(BoardType.free));

    }
}

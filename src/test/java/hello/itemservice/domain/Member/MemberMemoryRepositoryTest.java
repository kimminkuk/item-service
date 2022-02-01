package hello.itemservice.domain.Member;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Fail;
import org.assertj.core.error.ShouldNotExist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberMemoryRepositoryTest {
    MemberMemoryRepository testRepository = new MemberMemoryRepository();

    @AfterEach
    public void afterEach() {
        testRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member = new Member("rlaal567");

        //when
        testRepository.save(member);

        //then
        Member result = testRepository.findById(member.getMemberId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    void findById() {
        //given
        Member member1 = new Member("rlaal567");
        Member member2 = new Member("rlaal123");
        testRepository.save(member1);
        testRepository.save(member2);

        //when
        Member findMember = testRepository.findById(member1.getMemberId()).get();

        //then
        assertThat(findMember).isEqualTo(member1);
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member("rlaal567");
        Member member2 = new Member("rlaal123");
        testRepository.save(member1);
        testRepository.save(member2);

        //when
        List<Member> result = testRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findByName() {
        //given
        Member member1 = new Member("rlaal567");
        Member member2 = new Member("rlaal123");
        testRepository.save(member1);
        testRepository.save(member2);

        //when
        Member findMember = testRepository.findByName("rlaal123").get();

        //then
        assertThat(findMember).isEqualTo(member2);
    }
}
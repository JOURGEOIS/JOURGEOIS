package com.jourgeois.backend.model;

import com.jourgeois.backend.domain.Member;
import com.jourgeois.backend.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class MemberService {

    MemberRepository memberRepository;

    public void createUser(Member m) throws Exception {
        memberRepository.save(m);
    }

    // Dummy Data 생성
    public void makeDummyData(EntityManager em){
        em.persist(new Member("1", "1234", "전승준", "paasasd", "jsznawa@Naver.com", "1997-12-26", "a.img", "안녕하세요 전 승준입니다."));
    }

    public void readUser(Member m) throws Exception {

    }

    public void updateUser(Member m) throws Exception {

    }

    public void deleteUser(Member m) throws Exception {

    }
}

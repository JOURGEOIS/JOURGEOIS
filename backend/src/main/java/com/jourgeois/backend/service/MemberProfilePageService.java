package com.jourgeois.backend.service;

import com.jourgeois.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberProfilePageService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberProfilePageService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Object readMemberProfile(){
//        memberRepository;

        return null;
    }

}

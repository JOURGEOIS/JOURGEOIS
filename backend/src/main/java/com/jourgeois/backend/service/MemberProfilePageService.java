package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.member.MemberDTO;
import com.jourgeois.backend.api.dto.member.MemberVO;
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

    public MemberDTO readMemberProfile(MemberDTO memberDTO){
        MemberVO memberVO = memberRepository.findMemberProfile(memberDTO.getUid()).orElseThrow();

        System.out.println("heeeklekdl");
        MemberDTO m = memberDTO.builder().uid(memberVO.getUid()).email(memberVO.getEmail()).nickname(memberVO.getNickname())
                .profileImg(memberVO.getProfileImg()).introduce(memberVO.getIntroduce()).followerCnt(memberVO.getFollowerCnt())
                .followingCnt(memberDTO.getFollowingCnt()).postCnt(memberDTO.getPostCnt()).build();

        System.out.println("키보드왔당");
        return m;
    }

}

package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.ProfileDto;
import com.jourgeois.backend.api.dto.TokenResponseDto;
import com.jourgeois.backend.domain.Member;
import com.jourgeois.backend.domain.auth.RefreshToken;
import com.jourgeois.backend.repository.MemberRepository;
import com.jourgeois.backend.repository.auth.RefreshTokenRepository;
import com.jourgeois.backend.security.MyUserDetailsService;
import com.jourgeois.backend.security.jwt.JwtTokenProvider;
import org.hibernate.annotations.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MyUserDetailsService myUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    MemberService(MemberRepository memberRepository,
                  PasswordEncoder passwordEncoder,
                  JwtTokenProvider jwtTokenProvider,
                  MyUserDetailsService myUserDetailsService,
                  RefreshTokenRepository refreshTokenRepository){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.myUserDetailsService = myUserDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void createUser(Member m) throws Exception {
        validateDuplicateUser(m.getEmail());
        m.setPassword(passwordEncoder.encode(m.getPassword()));
        memberRepository.save(m);
    }

    @Transactional
    public boolean changeProfile(ProfileDto data){
        memberRepository.findByNicknameAndEmailIsNot(data.getNickname(), data.getEmail())
                .ifPresentOrElse(
                        (member -> {throw new IllegalArgumentException("닉네임 중복");}),
                        ()->memberRepository.findByEmail(data.getEmail())
                                .ifPresent(member -> {
                                    member.setIntroduce(data.getIntroduce());
                                    member.setProfileImg(data.getProfileImg());
                                    member.setNickname(data.getNickname());
                                    memberRepository.save(member);
                                })
                );
        return true;
    }

    @Transactional
    public TokenResponseDto signIn(String userId, String pw) {
        // uesrId 확인
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(userId);

        // pw 확인
        if(!passwordEncoder.matches(pw, userDetails.getPassword())){
            throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");
        }

        Authentication authentication =  new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        // refresh token 발급 및 저장
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
        RefreshToken token = RefreshToken.createToken(userId, refreshToken);
        System.out.println(refreshToken + " " + token);
        // 기존 토큰이 있으면 수정, 없으면 생성
        refreshTokenRepository.findByEmail(userId)
                .ifPresentOrElse(
                        (tokenEntity)->tokenEntity.changeToken(refreshToken),
                        ()->refreshTokenRepository.save(RefreshToken.createToken(userId, refreshToken))
                );

        return TokenResponseDto.builder()
                .accessToken("Bearer-"+jwtTokenProvider.createAccessToken(authentication))
                .refreshToken("Bearer-"+refreshToken)
                .build();
    }

    @Transactional
    public Optional<Member> findUserInfo(String userId){
        return memberRepository.findByEmail(userId);
    }

    @Transactional
    public void logout(String email){
        refreshTokenRepository.deleteByEmail(email);
        System.out.println("토큰 삭제 완료");
    }

    private void validateDuplicateUser(String email){
        System.out.println(email + " email");
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
//                    log.debug("email : {}, 이메일 중복으로 회원가입 실패", email);
                    throw new RuntimeException("이메일 중복");
                });
    }

    // Dummy Data 생성
//    public void makeDummyData(EntityManager em){
//        em.persist(new Member("1", "1234", "전승준", "paasasd", "jsznawa@Naver.com", "1997-12-26", "a.img", "안녕하세요 전 승준입니다."));
//    }

    public void readUser(Member m) throws Exception {

    }

    public void updateUser(Member m) throws Exception {

    }

    public void deleteUser(Member m) throws Exception {

    }
}

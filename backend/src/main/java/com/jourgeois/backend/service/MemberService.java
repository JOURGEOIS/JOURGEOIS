package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.ProfileDto;
import com.jourgeois.backend.api.dto.PasswordChangeForm;
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

    public boolean signUp(Member m) throws Exception {
        try {
            validateDuplicateUser(m.getEmail());
            m.setPassword(passwordEncoder.encode(m.getPassword()));
            memberRepository.save(m);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
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

    public boolean checkEmail(String email){
        Optional<Member> result = memberRepository.findByEmail(email);
        // 중복된 이메일이 없다면 사용 가능 return
        if(result.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkNickname(String nickname){
        Member result = memberRepository.findByNickname(nickname);
        // 중복된 닉네임이 없다면 사용 가능 return
        if(result==null) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public TokenResponseDto login(String email, String password) {
        // userId 확인
        System.out.println("3333331231231231312");
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(email);

        System.out.println("111111111");
        // pw 확인
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");
        }

        Authentication authentication =  new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        System.out.println("222222222");

        // refresh token 발급 및 저장
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
        RefreshToken token = RefreshToken.createToken(email, refreshToken);
        System.out.println(refreshToken + " " + token);
        // 기존 토큰이 있으면 수정, 없으면 생성
        refreshTokenRepository.findByEmail(email)
                .ifPresentOrElse(
                        (tokenEntity)->tokenEntity.changeToken(refreshToken),
                        ()->refreshTokenRepository.save(RefreshToken.createToken(email, refreshToken))
                );

        System.out.println("33333333333333333");

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
    public void readUser(Member m) throws Exception {

    }

    public void updateUser(Member m) throws Exception {

    }

    public void deleteUser(Member m) throws Exception {

    }

    @Transactional
    public void changePassword(PasswordChangeForm passwordChangeForm) throws IllegalArgumentException {
        String email = passwordChangeForm.getUserId();
        memberRepository.findByEmail(email)
                .ifPresentOrElse((member)-> {
                            if(passwordEncoder.matches(passwordChangeForm.getPasswordOld(), member.getPassword())) {
                                member.setPassword(passwordEncoder.encode(passwordChangeForm.getPasswordNew()));
                                memberRepository.save(member);
                            } else {
                                throw new IllegalArgumentException("기존 비밀번호와 일치하지 않습니다.");
                            }
                        },
                        () -> {throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");});
    }

    @Transactional
    public void findPassword(PasswordChangeForm passwordChangeForm) throws IllegalArgumentException {
        String email = passwordChangeForm.getUserId();
        memberRepository.findByEmail(email)
                .ifPresentOrElse((member)-> {
                            if(passwordChangeForm.getPasswordNew().equals(passwordChangeForm.getPasswordConfirm())) {
                                member.setPassword(passwordEncoder.encode(passwordChangeForm.getPasswordNew()));
                                memberRepository.save(member);
                            } else {
                                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                            }
                        },
                        () -> {throw new IllegalArgumentException("가입 정보가 없습니다.");}
                );
    }

}

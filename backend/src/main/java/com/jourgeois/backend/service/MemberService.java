package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.ProfileDTO;
import com.jourgeois.backend.api.dto.PasswordChangeForm;
import com.jourgeois.backend.api.dto.TokenResponseDTO;
import com.jourgeois.backend.domain.Member;
import com.jourgeois.backend.domain.auth.RefreshToken;
import com.jourgeois.backend.repository.MemberRepository;
import com.jourgeois.backend.repository.auth.RefreshTokenRepository;
import com.jourgeois.backend.security.MyUserDetailsService;
import com.jourgeois.backend.security.jwt.JwtTokenProvider;
import com.jourgeois.backend.util.ImgType;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MyUserDetailsService myUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final S3Util s3Util;
    private final String s3Url;
    @Autowired
    MemberService(MemberRepository memberRepository,
                  PasswordEncoder passwordEncoder,
                  JwtTokenProvider jwtTokenProvider,
                  MyUserDetailsService myUserDetailsService,
                  RefreshTokenRepository refreshTokenRepository,
                  S3Util s3Util,
                  @Value("${cloud.aws.s3.bucket.path}") String s3Url){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.myUserDetailsService = myUserDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.s3Util = s3Util;
        this.s3Url = s3Url;
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
    public boolean changeProfile(ProfileDTO data){
        memberRepository.findByNicknameAndUidIsNot(data.getNickname(), data.getUid())
                .ifPresentOrElse(
                        (member -> {throw new IllegalArgumentException("닉네임 중복");}),
                        ()->memberRepository.findById(data.getUid())
                                .ifPresent(member -> {
                                    member.setName(data.getName());
                                    member.setIntroduce(data.getIntroduce());
                                    member.setNickname(data.getNickname());
                                    try{
                                        if(data.getProfileLink()!=null && !data.getProfileLink().isEmpty()){
                                            if(!member.getProfileImg().equals("default/1.png")) {
                                                s3Util.deleteFile(member.getProfileImg());
                                            }
                                            member.setProfileImg(s3Util.upload(data.getProfileLink(), member.getUid(), ImgType.PROFILE));
                                        }
                                    } catch (IOException e){
                                        throw new IllegalArgumentException("이미지 업로드 오류");
                                    } finally {
                                        System.out.println("before finally");
                                        memberRepository.save(member);
                                        System.out.println("after finally");
                                    }
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

    public UserDetails loginUser(String email, String password){
        Member member = memberRepository.findByEmail(email).get();
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(member.getUid().toString());

        // pw 확인
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");
        }
        return userDetails;
    }
    @Transactional
    public TokenResponseDTO createToken(UserDetails userDetails) {
        Authentication authentication =  new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        // refresh token 발급 및 저장
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
        RefreshToken token = RefreshToken.createToken(Long.valueOf(userDetails.getUsername()), refreshToken);
        System.out.println(refreshToken + " " + token);

        // 기존 토큰이 있으면 수정, 없으면 생성
        refreshTokenRepository.findByUid(Long.valueOf(userDetails.getUsername()))
                .ifPresentOrElse(
                        (tokenEntity)->tokenEntity.changeToken(refreshToken),
                        ()->refreshTokenRepository.save(RefreshToken.createToken(Long.valueOf(userDetails.getUsername()), refreshToken))
                );

        return TokenResponseDTO.builder()
                .accessToken("Bearer-"+jwtTokenProvider.createAccessToken(authentication))
                .refreshToken("Bearer-"+refreshToken)
                .build();
    }

    @Transactional
    public ProfileDTO findUserInfo(Long uid){
        Member member = memberRepository.findById(uid).get();
        ProfileDTO p = new ProfileDTO(member.getUid(), member.getEmail(), member.getName(),
                member.getNickname(), member.getProfileImg(), member.getIntroduce());
        return p;

    }

    @Transactional
    public void logout(Long uid){
        refreshTokenRepository.deleteByUid(uid);
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

    @Transactional
    public void signOut(Long uid, String email) throws NoSuchElementException{
        Optional<Member> member = memberRepository.findByUidAndEmail(uid, email);
        member.ifPresentOrElse(selectMember -> {
            refreshTokenRepository.deleteByUid(selectMember.getUid());
            String userProfile = selectMember.getProfileImg();
            if(!userProfile.equals("default/1.png"))
                s3Util.deleteFile(userProfile);
            memberRepository.delete(selectMember);
        }, () -> {
            throw new NoSuchElementException("가입된 회원이 아닙니다.");
        });
        System.out.println("회원 탈퇴 완료");
    }

    @Transactional
    public void changePassword(PasswordChangeForm passwordChangeForm) throws IllegalArgumentException, NoSuchElementException {
        Long uid = passwordChangeForm.getUid();
        memberRepository.findById(uid)
                .ifPresentOrElse((member)-> {
                            if(passwordChangeForm.getPasswordNew().equals(passwordChangeForm.getPasswordConfirm())) {
                                member.setPassword(passwordEncoder.encode(passwordChangeForm.getPasswordNew()));
                                memberRepository.save(member);
                            } else {
                                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                            }
                        },
                        () -> {throw new NoSuchElementException("존재하지 않는 회원 입니다.");});
    }

    @Transactional
    public boolean checkPassword(PasswordChangeForm passwordChangeForm) throws NoSuchElementException {
        Long uid = passwordChangeForm.getUid();
        Member member = memberRepository.findById(uid).get();
        return passwordEncoder.matches(passwordChangeForm.getPasswordOld(), member.getPassword());
    }

    @Transactional
    public void findPassword(PasswordChangeForm passwordChangeForm) throws IllegalArgumentException, NoSuchElementException {
        String email = passwordChangeForm.getEmail();
        memberRepository.findByEmail(email)
                .ifPresentOrElse((member)-> {
                            if(passwordChangeForm.getPasswordNew().equals(passwordChangeForm.getPasswordConfirm())) {
                                member.setPassword(passwordEncoder.encode(passwordChangeForm.getPasswordNew()));
                                memberRepository.save(member);
                            } else {
                                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                            }
                        },
                        () -> {throw new NoSuchElementException("가입 정보가 없습니다.");}
                );
    }

    public boolean checkUser(String email, String userName) {
        return memberRepository.findByEmailAndName(email, userName).isPresent();
    }

    public boolean checkUserUid(Long uid){
        return memberRepository.findById(uid).isPresent();
    }

    @Transactional
    public String ProfileImageLocalUpload(ProfileDTO profileDto) throws IOException {
        Member member = memberRepository.findById(profileDto.getUid()).get();
        String url = s3Util.localUpload(profileDto.getProfileLink(), member.getUid(), ImgType.PROFILE);
        return url;
    }
}

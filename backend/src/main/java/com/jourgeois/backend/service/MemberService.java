package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.member.FollowerDTO;
import com.jourgeois.backend.api.dto.member.FollowerVO;
import com.jourgeois.backend.api.dto.member.ProfileDTO;
import com.jourgeois.backend.api.dto.member.PasswordChangeForm;
import com.jourgeois.backend.api.dto.auth.TokenResponseDTO;
import com.jourgeois.backend.domain.member.Follow;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.auth.RefreshToken;
import com.jourgeois.backend.repository.FollowRepository;
import com.jourgeois.backend.repository.MemberRepository;
import com.jourgeois.backend.repository.auth.RefreshTokenRepository;
import com.jourgeois.backend.security.MyUserDetailsService;
import com.jourgeois.backend.security.jwt.JwtTokenProvider;
import com.jourgeois.backend.socialLogin.GoogleLoginDTO;
import com.jourgeois.backend.util.ImgType;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MyUserDetailsService myUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final S3Util s3Util;
    private final String s3Url;


    @Autowired
    MemberService(MemberRepository memberRepository, FollowRepository followRepository, PasswordEncoder passwordEncoder,
                  JwtTokenProvider jwtTokenProvider,
                  MyUserDetailsService myUserDetailsService,
                  RefreshTokenRepository refreshTokenRepository,
                  S3Util s3Util,
                  @Value("${cloud.aws.s3.bucket.path}") String s3Url){
        this.memberRepository = memberRepository;
        this.followRepository = followRepository;
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
                                            if(!member.getProfileImg().equals("profile/default/1.png")) {
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

    public UserDetails loginUser(GoogleLoginDTO googleLoginDTO){
        System.out.println("GOOGLE EMAIL : " + googleLoginDTO.getEmail());
        Member member = memberRepository.findByEmail(googleLoginDTO.getEmail()).orElse(null) ;
        UserDetails userDetails = null;

        // 찾는 멤버가 없으면 리턴
        if(member == null){
            member = signUpSocialUser(googleLoginDTO);
        }

        userDetails = myUserDetailsService.loadUserByUsername(member.getUid().toString());

        System.out.println("member.getPassword() : "+member.getPassword());
        System.out.println("googleLoginDTO.getSub() : "+googleLoginDTO.getSub());
        // DB에 있는 sub(password)와 들어온 sub와 일치하는 지 확인
        if(member.getPassword().equals(googleLoginDTO.getSub())){
            return userDetails;
        }
        else {
            throw new BadCredentialsException("uid : " + userDetails.getUsername() + " / Invalid social login");
        }
    }

    public Member signUpSocialUser(GoogleLoginDTO gDTO){
        String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());

        Member m = Member.builder().email(gDTO.getEmail()).password(gDTO.getSub()).name(gDTO.getName())
                .profileImg("profile/default/1.png").nickname("유저"+gDTO.getSub().substring(3,10) + date).build();
        try{
            memberRepository.save(m);
            memberRepository.flush();

            return memberRepository.findByEmail(gDTO.getEmail()).orElseThrow(()-> new Exception("signUpSocialUser"));
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

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
                member.getNickname(), s3Url + member.getProfileImg(), member.getIntroduce());
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

    public boolean followUser(Long from, Long to) throws IllegalArgumentException, Exception{
        Follow follow = new Follow();

        Member follower = new Member();
        follower.setUid(from);
        Member followee = new Member();
        followee.setUid(to);

        follow.setFrom(follower);
        follow.setTo(followee);
        followRepository.save(follow);

        return true;
    }

    public List<FollowerDTO> getFollowerAll(Long uid, Long me, Pageable pageable) throws NumberFormatException{
        List<FollowerVO> followers = followRepository.getFollwerAll(uid, me, pageable);

        List<FollowerDTO> followersResponse = new ArrayList<>();

        followers.forEach((follower) -> {
            FollowerDTO followerDTO = FollowerDTO.builder()
                    .isFollowed(follower.getIsFollowed())
                    .nickname(follower.getNickname())
                    .uid(follower.getUid())
                    .profileImg(s3Url + follower.getProfileImg())
                    .introduce(follower.getIntroduce())
                    .build();
            followersResponse.add(followerDTO);
        });
        return followersResponse;
    }

    public Object getFolloweeAll(Long uid, Long me, Pageable pageable) {
        List<FollowerVO> followers = followRepository.getFollweeAll(uid, me, pageable);

        List<FollowerDTO> followeesResponse = new ArrayList<>();

        followers.forEach((follower) -> {
            FollowerDTO followerDTO = FollowerDTO.builder()
                    .isFollowed(follower.getIsFollowed())
                    .nickname(follower.getNickname())
                    .uid(follower.getUid())
                    .profileImg(s3Url + follower.getProfileImg())
                    .introduce(follower.getIntroduce())
                    .build();
            followeesResponse.add(followerDTO);
        });
        return followeesResponse;
    }

    public boolean unfollowUser(Long from, Long to) {
        Follow follow = new Follow();

        Member follower = new Member();
        follower.setUid(from);
        Member followee = new Member();
        followee.setUid(to);

        follow.setFrom(follower);
        follow.setTo(followee);
        followRepository.delete(follow);

        return true;
    }
}

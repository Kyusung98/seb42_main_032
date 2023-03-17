package DabuOps.tikkle.oauth.service;

import DabuOps.tikkle.member.entity.Member;
import DabuOps.tikkle.member.entity.Member.MemberState;
import DabuOps.tikkle.member.repository.MemberRepository;
import DabuOps.tikkle.oauth.principal.OAuthUserInfo;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    /*
    * Login 과정
    * Client -> OAuth2 Server 로그인 요청 (프론트)
    * OAuth2 Server -> Client로 accessToken 발급 (프론트)
    * Client에 있는 accessToken을 accessTokenDto에 저장 (Contorller에서 RequestBody로 받아오기)
    * accessToken validate (validate())
    * accessToken validate 완료시 UserInfo 받아오기 (getMemberProfile())
    * UserInfo 확인 후 첫 로그인이면 회원가입처리, 아니면 로그인 (getMemberProfile())
    * return으로 UserInfo 반환
     */

    public Optional<Member> login(){

        Optional<Member> member = getMemberProfile();

        return member;
    }

    /*
    * Google Server에서 accessToken validate하는 로직
    * https://www.googleapis.com/oauth2/v2/tokeninfo?access_token=
     */
    public HttpStatus validate(String accessToken) throws IOException {
        URL url = new URL("https://oauth2.googleapis.com/tokeninfo?id_token=" + accessToken);

        Scanner scanner = new Scanner(url.openStream());
        String response = scanner.useDelimiter("\\Z").next();
        scanner.close();

        if (response.contains("\"aud\":\"${G_CLIENT_ID}\"")) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.UNAUTHORIZED;
        }
    }

    /*
    * UserInfo 받아오기
    * 유저 정보 확인 후 있으면 로그인 없으면 회원가입(UserInfo -> MemberRepository에 저장
     */
    private Optional<Member> getMemberProfile(){
        OAuthUserInfo oAuthUserInfo = null;

        String email =  oAuthUserInfo.getEmail();
        String name = oAuthUserInfo.getName();
        String picture = oAuthUserInfo.getPicture();

        Optional<Member> member = memberRepository.findByEmailAndStateIs(email, MemberState.ACTIVE);

        if (member.isPresent()){
            Map<String, String> response = new HashMap<>();
            response.put("email", email);
            response.put("name", name);

        }else {
            Member newMember = new Member();
            newMember.setEmail(email);
            newMember.setName(name);
            newMember.setPicture(picture);
            memberRepository.save(newMember);

            Map<String, String> response = new HashMap<>();
            response.put("email", email);
            response.put("name", name);
            response.put("picture", picture);
        }
        return member;
    }
}
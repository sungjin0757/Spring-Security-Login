package login.basic.oauth.service;

import login.basic.domain.Member;
import login.basic.dto.LoginPrincipalDetails;
import login.basic.oauth.service.provider.GoogleUserInfo;
import login.basic.oauth.service.provider.KakaoUserInfo;
import login.basic.oauth.service.provider.NaverUserInfo;
import login.basic.oauth.service.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class Oauth2Service extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    //후처리 함수 구글로부터 받은 userRequest데이터에 대해서
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserInfo oAuth2UserInfo=null;
        log.info("user Request={}",userRequest.getAccessToken());   //google api information
        log.info("user Request={}",userRequest.getClientRegistration()); //access token
        //google login->code return (oAuth client ) -> AccessToken
        // userRequest Information -> member profile( load user)
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("user Request={}", oAuth2User.getAttributes());  //google user information
        log.info(userRequest.getClientRegistration().getRegistrationId()); //google
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo=new GoogleUserInfo(oAuth2User.getAttributes());
        }
        else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo=new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }
        else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            oAuth2UserInfo=new KakaoUserInfo(oAuth2User.getAttributes(),(Map)oAuth2User.getAttributes().get("kakao_account"));
        }
        log.info("name={}",oAuth2UserInfo.getName());
        Member member=new Member(oAuth2UserInfo.getEmail(),
                bCryptPasswordEncoder.encode("threeFam"),oAuth2UserInfo.getName());
        member.updateRole("ROLE_USER");

        return new LoginPrincipalDetails(member,oAuth2User.getAttributes());
    }
}

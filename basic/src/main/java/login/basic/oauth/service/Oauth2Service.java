package login.basic.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
@RequiredArgsConstructor
public class Oauth2Service extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //후처리 함수 구글로부터 받은 userRequest데이터에 대해서
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("user Request={}",userRequest.getAccessToken());   //google api information
        log.info("user Request={}",userRequest.getClientRegistration()); //access token
        //google login->code return (oAuth client ) -> AccessToken
        // userRequest Information -> member profile( load user)
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("user Request={}", oAuth2User.getAttributes());  //google user information

        String provider = userRequest.getClientRegistration().getClientId(); //google
        String providerId = oAuth2User.getAttribute("sub");
        String username=provider+"_"+providerId;
        String email=oAuth2User.getAttribute("email");
        String password=bCryptPasswordEncoder.encode("나이스");
        String Role="ROLE_USER";

        return oAuth2User;
    }
}

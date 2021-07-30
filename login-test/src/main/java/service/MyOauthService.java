package service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oauth.domain.Member;
import oauth.dto.SessionMember;
import oauth.repository.MemberRepository;
import oauth.security.OAuthAttributes;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
@Slf4j
public class MyOauthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>,MemberService {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findOne(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate=new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId=userRequest.getClientRegistration().getRegistrationId();
        String usernameAttribute=userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes=OAuthAttributes.of(registrationId,usernameAttribute,oAuth2User.getAttributes());

        Member member=save(attributes);
        httpSession.setAttribute("member",new SessionMember(member.getName(), member.getEmail()));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private Member save(OAuthAttributes attributes){
        Member member = findOne(attributes.getEmail());

        if(member==null)
            join(member);

        return member;
    }


}

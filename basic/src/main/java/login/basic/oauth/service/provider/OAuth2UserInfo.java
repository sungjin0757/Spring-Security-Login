package login.basic.oauth.service.provider;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2UserInfo  {
    String getEmail();
    String getName();
}

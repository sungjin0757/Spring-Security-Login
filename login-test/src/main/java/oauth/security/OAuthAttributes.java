package oauth.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OAuthAttributes {

    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    public static OAuthAttributes of(String registerId,String usernameAttributeName,
                                     Map<String,Object> attributes){
//        if(registerId.equals("naver"))
//        {
//            return ofNaver(usernameAttributeName,attributes);
//        }
//        else if(registerId.equals("kakao"))
//        {
//            return ofkakao(usernameAttributeName,attributes);
//        }
        return ofGoogle(usernameAttributeName,attributes);

    }

    private static OAuthAttributes ofGoogle(String usernameAttributeName,Map<String,Object> attributes){
        return new OAuthAttributes(attributes,usernameAttributeName,
                (String)attributes.get("name"),
                (String)attributes.get("email"));
    }


}

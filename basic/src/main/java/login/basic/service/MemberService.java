package login.basic.service;

import login.basic.domain.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;

public interface MemberService extends UserDetailsService  {

    Member join(Member member);
    Member findByEmail(String email);
    Member findById(Long id);
    List<Member> findAll();

}

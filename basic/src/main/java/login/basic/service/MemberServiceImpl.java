package login.basic.service;

import login.basic.domain.Member;
import login.basic.dto.LoginPrincipalDetails;
import login.basic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member join(Member member) {
        if(member.getEmail().equals("admin")){
            member.updateRole("ROLE_ADMIN");
        }
        else{
            member.updateRole("ROLE_USER");
        }
        member.passwordEncode(passwordEncoder.encode(member.getPassword()));
        Member saveMember = memberRepository.save(member);

        return saveMember;
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info(username);
        Member member = findByEmail(username);
        if(member==null){
            throw new UsernameNotFoundException(username);
        }

        return new LoginPrincipalDetails(member);

    }


}

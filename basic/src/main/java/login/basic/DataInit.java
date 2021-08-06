package login.basic;

import login.basic.domain.Member;
import login.basic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final MemberService memberService;

    @PostConstruct
    public void init(){
        Member member=new Member("admin", "admin","admin" );
        member.updateRole("ROLE_ADMIN");

        memberService.join(member);
    }
}

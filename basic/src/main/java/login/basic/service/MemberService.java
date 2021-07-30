package login.basic.service;

import login.basic.domain.Member;

import java.util.List;

public interface MemberService {

    Member join(Member member);
    Member findByEmail(String email);
    Member findById(Long id);
    List<Member> findAll();
}

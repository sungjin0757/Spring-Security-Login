package login.basic.repository;

import login.basic.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Member findById(Long id);
    Member findByEmail(String email);
    List<Member> findAll();
}

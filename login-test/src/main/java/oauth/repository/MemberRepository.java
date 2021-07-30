package oauth.repository;

import oauth.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    void save(Member member);
    Optional<Member> findByEmail(String email);
}

package service;

import oauth.domain.Member;

public interface MemberService {
    void join(Member member);
    Member findOne(String email);
}

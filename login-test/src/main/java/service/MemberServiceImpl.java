package service;

import lombok.RequiredArgsConstructor;
import oauth.domain.Member;
import oauth.repository.MemberRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findOne(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }
}

package oauth.repository;

import lombok.RequiredArgsConstructor;
import oauth.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository{

    private final EntityManager em;

    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(em.createQuery("select m from Member m where m.email= :email", Member.class)
                .setParameter("email", email)
                .getSingleResult());
    }
}

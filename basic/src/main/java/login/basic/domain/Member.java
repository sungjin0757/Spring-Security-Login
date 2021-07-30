package login.basic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name="member_ic")
    private Long id;

    private String email;
    private String password;
}

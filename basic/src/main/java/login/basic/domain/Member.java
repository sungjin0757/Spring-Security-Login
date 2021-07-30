package login.basic.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue
    @Column(name="member_ic")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String role;

    public Member(String email, String password, String name) {
        this.email = email;
        this.password=password;
        this.name = name;
    }

    public void updateRole(String role){
        this.role=role;
    }

    public void passwordEncode(String password){
        this.password=password;
    }
}

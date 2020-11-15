package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "members")
@NamedQueries({
        @NamedQuery(name = "getMembers", query = "SELECT m FROM Member m ORDER BY m.created_at DESC"),
        @NamedQuery(name = "getMembersCnt", query = "SELECT COUNT(m) FROM Member m"),
        @NamedQuery(name = "checkRegisteredCode", query = "SELECT COUNT(m) FROM Member m WHERE m.code = :code"),
        @NamedQuery(name = "checkLoginCodeAndPassword", query = "SELECT m FROM Member m WHERE m.delete_flag = 0 AND m.code = :code AND m.password = :pass")
})

@Getter
@Setter
@Entity
public class Member {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "role_flag", nullable = false)
    private Integer role_flag;

    @Column(name = "delete_flag", nullable = false)
    private Integer delete_flag;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;
}

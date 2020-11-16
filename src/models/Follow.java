package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "follows")
@NamedQueries({
        @NamedQuery(name = "followDestroy", query = "SELECT f.id FROM Follow f WHERE f.followed_id  = :followed_id AND f.following_id = :login_member_id"),
        @NamedQuery(name = "followerDestroy", query = "SELECT f.id FROM Follow f WHERE f.followed_id  = :login_member_id AND f.following_id = :following_id"),

        @NamedQuery(name = "getMyFollowAllReports", query = "SELECT i FROM Idea i, Follow f WHERE f.following_id = :login_member_id AND i.member.id = f.followed_id.id ORDER BY i.updated_at DESC"),
        @NamedQuery(name = "getMyFollowReportsCount", query = "SELECT COUNT(a) FROM Idea a, Follow f WHERE f.followed_id = :employee AND r.employee.id = f.follow.id"),

        @NamedQuery(name = "getMyAllFollowing", query = "SELECT f FROM Member m, Follow f WHERE f.employee = :employee AND e.id = f.follow.id ORDER BY f.id DESC"),
        @NamedQuery(name = "getMyFollowingCount", query = "SELECT COUNT(f) FROM Member m, Follow f WHERE f.employee = :employee AND e.id = f.follow.id"),

        @NamedQuery(name = "getMyAllFollower", query = "SELECT f FROM Member m, Follow f WHERE f.follow = :employee AND e.id = f.employee.id ORDER BY f.id DESC"),
        @NamedQuery(name = "getMyFollowerCount", query = "SELECT COUNT(f) FROM Member m, Follow f WHERE f.follow = :employee AND e.id = f.employee.id"),

        @NamedQuery(name = "checkMyFollow", query = "SELECT f.follow FROM Follow f WHERE f.employee = :employee"),

        @NamedQuery(name = "getEmployeeFollowing", query = "SELECT f FROM Follow f WHERE f.employee = :employee ORDER BY f.id DESC"),
        @NamedQuery(name = "getEmployeeFollowingCount", query = "SELECT COUNT(f) FROM Follow f WHERE f.employee = :employee"),

        @NamedQuery(name = "getEmployeeNotFollowing", query = "SELECT e FROM Member m WHERE NOT EXISTS (SELECT f FROM Follow AS f WHERE f.employee = :employee AND e.id = f.follow.id) AND e.id <> :employee ORDER BY e.id DESC"),
        @NamedQuery(name = "getEmployeeNotFollowingCount", query = "SELECT COUNT(e) FROM Member m WHERE NOT EXISTS (SELECT f FROM Follow AS f WHERE f.employee = :employee AND e.id = f.follow.id) AND e.id <> :employee")
})

@Getter
@Setter
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private Member following_id; // 元employee

    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    private Member followed_id; // 元follow

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;
}
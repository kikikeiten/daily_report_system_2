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
        @NamedQuery(name = "getDestroyFollow", query = "SELECT f.id FROM Follow f WHERE f.followed_id  = :followed_id AND f.following_id = :login_member_id"),
        @NamedQuery(name = "getDestroyFollower", query = "SELECT f.id FROM Follow f WHERE f.followed_id  = :login_member_id AND f.following_id = :following_id"),

        @NamedQuery(name = "getMyFollowingIdeas", query = "SELECT i FROM Idea i, Follow f WHERE f.following_id = :login_member_id AND i.member.id = f.followed_id.id ORDER BY i.updated_at DESC"),
        @NamedQuery(name = "getMyFollowingIdeasCnt", query = "SELECT COUNT(i) FROM Idea i, Follow f WHERE f.followed_id = :login_member_id AND i.member.id = f.followed_id.id"),

        @NamedQuery(name = "getMyFollowing", query = "SELECT f FROM Follow f, Member m WHERE f.following_id = :login_member_id AND m.id = f.followed_id.id ORDER BY f.created_at DESC"),
        @NamedQuery(name = "getMyFollowingCnt", query = "SELECT COUNT(f) FROM Follow f, Member m WHERE f.following_id = :login_member_id AND m.id = f.followed_id.id"),
        @NamedQuery(name = "checkMyFollow", query = "SELECT f.followed_id FROM Follow f WHERE f.following_id = :login_member_id"),

        @NamedQuery(name = "getMyFollower", query = "SELECT f FROM Follow f, Member m WHERE f.followed_id = :login_member_id AND m.id = f.following_id.id ORDER BY f.created_at DESC"),
        @NamedQuery(name = "getMyFollowerCnt", query = "SELECT COUNT(f) FROM  Follow f, Member m WHERE f.followed_id = :login_member_id AND m.id = f.following_id.id"),

        @NamedQuery(name = "getMemberFollowing", query = "SELECT f FROM Follow f WHERE f.following_id = :login_member_id ORDER BY f.created_at DESC"),
        @NamedQuery(name = "getMemberFollowingCnt", query = "SELECT COUNT(f) FROM Follow f WHERE f.following_id = :login_member_id"),

        @NamedQuery(name = "getMemberNotFollowing", query = "SELECT m FROM Member m WHERE NOT EXISTS (SELECT f FROM Follow f WHERE f.following_id = :login_member_id AND m.id = f.followed_id.id) AND m.id <> :login_member_id ORDER BY m.created_at DESC"),
        @NamedQuery(name = "getMemberNotFollowingCnt", query = "SELECT COUNT(m) FROM Member m WHERE NOT EXISTS (SELECT f FROM Follow f WHERE f.following_id = :login_member_id AND m.id = f.followed_id.id) AND m.id <> :login_member_id")
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
    private Member following_id;

    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    private Member followed_id;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;
}
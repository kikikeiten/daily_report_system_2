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
        // フォロー解除するメンバーidを取得
        @NamedQuery(name = "getDestroyFollow", query = "SELECT f.id FROM Follow f WHERE f.followed_id  = :followed_id AND f.following_id = :login_member"),
        // フォロワーのフォロー解除するメンバーidを取得
        @NamedQuery(name = "getDestroyFollower", query = "SELECT f.id FROM Follow f WHERE f.followed_id  = :login_member AND f.following_id = :following_id"),

        // ログイン中メンバーのフォローしているメンバーのideaを取得
        @NamedQuery(name = "getMyFollowingIdeas", query = "SELECT i FROM Idea i, Follow f WHERE f.following_id = :login_member AND i.member.id = f.followed_id.id ORDER BY i.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getMyFollowingIdeasCnt", query = "SELECT COUNT(i) FROM Idea i, Follow f WHERE f.followed_id = :login_member AND i.member.id = f.followed_id.id"),

        // ログイン中メンバーのフォロー一覧を取得
        @NamedQuery(name = "getMyFollowing", query = "SELECT f FROM Follow f, Member m WHERE f.following_id = :login_member AND m.id = f.followed_id.id ORDER BY f.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getMyFollowingCnt", query = "SELECT COUNT(f) FROM Follow f, Member m WHERE f.following_id = :login_member AND m.id = f.followed_id.id"),
        // ログイン中メンバーのフォローしているメンバーidを取得（削除予定）
        @NamedQuery(name = "checkMyFollow", query = "SELECT f.followed_id FROM Follow f WHERE f.following_id = :login_member"),

        // ログイン中メンバーのフォロワー一覧を取得
        @NamedQuery(name = "getMyFollower", query = "SELECT f FROM Follow f, Member m WHERE f.followed_id = :login_member AND m.id = f.following_id.id ORDER BY f.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getMyFollowerCnt", query = "SELECT COUNT(f) FROM  Follow f, Member m WHERE f.followed_id = :login_member AND m.id = f.following_id.id"),

        // 他のメンバーのフォロー一覧を表示
        @NamedQuery(name = "getMemberFollowing", query = "SELECT f FROM Follow f WHERE f.following_id = :login_member ORDER BY f.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getMemberFollowingCnt", query = "SELECT COUNT(f) FROM Follow f WHERE f.following_id = :login_member"),

        // 他のメンバーのフォローしていない一覧を表示
        @NamedQuery(name = "getMemberNotFollowing", query = "SELECT m FROM Member m WHERE NOT EXISTS (SELECT f FROM Follow f WHERE f.following_id = :login_member AND m.id = f.followed_id.id) AND m.id <> :login_member ORDER BY m.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getMemberNotFollowingCnt", query = "SELECT COUNT(m) FROM Member m WHERE NOT EXISTS (SELECT f FROM Follow f WHERE f.following_id = :login_member AND m.id = f.followed_id.id) AND m.id <> :login_member")
})

@Getter
@Setter
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // フォローする側のメンバーid
    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private Member following_id;

    // フォローされる側のメンバーid
    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    private Member followed_id;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;
}
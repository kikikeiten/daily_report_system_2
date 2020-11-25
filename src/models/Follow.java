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
        // フォロー解除するメンバーのIDを取得
        @NamedQuery(name = "getDestroyFollow", query = "SELECT f.id FROM Follow f WHERE f.followedId  = :followedId AND f.followingId = :loginMember"),
        // フォロー解除するフォロワーのIDを取得
        @NamedQuery(name = "getDestroyFollower", query = "SELECT f.id FROM Follow f WHERE f.followingId  = :loginMember AND f.followedId = :followerId"),

        // ログイン中メンバーがフォローしているメンバーのアイデアを取得
        @NamedQuery(name = "getMyFollowingIdeas", query = "SELECT i FROM Idea i, Follow f WHERE f.followingId = :loginMember AND i.member.id = f.followedId.id ORDER BY i.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getMyFollowingIdeasCnt", query = "SELECT COUNT(i) FROM Idea i, Follow f WHERE f.followedId = :loginMember AND i.member.id = f.followedId.id"),

        // ログイン中メンバーのフォロー一覧を取得
        @NamedQuery(name = "getMyFollowing", query = "SELECT f FROM Follow f, Member m WHERE f.followingId = :loginMember AND m.id = f.followedId.id ORDER BY f.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getMyFollowingCnt", query = "SELECT COUNT(f) FROM Follow f, Member m WHERE f.followingId = :loginMember AND m.id = f.followedId.id"),
        // ログイン中メンバーがフォローしているメンバーのIDを取得（削除予定）
        @NamedQuery(name = "checkMyFollow", query = "SELECT f.followedId FROM Follow f WHERE f.followingId = :loginMember"),

        // ログイン中メンバーのフォロワー一覧を取得
        @NamedQuery(name = "getMyFollower", query = "SELECT f FROM Follow f, Member m WHERE f.followedId = :loginMember AND m.id = f.followingId.id ORDER BY f.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getMyFollowerCnt", query = "SELECT COUNT(f) FROM  Follow f, Member m WHERE f.followedId = :loginMember AND m.id = f.followingId.id"),

        // 対象メンバーのフォロー一覧を取得
        @NamedQuery(name = "getMemberFollowing", query = "SELECT f FROM Follow f WHERE f.followingId = :member ORDER BY f.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getMemberFollowingCnt", query = "SELECT COUNT(f) FROM Follow f WHERE f.followingId = :member"),

        // 対象のメンバーがフォローしていないメンバー一覧を取得
        @NamedQuery(name = "getMemberNotFollowing", query = "SELECT m FROM Member m WHERE NOT EXISTS (SELECT f FROM Follow f WHERE f.followingId = :member AND m.id = f.followedId.id) AND m.id <> :member ORDER BY m.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getMemberNotFollowingCnt", query = "SELECT COUNT(m) FROM Member m WHERE NOT EXISTS (SELECT f FROM Follow f WHERE f.followingId = :member AND m.id = f.followedId.id) AND m.id <> :member")
})

@Getter
@Setter
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // フォローする側のメンバーID
    @ManyToOne
    @JoinColumn(name = "followingId", nullable = false)
    private Member followingId;

    // フォローされる側のメンバーID
    @ManyToOne
    @JoinColumn(name = "followedId", nullable = false)
    private Member followedId;

    @Column(name = "createdAt", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updatedAt", nullable = false)
    private Timestamp updatedAt;
}
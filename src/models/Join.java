package models;

import java.sql.Date;
import java.sql.Time;
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

@Table(name = "joins")
@NamedQueries({
        // ログイン中メンバーの全ジョイン履歴を取得
        @NamedQuery(name = "getMyJoins", query = "SELECT j FROM Join j WHERE j.member = :loginMember ORDER BY j.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getMyJoinCnt", query = "SELECT COUNT(j) FROM Join j WHERE j.member = :loginMember"),

        // 全てのジョイン履歴を取得
        @NamedQuery(name = "getJoins", query = "SELECT j FROM Join j ORDER BY j.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getJoinsCnt", query = "SELECT COUNT(j) FROM Join j"),

        // ログイン中メンバーの最新ジョイン履歴を取得
        @NamedQuery(name = "getMyLatestJoin", query = "SELECT j FROM Join j WHERE j.member = :loginMember ORDER BY j.updatedAt DESC"),
        // ログイン中メンバーの最新ジョイン履歴IDを取得（削除予定）
        @NamedQuery(name = "getMyLatestJoinId", query = "SELECT j.id FROM Join j WHERE j.member = :loginMember ORDER BY j.updatedAt DESC"),

        // 退席忘れを取得
        @NamedQuery(name = "get4getJoins", query = "SELECT j FROM Join j WHERE j.joinStatus = 1 AND j.joinDate <> :today")
})

@Getter
@Setter
@Entity
public class Join {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    private Member member;

    @Column(name = "joinDate", nullable = false)
    private Date joinDate;

    // 参加時間
    @Column(name = "punchIn", nullable = false)
    private Time punchIn;

    // 退席時間
    @Column(name = "punchOut", nullable = true)
    private Time punchOut;

    // 退席時間と参加時間の差
    @Column(name = "workingTime", nullable = true)
    private Time workingTime;

    /*
     * 0 : 退席済み
     * 1 : 参加
     * 2 : 退席忘れ
     * 3 : 退席時間の修正済み
     * */
    @Column(name = "joinStatus", nullable = false)
    private Integer joinStatus;

    @Column(name = "createdAt", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updatedAt", nullable = false)
    private Timestamp updatedAt;
}
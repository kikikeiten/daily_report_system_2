package models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "ideas")
@NamedQueries({
        // 全てのアイデアを取得
        @NamedQuery(name = "getIdeas", query = "SELECT i FROM Idea i ORDER BY i.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getIdeasCnt", query = "SELECT COUNT(i) FROM Idea i"),

        // ドラフトを除いたアイデアを取得
        @NamedQuery(name = "getIdeasButDrafts", query = "SELECT i FROM Idea i WHERE i.reviewStatus <> 0 ORDER BY i.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getIdeasCntButDrafts", query = "SELECT COUNT(i) FROM Idea i WHERE i.reviewStatus <> 0"),

        // ログイン中メンバーのアイデアを取得
        @NamedQuery(name = "getMyIdeas", query = "SELECT i FROM Idea i WHERE i.member = :loginMember ORDER BY i.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getMyIdeasCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :loginMember"),

        // ログイン中メンバーのドラフトを取得
        @NamedQuery(name = "getMyDrafts", query = "SELECT i FROM Idea i WHERE i.member = :loginMember AND i.reviewStatus = 0 ORDER BY i.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getMyDraftsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :loginMember AND i.reviewStatus = 0"),

        // マネージャーのレビュー待ちアイデアを取得
        @NamedQuery(name = "getManagerReviews", query = "SELECT i FROM Idea i WHERE i.reviewStatus = 2 ORDER BY i.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getManagerReviewsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.reviewStatus = 2"),

        // ディレクターのレビュー待ちアイデアを取得
        @NamedQuery(name = "getDirectorReviews", query = "SELECT i FROM Idea i WHERE i.reviewStatus = 4 ORDER BY i.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getDirectorReviewsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.reviewStatus = 4"),

        // マネージャーのアドバイス有アイデアを取得（再ポストの必要あり）
        @NamedQuery(name = "getManagerAdvice", query = "SELECT i FROM Idea i WHERE i.member = :loginMember AND i.reviewStatus = 1 ORDER BY i.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getManagerAdviceCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :loginMember AND i.reviewStatus = 1"),

        // ディレクターのアドバイス有アイデアを取得（再ポストの必要あり）
        @NamedQuery(name = "getDirectorAdvice", query = "SELECT i FROM Idea i WHERE i.member = :loginMember AND i.reviewStatus = 3 ORDER BY i.updatedAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getDirectorAdviceCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :loginMember AND i.reviewStatus = 3"),

        // マネージャーのレビュー忘れアイデアを取得（前日以前）
        @NamedQuery(name = "get4getManagerReviewsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.reviewStatus = 2 AND :role = 2 AND i.createdDate < :date"),
        // ディレクターのレビュー忘れアイデアを取得（前日以前）
        @NamedQuery(name = "get4getDirectorReviewsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.reviewStatus = 4 AND :role = 3 AND i.createdDate < :date"),
        // ドラフトのポスト忘れを取得（前日以前）
        @NamedQuery(name = "get4getMyDraftsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :loginMember AND i.reviewStatus = 0 AND i.createdDate < :date")
})

@Getter
@Setter
@Entity
public class Idea {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    private Member member;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "favors", nullable = false)
    private Integer favors;

    /*
     * 0 : 下書き
     * 1 : マネージャーのアドバイス有（再ポストの必要）
     * 2 : マネージャーのレビュー待ち
     * 3 : ディレクターのアドバイス有（再ポストの必要）
     * 4 : ディレクターのレビュー待ち
     * 6 : 採用
     * */
    @Column(name = "reviewStatus", nullable = false)
    private Integer reviewStatus;

    @Column(name = "createdDate", nullable = false)
    private Date createdDate;

    @Column(name = "createdAt", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updatedAt", nullable = false)
    private Timestamp updatedAt;
}
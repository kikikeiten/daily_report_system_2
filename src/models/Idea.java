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
        // 全てのideaを取得
        @NamedQuery(name = "getIdeas", query = "SELECT i FROM Idea i ORDER BY i.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getIdeasCnt", query = "SELECT COUNT(i) FROM Idea i"),

        // 下書きを除いたideaを取得
        @NamedQuery(name = "getIdeasButDrafts", query = "SELECT i FROM Idea i WHERE i.review_flag <> 0 ORDER BY i.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getIdeasCntButDrafts", query = "SELECT COUNT(i) FROM Idea i WHERE i.review_flag <> 0"),

        // ログイン中メンバーのideaを取得
        @NamedQuery(name = "getMyIdeas", query = "SELECT i FROM Idea i WHERE i.member = :login_member_id ORDER BY i.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getMyIdeasCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :login_member_id"),

        // ログイン中メンバーの下書きを取得
        @NamedQuery(name = "getMyDrafts", query = "SELECT i FROM Idea i WHERE i.member = :login_member_id AND i.review_flag = 0 ORDER BY i.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getMyDraftsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :login_member_id AND i.review_flag = 0"),

        // マネージャーのレビュー待ちideaを取得
        @NamedQuery(name = "getManagerReviews", query = "SELECT i FROM Idea i WHERE i.review_flag = 2 ORDER BY i.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getManagerReviewsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.review_flag = 2"),

        // ディレクターのレビュー待ちideaを取得
        @NamedQuery(name = "getDirectorReviews", query = "SELECT i FROM Idea i WHERE i.review_flag = 4 ORDER BY i.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getDirectorReviewsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.review_flag = 4"),

        // マネージャーのアドバイス有ideaを取得（再ポストの必要あり）
        @NamedQuery(name = "getManagerAdvice", query = "SELECT i FROM Idea i WHERE i.member = :login_member_id AND i.review_flag = 1 ORDER BY i.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getManagerAdviceCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :login_member_id AND i.review_flag = 1"),

        // ディレクターのアドバイス有ideaを取得（再ポストの必要あり）
        @NamedQuery(name = "getDirectorAdvice", query = "SELECT i FROM Idea i WHERE i.member = :login_member_id AND i.review_flag = 3 ORDER BY i.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getDirectorAdviceCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :login_member_id AND i.review_flag = 3"),

        // マネージャーのレビュー忘れideaを取得（前日以前）
        @NamedQuery(name = "get4getManagerReviewsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.review_flag = 2 AND :role_flag = 2 AND i.created_date < :today"),
        // ディレクターのレビュー忘れideaを取得（前日以前）
        @NamedQuery(name = "get4getDirectorReviewsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.review_flag = 4 AND :role_flag = 3 AND i.created_date < :today"),
        // 下書きのポスト忘れを取得（前日以前）
        @NamedQuery(name = "get4getMyDraftsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :login_member_id AND i.review_flag = 0 AND i.created_date < :today")
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
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "likes", nullable = false)
    private Integer likes;

    /*
     * 0 : 下書き
     * 1 : マネージャーのアドバイス有（再ポストの必要）
     * 2 : マネージャーのレビュー待ち
     * 3 : ディレクターのアドバイス有（再ポストの必要）
     * 4 : ディレクターのレビュー待ち
     * 6 : 採用
     * */
    @Column(name = "review_flag", nullable = false)
    private Integer review_flag;

    @Column(name = "created_date", nullable = false)
    private Date created_date;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;
}
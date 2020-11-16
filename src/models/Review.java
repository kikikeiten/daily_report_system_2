package models;

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

@Table(name = "reviews")
@NamedQueries({
        // ideaから全てのレビューを取得
        @NamedQuery(name = "getReviews", query = "SELECT r FROM Review r WHERE r.idea = :idea ORDER BY r.updated_at DESC"),
        // カウント
        @NamedQuery(name = "getReviewsCnt", query = "SELECT COUNT(r) FROM Review r WHERE r.idea = :idea")
})

@Getter
@Setter
@Entity
public class Review {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

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

    @Lob
    @Column(name = "advice", nullable = true)
    private String advice;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;
}
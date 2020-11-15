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
        @NamedQuery(name = "getIdeas", query = "SELECT i FROM Idea i ORDER BY i.updated_at DESC"),
        @NamedQuery(name = "getIdeasCnt", query = "SELECT COUNT(i) FROM Idea i"),

        @NamedQuery(name = "getIdeasButDrafts", query = "SELECT i FROM Idea i WHERE i.review_flag <> 0 ORDER BY i.updated_at DESC"),
        @NamedQuery(name = "getIdeasCntButDrafts", query = "SELECT COUNT(i) FROM Idea i WHERE i.review_flag <> 0"),

        @NamedQuery(name = "getMyIdeas", query = "SELECT i FROM Idea i WHERE i.member = :member ORDER BY i.updated_at DESC"),
        @NamedQuery(name = "getMyIdeasCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :member"),

        @NamedQuery(name = "getMyDrafts", query = "SELECT i FROM Idea i WHERE i.member = :member AND i.review_flag = 0 ORDER BY i.updated_at DESC"),
        @NamedQuery(name = "getMyDraftsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :member AND i.review_flag = 0"),

        @NamedQuery(name = "getManagerReviews", query = "SELECT i FROM Idea i WHERE i.review_flag = 2 ORDER BY i.updated_at DESC"),
        @NamedQuery(name = "getManagerReviewsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.review_flag = 2"),

        @NamedQuery(name = "getDirectorReviews", query = "SELECT i FROM Idea i WHERE i.review_flag = 4 ORDER BY i.updated_at DESC"),
        @NamedQuery(name = "getDirectorReviewsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.review_flag = 4"),

        @NamedQuery(name = "getManagerAdvice", query = "SELECT i FROM Idea i WHERE i.member = :member AND i.review_flag = 1 ORDER BY i.updated_at DESC"),
        @NamedQuery(name = "getManagerAdviceCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :member AND i.review_flag = 1"),

        @NamedQuery(name = "getDirectorAdvice", query = "SELECT i FROM Idea i WHERE i.member = :member AND i.review_flag = 3 ORDER BY i.updated_at DESC"),
        @NamedQuery(name = "getDirectorAdviceCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :member AND i.review_flag = 3"),

        @NamedQuery(name = "getYtdManagerReviewsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.review_flag = 2 AND :role_flag = 2 AND i.created_date < :today"),
        @NamedQuery(name = "getYtdDirectorReviewsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.review_flag = 4 AND :role_flag = 3 AND i.created_date < :today"),
        @NamedQuery(name = "getYtdMyDraftsCnt", query = "SELECT COUNT(i) FROM Idea i WHERE i.member = :member AND i.review_flag = 0 AND i.created_date < :now")
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

    @Column(name = "review_flag", nullable = false)
    private Integer review_flag;

    @Column(name = "created_date", nullable = false)
    private Date created_date;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;
}
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

@Table(name = "likes")
@NamedQueries({
        @NamedQuery(name = "getMyLikesCount", query = "SELECT COUNT(l) FROM Like AS l WHERE l.report = :report "),
        @NamedQuery(name = "getMyAllLikes", query = "SELECT l FROM Like AS l WHERE l.report = :report ORDER BY l.created_at DESC")
})

@Getter
@Setter
@Entity
public class Like {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Idea idea;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;
}
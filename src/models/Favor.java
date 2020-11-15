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

@Table(name = "favors")
@NamedQueries({
        // 全てのideaに付いたfavorを取得
        @NamedQuery(name = "getIdeaFavors", query = "SELECT f FROM Favor f WHERE f.idea = :idea ORDER BY f.created_at DESC"),
        // カウント
        @NamedQuery(name = "getIdeaFavorsCnt", query = "SELECT COUNT(f) FROM Favor f WHERE f.idea = :idea "),
})

@Getter
@Setter
@Entity
public class Favor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;
}
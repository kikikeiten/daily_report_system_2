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
        // 全てのアイデアに付いた賛成を取得
        @NamedQuery(name = "getFavors", query = "SELECT f FROM Favor f WHERE f.idea = :idea ORDER BY f.createdAt DESC"),
        // カウントを取得
        @NamedQuery(name = "getFavorsCnt", query = "SELECT COUNT(f) FROM Favor f WHERE f.idea = :idea "),
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
    @JoinColumn(name = "member", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "idea", nullable = false)
    private Idea idea;

    @Column(name = "createdAt", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updatedAt", nullable = false)
    private Timestamp updatedAt;
}
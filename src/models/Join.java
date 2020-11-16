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
        @NamedQuery(name = "getMyJoins", query = "SELECT j FROM Join j WHERE j.member = :login_member_id ORDER BY j.updated_at DESC"),
        @NamedQuery(name = "getMyJoinCnt", query = "SELECT COUNT(j) FROM Join j WHERE j.member = :login_member_id"),

        @NamedQuery(name = "getJoins", query = "SELECT j FROM Join j ORDER BY j.updated_at DESC"),
        @NamedQuery(name = "getJoinsCnt", query = "SELECT COUNT(j) FROM Join j"),

        @NamedQuery(name = "getMyLatestJoin", query = "SELECT j FROM Join j WHERE j.member = :login_member_id ORDER BY j.updated_at DESC"),
        @NamedQuery(name = "getMyLatestJoinId", query = "SELECT j.id FROM Join j WHERE j.member = :login_member_id ORDER BY j.updated_at DESC"),

        @NamedQuery(name = "getForgetJoins", query = "SELECT j FROM Join j WHERE j.join_flag = 1 AND j.join_date <> :today")
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
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "join_date", nullable = false)
    private Date join_date;

    @Column(name = "punch_in", nullable = false)
    private Time punch_in;

    @Column(name = "punch_out", nullable = true)
    private Time punch_out;

    @Column(name = "working_time", nullable = true)
    private Time working;

    @Column(name = "join_flag", nullable = false)
    private Integer join_flag;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;
}
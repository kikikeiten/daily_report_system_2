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

@Table(name = "approvals")
@NamedQueries({
        @NamedQuery(name = "getReportApprovals", query = "SELECT a FROM Approval AS a WHERE a.report = :report ORDER BY a.id DESC"),
        @NamedQuery(name = "getReportApprovalsCount", query = "SELECT COUNT(a) FROM Approval AS a WHERE a.report = :report")
})

@Getter
@Setter
@Entity
public class Approval {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Idea idea;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Member member;

    @Column(name = "approval", nullable = false)
    private Integer approval;

    @Lob
    @Column(name = "comment", nullable = true)
    private String comment;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;
}
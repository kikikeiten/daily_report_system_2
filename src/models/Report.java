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

@Table(name = "reports")
@NamedQueries({
        @NamedQuery(name = "getAllReports", query = "SELECT r FROM Report AS r ORDER BY r.id DESC"),
        @NamedQuery(name = "getReportsCount", query = "SELECT COUNT(r) FROM Report AS r"),
        @NamedQuery(name = "getMyAllReports", query = "SELECT r FROM Report AS r WHERE r.employee = :employee ORDER BY r.id DESC"),
        @NamedQuery(name = "getMyReportsCount", query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :employee"),
        @NamedQuery(name = "getMyAllDrafts", query = "SELECT r FROM Report AS r WHERE r.employee = :employee AND r.approval = 0 ORDER BY r.id DESC"), //List of my daily draft reports
        @NamedQuery(name = "getMyDraftsCount", query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :employee AND r.approval = 0"), //Count my daily draft report
        @NamedQuery(name = "getAllManagerApprovalReports", query = "SELECT r FROM Report AS r WHERE r.approval = 2 ORDER BY r.id DESC"), //List of daily reports waiting for approval by the section chief excluding my own daily reports
        @NamedQuery(name = "getManagerApprovalReportsCount", query = "SELECT COUNT(r) FROM Report AS r WHERE r.approval = 2"), //Total number of daily reports waiting for approval by the section chief, excluding my own daily reports
        @NamedQuery(name = "getAllDirectorApprovalReports", query = "SELECT r FROM Report AS r WHERE r.approval = 4 ORDER BY r.id DESC"), //List of daily reports waiting for approval by the director excluding my own daily reports
        @NamedQuery(name = "getDirectorApprovalReportsCount", query = "SELECT COUNT(r) FROM Report AS r WHERE r.approval = 4"), //Total number of daily reports waiting for approval by the director, excluding my own daily reports
        @NamedQuery(name = "getAllManagerRemandReports", query = "SELECT r FROM Report AS r WHERE r.employee = :employee AND r.approval = 1 ORDER BY r.id DESC"), //List of daily reports returned by the section chief for the logged-in employee
        @NamedQuery(name = "getManagerRemandReportsCount", query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :employee AND r.approval = 1"), //Total daily reports sent back by the section chief to logged-in employee
        @NamedQuery(name = "getAllDirectorRemandReports", query = "SELECT r FROM Report AS r WHERE r.employee = :employee AND r.approval = 3 ORDER BY r.id DESC"), //List of daily reports returned by the director for the logged-in employee
        @NamedQuery(name = "getDirectorRemandReportsCount", query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :employee AND r.approval = 3") //Total daily reports sent back by the director to logged-in employee
})
@Entity
public class Report {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "report_date", nullable = false)
    private Date report_date;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "likes", nullable = false)
    private Integer likes;

    @Column(name = "approval", nullable = false)
    private Integer approval;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getApproval() {
        return approval;
    }

    public void setApproval(Integer approval) {
        this.approval = approval;
    }

}
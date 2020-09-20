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

@Table(name = "follows")
@NamedQueries({
        @NamedQuery(name = "followDestroy", query = "SELECT f.id FROM Follow AS f WHERE f.follow  = :follow"),
        @NamedQuery(name = "getMyFollowAllReports", query = "SELECT r FROM Report AS r, Follow AS f WHERE f.employee = :employee AND r.employee.id = f.follow.id ORDER BY r.id DESC"),
        @NamedQuery(name = "getMyFollowReportsCount", query = "SELECT COUNT(r) FROM Report AS r, Follow AS f WHERE f.employee = :employee AND r.employee.id = f.follow.id"),
        @NamedQuery(name = "getMyAllFollowing", query = "SELECT f FROM Employee AS e, Follow AS f WHERE f.employee = :employee AND e.id = f.follow.id ORDER BY f.id DESC"),
        @NamedQuery(name = "getMyFollowingCount", query = "SELECT COUNT(f) FROM Employee AS e, Follow AS f WHERE f.employee = :employee AND e.id = f.follow.id"),
        @NamedQuery(name = "getMyAllFollower", query = "SELECT f FROM Employee AS e, Follow AS f WHERE f.follow = :employee AND e.id = f.employee.id ORDER BY f.id DESC"),
        @NamedQuery(name = "getMyFollowerCount", query = "SELECT COUNT(f) FROM Employee AS e, Follow AS f WHERE f.follow = :employee AND e.id = f.employee.id")

})

@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "follow_id", nullable = false)
    private Employee follow;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

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

    public Employee getFollow() {
        return follow;
    }

    public void setFollow(Employee follow) {
        this.follow = follow;
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

}
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

@Table(name = "follows")
@NamedQueries({
        @NamedQuery(name = "followDestroy", query = "SELECT f.id FROM Follow AS f WHERE f.follow  = :follow AND f.employee = :employee"),
        @NamedQuery(name = "followerDestroy", query = "SELECT f.id FROM Follow AS f WHERE f.follow  = :employee AND f.employee = :follow"),
        @NamedQuery(name = "getMyFollowAllReports", query = "SELECT r FROM Report AS r, Follow AS f WHERE f.employee = :employee AND r.employee.id = f.follow.id ORDER BY r.id DESC"),
        @NamedQuery(name = "getMyFollowReportsCount", query = "SELECT COUNT(r) FROM Report AS r, Follow AS f WHERE f.employee = :employee AND r.employee.id = f.follow.id"),
        @NamedQuery(name = "getMyAllFollowing", query = "SELECT f FROM Employee AS e, Follow AS f WHERE f.employee = :employee AND e.id = f.follow.id ORDER BY f.id DESC"),
        @NamedQuery(name = "getMyFollowingCount", query = "SELECT COUNT(f) FROM Employee AS e, Follow AS f WHERE f.employee = :employee AND e.id = f.follow.id"),
        @NamedQuery(name = "getMyAllFollower", query = "SELECT f FROM Employee AS e, Follow AS f WHERE f.follow = :employee AND e.id = f.employee.id ORDER BY f.id DESC"),
        @NamedQuery(name = "getMyFollowerCount", query = "SELECT COUNT(f) FROM Employee AS e, Follow AS f WHERE f.follow = :employee AND e.id = f.employee.id"),
        @NamedQuery(name = "checkMyFollow", query = "SELECT f.follow FROM Follow AS f WHERE f.employee = :employee"),
        @NamedQuery(name = "getEmployeeFollowing", query = "SELECT f FROM Follow AS f WHERE f.employee = :employee ORDER BY f.id DESC"), // Follow-up list for any employee
        @NamedQuery(name = "getEmployeeFollowingCount", query = "SELECT COUNT(f) FROM Follow AS f WHERE f.employee = :employee"), //Follow count for any employee
        @NamedQuery(name = "getEmployeeNotFollowing", query = "SELECT e FROM Employee AS e WHERE NOT EXISTS (SELECT f FROM Follow AS f WHERE f.employee = :employee AND e.id = f.follow.id) AND e.id <> :employee ORDER BY e.id DESC"), // List of other employees that one employee doesn't follow
        @NamedQuery(name = "getEmployeeNotFollowingCount", query = "SELECT COUNT(e) FROM Employee AS e WHERE NOT EXISTS (SELECT f FROM Follow AS f WHERE f.employee = :employee AND e.id = f.follow.id) AND e.id <> :employee") //Counts on the list of other employees that one employee doesn't follow
})

@Getter
@Setter
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
}
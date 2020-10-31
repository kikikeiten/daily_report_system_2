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

@Table(name = "attendances")
@NamedQueries({
        @NamedQuery(name = "getMyAllAttendances", query = "SELECT a FROM Attendance AS a WHERE a.employee = :employee ORDER BY a.punch_in DESC"),
        @NamedQuery(name = "getMyAttendancesCount", query = "SELECT COUNT(a) FROM Attendance AS a WHERE a.employee = :employee"),
        @NamedQuery(name = "getAllAttendances", query = "SELECT a FROM Attendance AS a ORDER BY a.punch_in DESC"),
        @NamedQuery(name = "getAllAttendancesCount", query = "SELECT COUNT(a) FROM Attendance AS a"),
        @NamedQuery(name = "getMyLatestAttendanceId", query = "SELECT a.id FROM Attendance AS a WHERE a.employee = :employee ORDER BY a.punch_in DESC"),
        @NamedQuery(name = "getMyLatestAttendance", query = "SELECT a FROM Attendance AS a WHERE a.employee = :employee ORDER BY a.punch_in DESC")
})

@Entity
public class Attendance {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "attendance_date", nullable = false)
    private Date attendance_date;

    @Column(name = "punch_in", nullable = false)
    private Timestamp punch_in;

    @Column(name = "punch_out", nullable = true)
    private Timestamp punch_out;

    @Column(name = "working", nullable = true)
    private Time working;

    @Column(name = "attendance_flag", nullable = false)
    private Integer attendance_flag;

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

    public Date getAttendance_date() {
        return attendance_date;
    }

    public void setAttendance_date(Date attendance_date) {
        this.attendance_date = attendance_date;
    }

    public Timestamp getPunch_in() {
        return punch_in;
    }

    public void setPunch_in(Timestamp punch_in) {
        this.punch_in = punch_in;
    }

    public Timestamp getPunch_out() {
        return punch_out;
    }

    public void setPunch_out(Timestamp punch_out) {
        this.punch_out = punch_out;
    }

    public Time getWorking() {
        return working;
    }

    public void setWorking(Time working) {
        this.working = working;
    }

    public Integer getAttendance_flag() {
        return attendance_flag;
    }

    public void setAttendance_flag(Integer attendance_flag) {
        this.attendance_flag = attendance_flag;
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
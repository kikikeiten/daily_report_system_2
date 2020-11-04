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

@Table(name = "attendances")
@NamedQueries({
        @NamedQuery(name = "getMyAllAttendances", query = "SELECT a FROM Attendance AS a WHERE a.employee = :employee ORDER BY a.punch_in DESC"),
        @NamedQuery(name = "getMyAttendancesCount", query = "SELECT COUNT(a) FROM Attendance AS a WHERE a.employee = :employee"),
        @NamedQuery(name = "getAllAttendances", query = "SELECT a FROM Attendance AS a ORDER BY a.punch_in DESC"),
        @NamedQuery(name = "getAllAttendancesCount", query = "SELECT COUNT(a) FROM Attendance AS a"),
        @NamedQuery(name = "getMyLatestAttendanceId", query = "SELECT a.id FROM Attendance AS a WHERE a.employee = :employee ORDER BY a.punch_in DESC"),
        @NamedQuery(name = "getMyLatestAttendance", query = "SELECT a FROM Attendance AS a WHERE a.employee = :employee ORDER BY a.punch_in DESC"),
        @NamedQuery(name = "getAllForgetAttendances", query = "SELECT a FROM Attendance AS a WHERE a.attendance_flag = 1 AND a.attendance_date <> :today")
})

@Getter
@Setter
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
    private Time punch_in;

    @Column(name = "punch_out", nullable = true)
    private Time punch_out;

    @Column(name = "working", nullable = true)
    private Time working;

    @Column(name = "attendance_flag", nullable = false)
    private Integer attendance_flag;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;
}
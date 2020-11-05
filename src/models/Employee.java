package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "employees")
@NamedQueries({
        @NamedQuery(name = "getAllEmployees", query = "SELECT e FROM Employee AS e ORDER BY e.id DESC"),
        @NamedQuery(name = "getEmployeesCount", query = "SELECT COUNT(e) FROM Employee AS e"),
        @NamedQuery(name = "checkRegisteredCode", query = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :code"),
        @NamedQuery(name = "checkLoginCodeAndPassword", query = "SELECT e FROM Employee AS e WHERE e.delete_flag = 0 AND e.code = :code AND e.password = :pass")
})

@Getter
@Setter
@Entity
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "admin_flag", nullable = false)
    private Integer admin_flag;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "delete_flag", nullable = false)
    private Integer delete_flag;

    public Integer getId() {
        return id;
    }
}

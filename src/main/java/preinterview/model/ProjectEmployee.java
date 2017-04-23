package preinterview.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by byorn on 22/04/2017.
 */
@Entity
public class ProjectEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne
    private Employee employee;

    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne
    private Project project;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "ProjectEmployee[id=%d]",
                id, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectEmployee)) return false;
        ProjectEmployee projectEmployee = (ProjectEmployee) o;
        return Objects.equals(getId(), projectEmployee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

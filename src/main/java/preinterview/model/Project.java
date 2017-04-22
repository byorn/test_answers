package preinterview.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by byorn on 22/04/2017.
 */
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;


    public Project(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Project[id=%d, name='%s']",
                id, name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

package preinterview.repository;

import org.springframework.data.repository.CrudRepository;
import preinterview.model.Employee;
import preinterview.model.Project;
import preinterview.model.ProjectEmployee;

import java.util.List;

/**
 * Created by byorn on 22/04/2017.
 */
public interface ProjectEmployeeRepository extends CrudRepository<ProjectEmployee, Long> {

    List<ProjectEmployee> findByProject(Project project);

    List<ProjectEmployee> findByEmployee(Employee employee);



}
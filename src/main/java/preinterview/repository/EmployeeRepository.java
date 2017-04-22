package preinterview.repository;

import org.springframework.data.repository.CrudRepository;
import preinterview.model.Employee;

import java.util.List;

/**
 * Created by byorn on 22/04/2017.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<Employee> findByFirstName(String firstName);

    List<Employee> findByManager(Employee employee);

}
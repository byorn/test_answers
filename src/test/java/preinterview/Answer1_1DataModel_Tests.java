/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package preinterview;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import preinterview.model.Employee;
import preinterview.model.Project;
import preinterview.model.ProjectEmployee;
import preinterview.repository.EmployeeRepository;
import preinterview.repository.ProjectEmployeeRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class Answer1_1DataModel_Tests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectEmployeeRepository projectEmployeeRepository;



    /**
     * Answer 1.1 - Create a DataModel -
     * Demonstrates John works for Lisa
     */
    @Test
    public void testFindEmployeesWorkingForAManager() {

        //Data Setup: --------------------------------------
        Employee manager = new Employee("Lisa");
        Employee employee = new Employee("John");
        employee.setManager(manager);

        entityManager.persist(manager);
        entityManager.persist(employee);
        // --------------------------------------------------


        List<Employee> subordinates = employeeRepository.findByManager(manager);

        assertThat(subordinates).extracting(Employee::getFirstName).containsOnly(employee.getFirstName());
    }

    /**
     * Answer 1.1 - Create a DataModel -
     * Demonstrates John Works on Project A and B
     */
    @Test
    public void testFindEmployeesWorkingForAProject() {

        //Data Setup -------------------------------------
        Employee john = new Employee("John");
        Project projectA = new Project("Project-A");
        Project projectB = new Project("Project-B");

        entityManager.persist(john);
        entityManager.persist(projectA);
        entityManager.persist(projectB);

        ProjectEmployee projectEmployeeForA = new ProjectEmployee();
        projectEmployeeForA.setProject(projectA);
        projectEmployeeForA.setEmployee(john);
        entityManager.persist(projectEmployeeForA);

        ProjectEmployee projectEmployeeForB = new ProjectEmployee();
        projectEmployeeForB.setProject(projectB);
        projectEmployeeForB.setEmployee(john);
        entityManager.persist(projectEmployeeForB);
        // ----------------------------------------------


        List<ProjectEmployee> projectEmployees = projectEmployeeRepository.findByEmployee(john);

        assertThat(projectEmployees).extracting(ProjectEmployee::getEmployee).containsOnly(john);
    }
}
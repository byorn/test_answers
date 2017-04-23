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
import org.springframework.test.context.junit4.SpringRunner;
import preinterview.model.Employee;
import preinterview.model.Project;
import preinterview.model.ProjectEmployee;
import preinterview.repository.EmployeeRepository;
import preinterview.repository.ProjectEmployeeRepository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class Answer1_4GetLikelyResponsibleManagerOfAProject_Tests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectEmployeeRepository projectEmployeeRepository;


    /**
     * Answer 1.4 - Get Likely responsible manager for a project
     **/
    @Test
    public void testGetLikelyResponsibleManagerDecidedByNumberOfWorkers() {

        Project project = new Project("Project-A");
        dataSetup(project);

        Employee employee = getLikelyResponsibleManagerDecidedByNumberOfWorkers(project);
        assertThat(employee.getFirstName()).isEqualTo("Lisa");

    }


    private Employee getLikelyResponsibleManagerDecidedByNumberOfWorkers(Project project) {

        Map<Employee, Integer> managerAndCount = new HashMap<>();

        List<ProjectEmployee> projectEmployeeList = projectEmployeeRepository.findByProject(project);

        for (ProjectEmployee projectEmployee : projectEmployeeList) {

            //Assumption: If Employee Does Not Report to AnyOne He/She is the owner of the Company and Owner of the Project
            if (projectEmployee.getEmployee().getManager() == null) {
                return projectEmployee.getEmployee();
            }
            if (managerAndCount.get(projectEmployee.getEmployee().getManager()) != null) {

                Integer count = managerAndCount.get(projectEmployee.getEmployee().getManager());
                count += 1;
            } else {
                Employee manager = projectEmployee.getEmployee().getManager();
                managerAndCount.put(manager, new Integer(1));
            }

        }
        return decideLikelyManagerBasedOnMaximumCount(managerAndCount);
    }

    private Employee decideLikelyManagerBasedOnMaximumCount(Map<Employee, Integer> managerAndCount) {

        Employee manager = null;
        Integer largestCount = 0;
        for (Map.Entry<Employee, Integer> entry : managerAndCount.entrySet()) {
            if (entry.getValue() > largestCount) {
                manager = entry.getKey();
                largestCount = entry.getValue();
            }
        }
        return manager;
    }


    /**
     * John and Jack Report to Lisa
     * John and Jack are assigned to Project A
     * Note: Lisa the manager is not assigned to a project
     * Note: All Reporting Managers are not assigned to a project.
     * James is assigned to Project A
     * @param project
     */
    private void dataSetup(Project project) {

        Employee john = new Employee("John");
        Employee jack = new Employee("Jack");
        Employee james = new Employee("James");
        Employee lisa = new Employee("Lisa");
        Employee leornard = new Employee("Leonard");
        Employee simon = new Employee("Simon");

        john.setManager(lisa);
        jack.setManager(lisa);
        james.setManager(leornard);

        lisa.setManager(simon);
        leornard.setManager(simon);

        entityManager.persist(john);
        entityManager.persist(jack);
        entityManager.persist(james);
        entityManager.persist(lisa);
        entityManager.persist(leornard);
        entityManager.persist(simon);
        entityManager.persist(project);

        ProjectEmployee projectEmployee1 = new ProjectEmployee();
        projectEmployee1.setProject(project);
        projectEmployee1.setEmployee(john);
        entityManager.persist(projectEmployee1);

        ProjectEmployee projectEmployee2 = new ProjectEmployee();
        projectEmployee2.setProject(project);
        projectEmployee2.setEmployee(jack);
        entityManager.persist(projectEmployee2);

        ProjectEmployee projectEmployee3 = new ProjectEmployee();
        projectEmployee3.setProject(project);
        projectEmployee3.setEmployee(james);
        entityManager.persist(projectEmployee3);

    }



}
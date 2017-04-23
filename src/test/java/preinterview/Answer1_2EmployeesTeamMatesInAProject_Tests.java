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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class Answer1_2EmployeesTeamMatesInAProject_Tests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectEmployeeRepository projectTeamRepository;


    /**
     * Answer 1.2 - For a given employee return team mates on a Project
     */
    @Test
    public void testGetTeamMembersForEmployee() {

        //Data Setup ------------------------------------
        Employee john = new Employee("John");
        Project projectA = new Project("Project-A");
        Project projectB = new Project("Project-B");
        Project projectC = new Project("Project-C");

        dataSetup_JohnWorksOnProjectAandB(john, projectA, projectB);
        dataSetup_JackWorksOnProjectA(projectA);
        dataSetup_LucyWorksOnProjectB(projectB);
        dataSetup_JameWorksOnProjectC(projectC);
        // --------------------------------------------------

        //John's Team Mates Should Be Jack And Lucy
        Set<Employee> teamMembers = testGetTeamMembersForEmployee(john);
        assertThat(teamMembers.size()).isEqualTo(2);
        assertThat(teamMembers).extracting(Employee::getFirstName).contains("Jack");
        assertThat(teamMembers).extracting(Employee::getFirstName).contains("Lucy");


    }

    private Set<Employee> testGetTeamMembersForEmployee(Employee employee) {

        Set<Employee> teamMembers = new HashSet<>();

        List<ProjectEmployee> listOfProjectsWorkingByEmployee = projectTeamRepository.findByEmployee(employee);
        listOfProjectsWorkingByEmployee.forEach(obj -> {

            List<ProjectEmployee> listOfEmployeesForTheProject = projectTeamRepository.findByProject(obj.getProject());
            listOfEmployeesForTheProject.forEach(projectEmployee -> {

                if (!projectEmployee.getEmployee().equals(employee)) {
                    teamMembers.add(projectEmployee.getEmployee());
                }

            });
        });

        return teamMembers;

    }

    private void dataSetup_JohnWorksOnProjectAandB(Employee john, Project projectA, Project projectB) {

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
    }

    private void dataSetup_JackWorksOnProjectA(Project projectA) {

        Employee jack = new Employee("Jack");
        entityManager.persist(jack);
        entityManager.persist(projectA);
        ProjectEmployee projectEmployeeForA = new ProjectEmployee();
        projectEmployeeForA.setProject(projectA);
        projectEmployeeForA.setEmployee(jack);
        entityManager.persist(projectEmployeeForA);


    }

    private void dataSetup_LucyWorksOnProjectB(Project projectB) {

        Employee lucy = new Employee("Lucy");
        entityManager.persist(lucy);
        entityManager.persist(projectB);
        ProjectEmployee projectEmployee = new ProjectEmployee();
        projectEmployee.setProject(projectB);
        projectEmployee.setEmployee(lucy);
        entityManager.persist(projectEmployee);


    }

    private void dataSetup_JameWorksOnProjectC(Project projectC) {

        Employee lucy = new Employee("James");
        entityManager.persist(lucy);
        entityManager.persist(projectC);
        ProjectEmployee projectEmployee = new ProjectEmployee();
        projectEmployee.setProject(projectC);
        projectEmployee.setEmployee(lucy);
        entityManager.persist(projectEmployee);

    }


}
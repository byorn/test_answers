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
public class Answer1_3ReturnCommonManager_Tests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectEmployeeRepository projectTeamRepository;



    /**
     * Answer 1.3 - For given two employees return common manager
     *
     * John and Jack's Common Manager is Lisa
     */
    @Test
    public void testTwoEmployeesHavingACommonManager() {

        Employee john = new Employee("John");
        Employee jack = new Employee("Jack");
        Employee lisa = new Employee("Lisa");

        john.setManager(lisa);
        jack.setManager(lisa);
        entityManager.persist(john);
        entityManager.persist(jack);
        entityManager.persist(lisa);

        Employee expectedLisa = getCommonManager(john, jack);

        assertThat(expectedLisa).isEqualTo(lisa);
    }


    /**
     * Answer 1.3 - For given two employees return common manager
     *
     * John and Lucy Do not have a common Manager - returns null
     */
    @Test
    public void testTwoEmployeesWithoutACommonManager() {

        Employee john = new Employee("John");
        Employee lucy = new Employee("Lucy");
        Employee lisa = new Employee("Lisa");
        Employee leonard = new Employee("Leonard");

        john.setManager(lisa);
        lucy.setManager(leonard);

        entityManager.persist(john);
        entityManager.persist(lucy);
        entityManager.persist(lisa);
        entityManager.persist(leonard);

        Employee expectingNull = getCommonManager(john, lucy);

        assertThat(expectingNull).isEqualTo(null);
    }

    private Employee getCommonManager(Employee employee1, Employee employee2){

        Employee commonManager = null;

        if(employee1.getManager()!=null && employee1.getManager().equals(employee2.getManager())){
            commonManager = employee1.getManager();
        }

        return commonManager;
    }

}
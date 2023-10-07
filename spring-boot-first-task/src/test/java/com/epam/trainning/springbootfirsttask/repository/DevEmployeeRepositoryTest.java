package com.epam.trainning.springbootfirsttask.repository;

import com.epam.trainning.springbootfirsttask.SpringBootFirstTaskApplication;
import com.epam.trainning.springbootfirsttask.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = SpringBootFirstTaskApplication.class)
@ActiveProfiles(value = "dev")
class DevEmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Predefined data is correct.")
    void predefinedDataIsCorrect() {
        final var employee = employeeRepository.findById(1L);
        Assertions.assertTrue(employee.isPresent());
        Assertions.assertEquals("Main DEV burglar", employee.get().getName());
    }

    @Test
    @DisplayName("Insertion of new Employee is working correctly.")
    void insertionIsWorkingCorrectly() {
        final var name = "John";
        final var newEmployee = new Employee(name);
        Assertions.assertEquals(2L, employeeRepository.count());
        employeeRepository.save(newEmployee);
        Assertions.assertEquals(3L, employeeRepository.count());
        final var employee = employeeRepository.findById(3L);
        Assertions.assertTrue(employee.isPresent());
        Assertions.assertEquals(name, employee.get().getName());
    }
}
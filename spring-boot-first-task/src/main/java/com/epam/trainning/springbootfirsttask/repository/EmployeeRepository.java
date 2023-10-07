package com.epam.trainning.springbootfirsttask.repository;

import com.epam.trainning.springbootfirsttask.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
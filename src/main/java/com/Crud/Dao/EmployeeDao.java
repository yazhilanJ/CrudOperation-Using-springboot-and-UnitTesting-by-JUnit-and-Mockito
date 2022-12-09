package com.Crud.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Crud.Model.Employee;
@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {

}

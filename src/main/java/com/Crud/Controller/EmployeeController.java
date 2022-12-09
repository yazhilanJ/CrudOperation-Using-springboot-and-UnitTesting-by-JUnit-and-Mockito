package com.Crud.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Crud.Model.Employee;
import com.Crud.Service.EmployeeService;

@RestController
public class EmployeeController {
	@Autowired
	EmployeeService empservice;
	@GetMapping("/employee")
	public List<Employee> getAllEmployee(){
		return empservice.getEmployee();
	}
	@GetMapping("/employee/{id}")
	public Employee getEmployeeById(@PathVariable("id") int empid) {
		return getEmployeeById(empid);
	}
	@PostMapping("/createemployee")
	public Employee createEmployee(@RequestBody  Employee employee) throws Exception {
		
		
		return empservice.save(employee);
}

	
	@PutMapping("/updateemployee")
	public Employee updateEmployee(@RequestBody  Employee employee) throws Exception {    //update
   
		return empservice.updateEmployee(employee);
		
	}
	@DeleteMapping("/deleteemployee/{empid}")
	public void delete(@PathVariable  int empid) throws Exception{     //delete
		
		 empservice.deleteEmployee(empid);
		
	}
}

package com.Crud.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Crud.Dao.EmployeeDao;
import com.Crud.Model.Employee;

@Service
public class EmployeeService {
	@Autowired
	EmployeeDao empdao;
	public List<Employee> getEmployee() {
		return empdao.findAll();
	}
	public Employee save(Employee employee) throws Exception {
		Optional<Employee> st = empdao.findById(employee.getEmpid());
		if (st.isPresent()) {
			throw new Exception("Employee  Id already present");
		}
		Employee emp = new Employee();
		emp.setEmpid(employee.getEmpid());
		emp.setName(employee.getName());
		emp.setAddress(employee.getAddress());
		emp.setPhno(employee.getPhno());
		emp.setEmail(employee.getEmail());
		return empdao.save(employee);

	}
	public Employee updateEmployee(Employee employee) throws Exception {
		Optional<Employee> emp=empdao.findById(employee.getEmpid());
		if(emp.isEmpty()){
			throw new Exception("Employee not present");}

		emp.ifPresent((Employee em)->
		{		
			
			if(employee.getName()!=null) {
				em.setName(employee.getName());
			}
			if(employee.getAddress()!=null) {
				em.setAddress(employee.getAddress());
			}
			if(employee.getPhno()!=null) {
				em.setPhno(employee.getPhno());
			}
			if(employee.getEmail()!=null) {
				em.setEmail(employee.getEmail());
			}
			
			empdao.save(em);
			});

	
		return employee;
	}

	 public void deleteEmployee(int empid) {
		
			empdao.deleteById(empid);
			
		
	 }	
	 public Optional<Employee> getEmployeeById(int id) {
	        return empdao.findById(id);
	 }


	

}

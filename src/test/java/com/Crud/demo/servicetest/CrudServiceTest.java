package com.Crud.demo.servicetest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Crud.Dao.EmployeeDao;
import com.Crud.Model.Employee;
import com.Crud.Service.EmployeeService;
@ExtendWith(MockitoExtension.class)
class CrudServiceTest {
	@Mock
	private EmployeeDao employeedao;
	@InjectMocks
	private EmployeeService employeeservice;
	
	private Employee employee;      
	        
	@BeforeEach
	public void setup() {
		employee=Employee.builder()
                .empid(1)
                .name("Ramesh")
                .address("TN")
                .phno(1234567890L)
                .email("ramesh@gmail.com")
               
                .build();
	}
	@Test
//	@Disabled
	public void createEmployee_Test() throws Exception {
		
		Mockito.when(employeedao.findById(employee.getEmpid())).thenReturn(Optional.empty());
		Mockito.when(employeedao.save(employee)).thenReturn(employee);

		Employee savedemployee=employeeservice.save(employee);

		assertThat(savedemployee).isNotNull();
		assertEquals(employee.getEmpid(),savedemployee.getEmpid());
		assertEquals(employee.getEmail(),savedemployee.getEmail());
		
		
		
	}
	@Test
//	@Disabled
	public void getAllEMployee_Test()throws Exception{
		Employee employee1=Employee.builder()
                .empid(2)
                .name("yazh")
                .address("TN")
                .phno(9751514553L)
                .email("yazh@gmail.com")
               
                .build();
		Mockito.when(employeedao.findAll()).thenReturn(List.of(employee,employee1));
		List<Employee> getallemployee=employeeservice.getEmployee();
		assertThat(getallemployee).isNotNull();
//		System.out.println(getallemployee);
		assertThat(getallemployee.size()).isEqualTo(2);
		
	}
	@Test
	public void updateEmployee_test() throws Exception{

		Mockito.when(employeedao.findById(employee.getEmpid())).thenReturn(Optional.of(employee));
	  
		Mockito.when(employeedao.save(employee)).thenReturn(employee);
		System.out.println("before"+employee);
		employee.setName("yazh");
		employee.setEmail("yaz@gmail.com");
		System.out.println("service"+employeeservice);
		try {
		Employee updatedemployee=employeeservice.updateEmployee(employee);
		System.out.println("after"+updatedemployee);
		System.out.println("get email from upemp  :"+updatedemployee.getEmail());
		assertThat(updatedemployee.getEmail()).isEqualTo("yaz@gmail.com");
		assertThat(updatedemployee.getName()).isEqualTo("yazh");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	@Test
//	@Disabled
	public void deleteEmployee_Test()throws Exception{
		doNothing().when(employeedao).deleteById(employee.getEmpid());
		employeeservice.deleteEmployee(employee.getEmpid());
		System.out.println(employee.getEmpid());
		verify(employeedao,times(1)).deleteById(employee.getEmpid());
		assertThat(employee.getEmpid()).isEqualTo(1);

	}
	
	

}

package com.Crud.demo.controllertest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.Crud.Controller.EmployeeController;
import com.Crud.Model.Employee;
import com.Crud.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest // 1
class CrudControllerTest {
	@Autowired
	private MockMvc mockmvc;// 2
	@MockBean
	private EmployeeService empservice;// 3
	@Autowired
	private ObjectMapper objmapper;// 4
	@InjectMocks
	private EmployeeController empcontroller;

	Employee emp = new Employee(9, "saran", "TN", 1212121212L, "saran@gmail.com");
	Employee emp1 = new Employee(10, "Arun", "TN", 1313131313L, "arun@gmail.com");
	Employee emp2 = new Employee(11, "Bala", "TN", 1414141414L, "bala@gmail.com");

	@Test
//	@Disabled
	public void getAllEmployee_test() throws Exception {
		List<Employee> listOfEmployees = new ArrayList<>(Arrays.asList(emp, emp1, emp2));
		Mockito.when(empservice.getEmployee()).thenReturn(listOfEmployees);
		ResultActions res = mockmvc.perform(get("/employee"));

		res.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$[0].empid", is(9)))
		.andExpect(jsonPath("$[0].name", is("saran"))).andExpect(jsonPath("$[1].address", is("TN")))
		.andExpect(jsonPath("$[2].email", is("bala@gmail.com")))
		.andExpect(jsonPath("$[2].phno", is(1414141414)))
		.andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
	}

	@Test
	public void createEmployee_test() throws Exception {
		Employee employee = Employee.builder().empid(1).name("pozhilan").address("TN").phno((long)888888888)
				.email("pozhil@gmail.com").build();

		Mockito.when(empservice.save(any(Employee.class))).thenReturn(employee);
		String content=objmapper.writeValueAsString(employee);
		ResultActions res = mockmvc.perform(post("/createemployee")
				.contentType(MediaType.APPLICATION_JSON)
				
				.content(content));
		res
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.name", is(employee.getName())))
		.andExpect(jsonPath("$.address", is(employee.getAddress())))
		.andExpect(jsonPath("$.email", is(employee.getEmail())))
//		.andExpect(jsonPath("$.phno", is( employee.getPhno())))

		;

	}

	@Test
//	@Disabled
	public void updateEmployee_test() throws Exception {
		
		Employee saveemp = Employee.builder().empid(1).name("pozhilan").address("TN").phno(888888888L)
				.email("pozhil@gmail.com").build();

		Employee updatedemployee = Employee.builder().empid(1).name("dharshan").phno(9755566689L).build();

		Mockito.when(empservice.getEmployeeById(saveemp.getEmpid())).thenReturn(Optional.of(saveemp));
		Mockito.when(empservice.updateEmployee(any(Employee.class))).thenReturn(updatedemployee);
		
		
		ResultActions res = mockmvc.perform(put("/updateemployee")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objmapper.writeValueAsString(updatedemployee)));
//		System.out.println(saveemp);
		res.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.name", is(updatedemployee.getName())))
		.andExpect(jsonPath("$.phno",is(updatedemployee.getPhno())));	

	}
	
	@Test
	public void deleteEmployeeById_Test() throws Exception {
//		int empid=1;
		Employee emp=Employee.builder().empid(1).build();
		doNothing().when(empservice).deleteEmployee(emp.getEmpid());
		ResultActions res=mockmvc.perform(delete("/deleteemployee/{empid}",emp.getEmpid()));
		res.andDo(print())
		.andExpect(status().isOk())

		;
		
	}

}

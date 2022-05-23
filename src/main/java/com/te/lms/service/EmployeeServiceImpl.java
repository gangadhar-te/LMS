package com.te.lms.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.te.lms.dto.AddEmployeeDTO;
import com.te.lms.pojo.Employee;
import com.te.lms.pojo.EmployeeRequest;
import com.te.lms.pojo.MockRatings;
import com.te.lms.pojo.Technologies;
import com.te.lms.pojo.UserInfo;
import com.te.lms.repo.EmployeeRepo;
import com.te.lms.repo.EmployeeRequestRepo;
import com.te.lms.repo.TechnologiesRepo;
import com.te.lms.repo.UserInfoRepo;
import com.te.lms.utility.EmailServicesLms;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo empRepo;

	@Autowired
	private TechnologiesRepo technologiesRepo;
	
	@Autowired
	private EmployeeRequestRepo requestRepo;
	
	@Autowired
	private EmailServicesLms emailServices;
	
	@Autowired
	private UserInfoRepo userInfoRepo;
	
	@Override
	public Employee addEmployee(AddEmployeeDTO employeedetails) {
		List<Integer> tech = employeedetails.getTech();
		List<Technologies> findAllById = technologiesRepo.findAllById(tech);
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeedetails, employee);
		employee.setTech(findAllById);
		String password = emailServices.sendPassword(employee.getEmail());
		Employee save = empRepo.save(employee);
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(employeedetails.getEmpId());
		userInfo.setPassword(password);
		userInfo.setAuthorities("ROLE_EMPLOYEE");
		userInfoRepo.save(userInfo);
		if (save == null) {
			throw new RuntimeException();
		}
		EmployeeRequest employeeRequest = new EmployeeRequest();
		employeeRequest.setEmpId(save.getId());
		requestRepo.save(employeeRequest);
		return save;
	}

	@Override
	public List<MockRatings> getMockDetails(String name) {
		Employee findByEmpName = empRepo.findByEmpName(name);
		List<MockRatings> ratings = findByEmpName.getRatings();
		System.out.println("mock rating ==========> " + ratings);
		if (ratings == null) {
			throw new RuntimeException();
		}
		return ratings;
	}

	@Override
	public Employee getDetails(String name) {
		Employee empName = empRepo.findByEmpName(name);
		if (empName.getStatus().equals("Active")) {
			return empName;
		} else {
			throw new RuntimeException();
		}
	}

	@Override
	public Employee updateEmployeeDeatils(Employee employee, Integer id) {
		Employee empDetails = empRepo.findById(id).get();
		BeanUtils.copyProperties(employee, empDetails);
		return empDetails;
	}

}

package com.te.lms.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddMentorDTO {

	@NotEmpty(message = "Mentor name cannot not be empty")
	@NotNull(message = "Mentor name is missing")
	private String name;

	@NotEmpty(message = "Mentor employeeId cannot not be empty")
	@NotNull(message = "Mentor employeeId is missing")
	private String empId;

	private String status;

	@NotEmpty(message = "Mentor emailId cannot not be empty")
	@NotNull(message = "Mentor emailId is missing")
	private String emailId;

	@NotEmpty(message = "Technology id cannot not be empty")
	@NotNull(message = "technology is missing")
	private List<Integer> techId;
}

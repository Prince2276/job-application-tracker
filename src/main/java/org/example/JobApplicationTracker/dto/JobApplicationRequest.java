package org.example.JobApplicationTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class JobApplicationRequest {

	@NotBlank(message = "Company name cannot be empty/blank or null")
	@Size(min = 2, max = 50, message = "Company name must be within 2 to 50 characters long")
	private String companyName;

	@NotBlank(message = "Job role cannot be empty/blank or null")
	@Size(min = 2, max = 100, message = "Job role must be within 2 to 10 characters long")
	private String jobRole;

	@NotBlank(message = "Job type cannot be empty/blank or null")
	@Size(min = 2, max = 10, message = "Job type must be within 2 to 10 characters long")
	@Pattern(
			regexp = "FULL_TIME|PART_TIME|INTERNSHIP|CONTRACT",
			message = "Job type must be FULL_TIME, PART_TIME, INTERNSHIP, or CONTRACT"
			)
	private String jobType;

	@NotBlank(message = "Status cannot be empty/blank or null")
	@Size(min = 2, max = 10, message = "Status must be within 2 to 10 characters long")
	@Pattern(
			regexp = "APPLIED|ASSESSMENT|INTERVIEW|OFFER|REJECTED",
			message = "Status must be APPLIED, ASSESSMENT, INTERVIEW, OFFER, or REJECTED"
			)
	private String status;

	@NotBlank(message = "Job URL cannot be empty/blank or null")
	private String jobUrl;

	@Size(max = 100, message = "Notes must be within 100 characters long")
	private String notes;

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getJobRole() {
		return jobRole;
	}
	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getJobUrl() {
		return jobUrl;
	}
	public void setJobUrl(String jobUrl) {
		this.jobUrl = jobUrl;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}



}

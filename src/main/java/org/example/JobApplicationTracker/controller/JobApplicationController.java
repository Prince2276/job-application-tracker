package org.example.JobApplicationTracker.controller;

import java.util.List;
import java.util.Map;

import org.example.JobApplicationTracker.dto.JobApplicationRequest;
import org.example.JobApplicationTracker.dto.JobApplicationResp;
import org.example.JobApplicationTracker.dto.UpdateReqDto;
import org.example.JobApplicationTracker.dto.UpdateRespDto;
import org.example.JobApplicationTracker.entity.JobApplication;
import org.example.JobApplicationTracker.repository.JobApplicationRepository;
import org.example.JobApplicationTracker.service.JobApplicatonService;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

	private JobApplicationRepository jobApplicationRepository;

	private	JobApplicatonService applicatonService;

	public JobApplicationController(JobApplicatonService applicatonService) {
		this.applicatonService = applicatonService;
	}

	@PostMapping
	public ResponseEntity<JobApplicationResp> addApplicant(
	  @Valid	 @RequestBody JobApplicationRequest applicationRequest) {

		JobApplicationResp addedApplication = applicatonService.add(applicationRequest);

		return ResponseEntity.ok(addedApplication);
	}

	@GetMapping
	public ResponseEntity<List<JobApplicationResp>> getAllApplications(){

		List<JobApplicationResp> applicationsList = applicatonService.getAllApplications();

		if(applicationsList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(applicationsList);
	}

	@GetMapping({"/{id}"})
	public ResponseEntity<JobApplicationResp> get(@PathVariable Long id){

		JobApplicationResp getApplicant = applicatonService.getApplicant(id);

		return ResponseEntity.ok(getApplicant);

	}

	@PutMapping({"/{id}"})
	public ResponseEntity<UpdateRespDto> updateApplicant(
		@PathVariable Long id,
			@RequestBody UpdateReqDto applicationRequest){

		UpdateRespDto updatedResp =	applicatonService.updateApplicant(id, applicationRequest);

		if(updatedResp == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(updatedResp);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteApplicant(@PathVariable Long id) {

		applicatonService.deleteApplicant(id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<JobApplication>> getByStatus(@PathVariable String status) {

		List<JobApplication> listOfApplicants =  applicatonService.getAllByStatus(status);

		if(listOfApplicants == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(listOfApplicants);
	}

	@GetMapping("/company/{companyName}")
	public ResponseEntity<List<JobApplication>> getByCompanyName(@PathVariable String companyName) {

		List<JobApplication> getApplicants = applicatonService.getAllByCompanyName(companyName);

		if(getApplicants == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(getApplicants);
	}

	@GetMapping("/role/{jobRole}")
	public ResponseEntity<List<JobApplication>> getByRole(@PathVariable String jobRole) {

		List<JobApplication> getApplicants = applicatonService.getAllByJobRole(jobRole);

		if(getApplicants == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(getApplicants);
	}









}

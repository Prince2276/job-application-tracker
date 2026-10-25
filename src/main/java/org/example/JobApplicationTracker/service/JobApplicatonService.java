package org.example.JobApplicationTracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.example.JobApplicationTracker.dto.JobApplicationRequest;
import org.example.JobApplicationTracker.dto.JobApplicationResp;
import org.example.JobApplicationTracker.dto.UpdateReqDto;
import org.example.JobApplicationTracker.dto.UpdateRespDto;
import org.example.JobApplicationTracker.entity.JobApplication;
import org.example.JobApplicationTracker.exception.ResourceNotFoundException;
import org.example.JobApplicationTracker.repository.JobApplicationRepository;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JobApplicatonService {

	private JobApplicationRepository jobApplicationRepository;

	public JobApplicatonService(JobApplicationRepository applicationRepository) {
		this.jobApplicationRepository = applicationRepository;
	}


	public JobApplicationResp add(JobApplicationRequest applicationReq) {

		JobApplication application = maptoEntity(applicationReq);

		JobApplication applicationResp = jobApplicationRepository.save(application);

		return mapToDto(applicationResp);
	}

	public List<JobApplicationResp> getAllApplications(){

		List<JobApplication> applicationsList = jobApplicationRepository.findAll();

		return applicationsList.stream()
				.map(this::mapToDto)
				.toList();
	}

	public JobApplicationResp getApplicant(Long id) {

		JobApplication applicationResp =	jobApplicationRepository
				.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException("Job application not found with id: " + id));

		return mapToDto(applicationResp);
	}

	public UpdateRespDto updateApplicant(Long id, UpdateReqDto applicationReq) {

//		Optional<JobApplication> applicant = jobApplicationRepository.findById(id);

		JobApplication applicantToSave = jobApplicationRepository
				.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException("Job application not found with id: " + id));

		applicantToSave.setCompanyName(applicationReq.getCompanyName());
		applicantToSave.setJobRole(applicationReq.getJobRole());
		applicantToSave.setJobType(applicationReq.getJobType());
		applicantToSave.setApplicationDate(LocalDate.now());
		applicantToSave.setJobUrl(applicationReq.getJobUrl());
		applicantToSave.setNotes(applicationReq.getNotes());
		applicantToSave.setStatus(applicationReq.getStatus());
		
		JobApplication savedapplicant = jobApplicationRepository.save(applicantToSave);

		return mapToUpdateDto(savedapplicant);
	}

	public void deleteApplicant(Long id) {
		
		JobApplication applicantToBeDeleted = jobApplicationRepository
				.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException("Job application not found with id: " + id));

		jobApplicationRepository.deleteById(id);
	}

	public List<JobApplication> getAllByStatus(String status){

		List<JobApplication> getApplicants = jobApplicationRepository.findAllByStatus(status);

		if(getApplicants.isEmpty()) {
			return null;
		}

		return getApplicants;
	}

	public List<JobApplication> getAllByCompanyName(String companyName) {

		List<JobApplication> getApplicants = jobApplicationRepository.findAllByCompanyName(companyName);

		if(getApplicants.isEmpty()) {
			return null;
		}

		return getApplicants;
	}

	public List<JobApplication> getAllByJobRole(String jobRole) {

		List<JobApplication> getApplicants = jobApplicationRepository.findAllByJobRole(jobRole);

		if(getApplicants.isEmpty()) {
			return null;
		}

		return getApplicants;
	}

	public JobApplication maptoEntity(JobApplicationRequest applicationRequest) {

		JobApplication application = new JobApplication();

		application.setCompanyName(applicationRequest.getCompanyName());
		application.setJobRole(applicationRequest.getJobRole());
		application.setJobType(applicationRequest.getJobType());
		application.setJobUrl(applicationRequest.getJobUrl());
		application.setStatus(applicationRequest.getStatus());
		application.setNotes(applicationRequest.getNotes());

		application.setApplicationDate(LocalDate.now());

		return application;
	}

	public JobApplicationResp mapToDto(JobApplication application) {

		JobApplicationResp applicationResp = new JobApplicationResp();

		applicationResp.setId(application.getId());
		applicationResp.setCompanyName(application.getCompanyName());
		applicationResp.setJobRole(application.getJobRole());
		applicationResp.setJobType(application.getJobType());
		applicationResp.setStatus(application.getStatus());
		applicationResp.setNotes(application.getNotes());
		applicationResp.setJobUrl(application.getJobUrl());
		applicationResp.setId(application.getId());

		applicationResp.setMessage("Application saved successfully.");
		applicationResp.setApplicationDate(application.getApplicationDate());

		return applicationResp;
	}
	
	public UpdateRespDto mapToUpdateDto(JobApplication application) {
		
		UpdateRespDto updateRespDto = new UpdateRespDto();
		
		updateRespDto.setCompanyName(application.getCompanyName());
		updateRespDto.setJobRole(application.getJobRole());
		updateRespDto.setJobType(application.getJobType());
		updateRespDto.setJobUrl(application.getJobUrl());
		updateRespDto.setStatus(application.getStatus());
		updateRespDto.setNotes(application.getNotes());
		updateRespDto.setApplicationDate(LocalDate.now());
		updateRespDto.setMessage("Applicant updated successfully");
		
		
		return updateRespDto;
	}

}

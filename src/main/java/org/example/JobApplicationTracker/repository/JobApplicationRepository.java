package org.example.JobApplicationTracker.repository;

import java.util.List;

import org.example.JobApplicationTracker.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long>{
   
	List<JobApplication> findAllByStatus(String status);
	
	List<JobApplication> findAllByCompanyName(String companyName);
	
	List<JobApplication> findAllByJobRole(String jobRole);
}

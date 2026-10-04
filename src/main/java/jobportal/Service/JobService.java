package jobportal.Service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import jobportal.Annotation.Authorization;
import jobportal.Entity.Job;
import jobportal.Exception.ResourceNotFoundException;

import jobportal.Repository.JobRepository;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;


import java.time.LocalDateTime;


@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    @Transactional
    @Authorization(roles={"ADMIN", "RECRUITER"})
    public Job saveJob(Job job) {
       return jobRepository.save(job);

    }
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.SERIALIZABLE,
            readOnly = true)
    public Page<Job> getAllJobs(Pageable pageable) {
//        Pageable pageable = PageRequest.of(0, 2, Sort.by("createdAt").descending());
        return jobRepository.findAllByDeletedFalse(pageable);
//        return page.getContent();
    }

    @Transactional(readOnly = true)
    public Page<Job> searchJobs(String keyword,Pageable pageable) {
        return jobRepository.SearchJob(keyword,pageable);
    }


    @Transactional(readOnly = true)
    public Job getJobById(int id) {
        return jobRepository.findByIdAndDeletedFalse(id).orElseThrow(()-> new ResourceNotFoundException("Job Not Found"+id));
    }
    @Transactional
    public Job updateJob(int id,Job job) {
        Job updatedJob = jobRepository.findByIdAndDeletedFalse(id).orElseThrow(()-> new ResourceNotFoundException("Job not found"+id));

        if(job.getTitle()!=null){
            updatedJob.setTitle(job.getTitle());
        }
        if(job.getLocation()!=null){
            updatedJob.setLocation(job.getLocation());
        } if(job.getCompany()!=null){
            updatedJob.setCompany(job.getCompany());
        }
        updatedJob.setUpdatedAt(LocalDateTime.now());
        return jobRepository.save(updatedJob);
    }
    @Transactional
    @Authorization(roles={"ADMIN"})
    public boolean deletion(int id){
        if(!jobRepository.existsByIdAndDeletedFalse(id)) {
            return false;
        }
        jobRepository.deleteById(id);
        return true;
    }
    @Transactional
    public boolean softDelete(int id) {
       Job job= jobRepository.findByIdAndDeletedFalse(id).orElse(null);
       if(job == null) {
           return false;
       }
       job.setDeleted(true);
       jobRepository.save(job);
       return true;
    }
    @Transactional
    public Job restoreJob(int id) {

        Job job = jobRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Job not found"+id));



        job.setDeleted(false);
        jobRepository.save(job);

        return job;
    }


}

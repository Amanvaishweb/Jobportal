package jobportal.Controller;

import jakarta.validation.Valid;
import jobportal.DTO.JobRequestDto;
import jobportal.DTO.JobResponseDto;
import jobportal.Entity.Job;
import jobportal.Entity.JobSkills;
import jobportal.Service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;


import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/Base")

public class JobController{
    private final JobService jobService;
    public JobController(JobService jobService) {

        this.jobService = jobService;
    }

    @PostMapping("/save")
    public ResponseEntity<JobResponseDto> data(@Valid @RequestBody JobRequestDto job ){
        Job j = DtoToEntity(job);
        Job save= jobService.saveJob(j);
        JobResponseDto saved= EntityToDto(save);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }

    @GetMapping("/jobs")
    public ResponseEntity<Page<Job>> jobs(@PageableDefault(size = 4,sort = "createdAt",direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
       if (pageable.getPageSize()>4){
           pageable=PageRequest.of(   pageable.getPageNumber(),
                   4,
                   pageable.getSort());
       }
        Page<Job> allJobs = jobService.getAllJobs(pageable);
        if(allJobs.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allJobs, HttpStatus.OK);
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJob(@PathVariable int id)
    {
        Job j=jobService.getJobById(id);

        return new ResponseEntity<>(j,HttpStatus.OK);
    }
    @GetMapping("/jobs/search")
    public ResponseEntity<Page<Job>> searchJobs(

            @RequestParam String keyword,

            @PageableDefault(
                    size = 4,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        // Maximum page size = 4
        if (pageable.getPageSize() > 4) {

            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    4,
                    pageable.getSort()
            );
        }

        Page<Job> jobs =
                jobService.searchJobs(keyword, pageable);

        if (jobs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(jobs);
    }

    @PutMapping("/jobs/{id}")

    public ResponseEntity<JobResponseDto> updateJob(@PathVariable int id, @RequestBody JobRequestDto job){
        Job j = DtoToEntityUpdate(job);
        Job update=jobService.updateJob(id,j);
        JobResponseDto saved= EntityToDto(update);

        return new ResponseEntity<>(saved,HttpStatus.OK);
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable int id){
        boolean check = jobService.deletion(id);
        if(!check){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
     return ResponseEntity.noContent().build();
    }
    @PatchMapping("/jobs/{id}")
    public ResponseEntity<Void> softDeleteJob(@PathVariable int id){
        boolean check = jobService.softDelete(id);
        if(!check){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/jobs/restore/{id}")
    public ResponseEntity<Job> restoreSoftDelete(@PathVariable int id){
        Job job =jobService.restoreJob(id);

        return new ResponseEntity<>(job,HttpStatus.OK);
    }
    @GetMapping("Csrf")
    public CsrfToken getToken(CsrfToken token) {
        return token;
    }
    public Job DtoToEntity(JobRequestDto j){
        Job job = new Job();
        job.setTitle(j.getTitle());
        job.setCompany(j.getCompany());
        job.setLocation(j.getLocation());
        job.setDeleted(false);
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());
        job.setAddress(j.getAddress());
        if (j.getJobSkills() != null) {
            job.setJobSkills(j.getJobSkills());

            for (JobSkills skill : j.getJobSkills()) {
                skill.setJob(job);
            }
        }
        return job;
    }
    public JobResponseDto EntityToDto(Job job){
        JobResponseDto j= new JobResponseDto();
        j.setId(job.getId());
        j.setTitle(job.getTitle());
        j.setCompany(job.getCompany());
        j.setLocation(job.getLocation());
        j.setUpdatedAt(job.getUpdatedAt());
        j.setCreatedAt(job.getCreatedAt());
        j.setAddress(job.getAddress());
        j.setJobSkills(job.getJobSkills());
       return j;
    }
    public Job DtoToEntityUpdate(JobRequestDto j){
        Job job = new Job();
        job.setTitle(j.getTitle());
        job.setCompany(j.getCompany());
        job.setLocation(j.getLocation());
        job.setUpdatedAt(LocalDateTime.now());
        job.setAddress(j.getAddress());
        if (j.getJobSkills() != null) {
            job.setJobSkills(j.getJobSkills());

            for (JobSkills skill : j.getJobSkills()) {
                skill.setJob(job);
            }
        }
        return job;
    }


}

package jobportal.DTO;

import jobportal.Entity.Address;
import jobportal.Entity.JobSkills;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class JobResponseDto {
    private int id;
    private String title;
    private String company;
    private String location;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Address address;
    private Set<JobSkills> jobSkills;


}

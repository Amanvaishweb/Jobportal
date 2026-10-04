package jobportal.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jobportal.Entity.Address;
import jobportal.Entity.JobSkills;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class JobRequestDto {
    @NotBlank(message = "Title cannot be null/Empty or Blank")
    private String title;
    @NotBlank(message = "Company cannot be null/Empty or Blank")
    private String company;
    @NotBlank(message = "Location cannot be null/Empty or Blank")
    private String location;

    @Valid
    private Address address;
    @NotEmpty(message = "skills cannot be null or empty")
    private Set<JobSkills> jobSkills;



}

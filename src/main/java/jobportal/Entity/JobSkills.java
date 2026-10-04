package jobportal.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class JobSkills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String skill;

    @Column(nullable = false)
    private int yearsExperience;
    @ManyToOne(optional = false)
    @JoinColumn(name = "Job_ID",
    nullable = false)
    @JsonBackReference
    private Job job;







    // getters and setters



}
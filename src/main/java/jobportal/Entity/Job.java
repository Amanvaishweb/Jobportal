package jobportal.Entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Column(
        name= "company",
        nullable = false,
        length = 100
    )
    private String company;
    private String location;
    private boolean deleted;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    @Transient
    private int openPosition;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "job",
    cascade = CascadeType.ALL,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            },
    orphanRemoval = true,
    fetch = FetchType.LAZY)

    @JsonManagedReference
    private Set<JobSkills> jobSkills=new HashSet<>() ;



}

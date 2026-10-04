package jobportal.Repository;
import jobportal.Entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job,Integer> {
     boolean existsByIdAndDeletedFalse(int id);

//    @EntityGraph(attributePaths = "jobSkills")
    Page<Job> findAllByDeletedFalse(Pageable pageable);
//    List<Job> findAllByDeletedFalse();


    Optional<Job> findByIdAndDeletedFalse(int id);

    @Query("""
Select j
FROM Job j
WHERE j.deleted=false
AND(
LOWER(j.company) LIKE LOWER(CONCAT('%',:keyword,'%'))
OR
LOWER(j.title) LIKE LOWER(CONCAT('%',:keyword,'%'))

)
""")
Page<Job> SearchJob(@Param("keyword")String keyword, Pageable pageable);

}
// public class JobRepository{
//    @PersistenceContext
//    private EntityManager em;
//    public Job save(Job job) {
//     em.persist(job);
//     return job;
//    }
//
//    public List<Job> findAllByDeletedFalse() {
//     return em.createQuery("select value from Job value", Job.class).getResultList();
//    }
//    public  Job findByIdAndDeletedFalse(int id) {
//        return em.find(Job.class, id);
//    }
//
//
//}

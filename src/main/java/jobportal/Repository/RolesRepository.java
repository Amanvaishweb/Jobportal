package jobportal.Repository;

import jobportal.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Integer> {
    public Role findByName(String name);
}

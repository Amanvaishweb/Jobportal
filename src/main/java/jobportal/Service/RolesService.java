package jobportal.Service;

import jobportal.Entity.Role;
import jobportal.Repository.RolesRepository;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    private RolesRepository rolesRepository;
    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }
    public Role saveRole(Role role) {
       return rolesRepository.save(role);
    }
}

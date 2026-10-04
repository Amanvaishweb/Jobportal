package jobportal.Controller;

import jobportal.Entity.Role;
import jobportal.Service.RolesService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Roles")
public class RolesController {
    RolesService rolesService;
    public RolesController( RolesService rolesService) {
        this.rolesService = rolesService;
    }
    @PostMapping("/Save")
    public Role saveRoles(@RequestBody Role role) {
      return  rolesService.saveRole(role);
    }
}

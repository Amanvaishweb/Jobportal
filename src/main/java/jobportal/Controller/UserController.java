package jobportal.Controller;

import jobportal.DTO.UserRequestDTO;
import jobportal.DTO.UserResponseDTO;
import jobportal.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    public AuthService authService;
    public UserController(AuthService authService){
        this.authService=authService;
    }
    @PostMapping("/Register")
    public ResponseEntity<UserResponseDTO> RegisterUser(@RequestBody UserRequestDTO userRequestDTO) {
       UserResponseDTO userResponseDTO= authService.register(userRequestDTO);
       return ResponseEntity.ok(userResponseDTO);
}
}

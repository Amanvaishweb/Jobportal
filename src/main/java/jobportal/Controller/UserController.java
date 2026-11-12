
package jobportal.Controller;

import jakarta.validation.Valid;
import jobportal.DTO.UserRequestDTO;
import jobportal.DTO.UserResponseDTO;
import jobportal.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/Register")
    public ResponseEntity<UserResponseDTO> registerUser(
            @Valid @RequestBody UserRequestDTO userRequestDTO) {

        UserResponseDTO userResponseDTO =
                authService.register(userRequestDTO);

        return ResponseEntity.ok(userResponseDTO);
    }
}

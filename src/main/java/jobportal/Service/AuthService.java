package jobportal.Service;

import jobportal.DTO.UserRequestDTO;
import jobportal.DTO.UserResponseDTO;
import jobportal.Entity.Role;
import jobportal.Entity.User;
import jobportal.Repository.RolesRepository;
import jobportal.Repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       RolesRepository rolesRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO register(UserRequestDTO userRequestDTO) {

        // Check duplicate username
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Check duplicate email
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setEnabled(true);

        // Assign USER role
        Role role = rolesRepository.findByName("USER");

        if (role == null) {
            throw new RuntimeException("USER role not found in database");
        }

        user.getRoles().add(role);

        User savedUser = userRepository.save(user);

        UserResponseDTO response = new UserResponseDTO();
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setMessage("Successfully registered");

        return response;
    }
}
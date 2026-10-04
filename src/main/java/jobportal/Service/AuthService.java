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
    UserRepository userRepository;
    RolesRepository rolesRepository;
    PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository=userRepository;
        this.rolesRepository=rolesRepository;
        this.passwordEncoder=passwordEncoder;
    }
    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setUsername(userRequestDTO.getUsername());
        user.setEnabled(true);
        Role role= rolesRepository.findByName("ADMIN");
        user.getRoles().add(role);
        User value= userRepository.save(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername(value.getUsername());
        userResponseDTO.setMessage("Successfully registered");
        return userResponseDTO;

    }
}

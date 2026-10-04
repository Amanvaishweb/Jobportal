package jobportal.Controller;


import jobportal.DTO.AuthRequest;
import jobportal.DTO.AuthResponse;

import jobportal.Service.LoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Auth")

public class LoginController {
    LoginService loginService;
    AuthenticationManager authenticationManager;
    LoginController(LoginService loginService,  AuthenticationManager authenticationManager) {
        this.loginService = loginService;
        this.authenticationManager = authenticationManager;
    }
    @PostMapping("/login")
    public AuthResponse loginController(@RequestBody AuthRequest authRequest ){
        Authentication  authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                authRequest.getUsername(), authRequest.getPassword()
        );
        Authentication authenticationPrinciple=authenticationManager.authenticate(authenticationRequest);
        String token= loginService.generateToken(authenticationPrinciple);

    return  new AuthResponse(token);
    }
}

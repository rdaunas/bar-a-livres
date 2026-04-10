package fr.eni.baralivre.back.api;

import fr.eni.baralivre.back.dto.UserDTO;
import fr.eni.baralivre.back.entity.Role;
import fr.eni.baralivre.back.entity.User;
import fr.eni.baralivre.back.repository.UserRepository;
import fr.eni.baralivre.back.security.JwtUtil;
import fr.eni.baralivre.back.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtils;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder encoder,
            JwtUtil jwtUtils,
            UserDetailsServiceImpl userDetailsServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }


    @PostMapping("/signin")
    public String authenticateUser(@RequestBody UserDTO user) {
        log.info("Signin attempt");
        Authentication authentication = authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info(userDetails.getAuthorities().toString());
        log.info(userDetailsServiceImpl.getUserRole(userDetails.getUsername()));
        return jwtUtils.generateToken(userDetails.getUsername(),userDetailsServiceImpl.getUserRole(userDetails.getUsername()));
    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody UserDTO user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "User already exists!";
        }

        final User newUser = new User(
                user.getEmail(),
                encoder.encode(user.getPassword()),
                new Role()
        );
        userRepository.save(newUser);
        return "User registered successfully!";
    }
    @GetMapping("/test")
    public String test() {

        return "test";
    }
}
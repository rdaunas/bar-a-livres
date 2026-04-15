package fr.eni.baralivre.back.api;

import fr.eni.baralivre.back.dto.UserDTO;
import fr.eni.baralivre.back.entity.Role;
import fr.eni.baralivre.back.entity.User;
import fr.eni.baralivre.back.repository.UserRepository;
import fr.eni.baralivre.back.security.JwtUtil;
import fr.eni.baralivre.back.service.InscriptionService;
import fr.eni.baralivre.back.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtils;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final InscriptionService inscriptionService;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder encoder,
            JwtUtil jwtUtils,
            UserDetailsServiceImpl userDetailsServiceImpl, InscriptionService inscriptionService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.inscriptionService = inscriptionService;
    }


    @PostMapping("/signin")
    public ResponseEntity authenticateUser(@RequestBody UserDTO user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );
            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User AutnehticatedUser = userDetailsServiceImpl.getUserInformation(userDetails.getUsername());

            Map<String,String> payload = new HashMap<>();
            payload.put("token",jwtUtils.generateToken(userDetails.getUsername(),AutnehticatedUser.getNom(),AutnehticatedUser.getPrenom(),AutnehticatedUser.getId(),AutnehticatedUser.getRole().getLabel()));
            return new ResponseEntity<>(payload,HttpStatus.OK);
        }
        catch(AuthenticationException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user) {

        if(inscriptionService.inscrire(user)) {
            Map<String,String> payload = new HashMap<>();
            payload.put("message","Inscription réussie");
            return new  ResponseEntity<>(payload, HttpStatus.OK);
        }
        Map<String,String> payload = new HashMap<>();
        payload.put("message","L'utilisateur existe déjà.");
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);

    }
    @GetMapping("/test")
    public String test() {

        return "test";
    }
}
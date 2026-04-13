package fr.eni.baralivre.back.service;

import fr.eni.baralivre.back.dto.UserDTO;

import fr.eni.baralivre.back.entity.User;
import fr.eni.baralivre.back.repository.RoleRepository;
import fr.eni.baralivre.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InscriptionService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;
    @Autowired
    public InscriptionService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public boolean inscrire(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return false;
        }

        final User newUser =  User.builder()
                .nom(userDTO.getNom())
                .prenom(userDTO.getPrenom())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(roleRepository.findByLabel("USER"))
                .build();
        userRepository.save(newUser);
        return true;
    }
}
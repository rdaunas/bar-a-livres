package fr.eni.baralivre.back.service;


import fr.eni.baralivre.back.dto.SecurityUser;
import fr.eni.baralivre.back.entity.User;
import fr.eni.baralivre.back.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user== null) {
            throw  new UsernameNotFoundException("User not found");
        }
        return  new SecurityUser(
                user.getEmail(),
                user.getPassword(),
                user.getRole().getLabel()
        );
    }
    public User getUserInformation(String email) {
        return userRepository.findByEmail(email);
    }
    public String getUserRole(String username) {
        try {
            User user = userRepository.findByEmail(username);
            return user.getRole().getLabel();
        } catch (Exception e) {
            throw new EntityNotFoundException(e);
        }
    }
}

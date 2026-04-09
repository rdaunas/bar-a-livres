package fr.eni.baralivre.back.service;


import fr.eni.baralivre.back.entity.User;
import fr.eni.baralivre.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return  new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword()
               // user.getRole() add additional claim here
        );
    }
}

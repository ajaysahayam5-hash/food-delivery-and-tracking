package com.fooddelivery.security;

import com.fooddelivery.entity.User;
import com.fooddelivery.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository ur){ this.userRepository=ur; }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                u.getEmail(), u.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_"+u.getRole()))
        );
    }
}

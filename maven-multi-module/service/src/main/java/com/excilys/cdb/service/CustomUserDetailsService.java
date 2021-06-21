package com.excilys.cdb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.model.CustomUserDetails;
import com.excilys.cdb.model.User;
import com.excilys.cdb.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = this.userRepository.findByUsername(username);
        userOpt.orElseThrow(() -> new UsernameNotFoundException("Not found  :" + username));
        return userOpt.map(CustomUserDetails::new).get();
    }

}

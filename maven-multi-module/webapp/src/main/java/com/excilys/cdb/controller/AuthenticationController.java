package com.excilys.cdb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.AuthenticationRequest;
import com.excilys.cdb.dto.AuthenticationResponse;
import com.excilys.cdb.model.CustomUserDetails;
import com.excilys.cdb.service.CustomUserDetailsService;
import com.excilys.cdb.service.JwtService;

@RestController
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService customUserDetailsService;
    private JwtService jwtService;

    public AuthenticationController(AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailsService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final CustomUserDetails userDetails = this.customUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = this.jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}

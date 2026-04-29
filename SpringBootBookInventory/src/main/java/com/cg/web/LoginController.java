package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.security.AuthRequest;
import com.cg.security.JwtService;

@RestController
public class LoginController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    	
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
        	 List<String> roles = authentication.getAuthorities()
                     .stream()
                     //.map(e->e.getAuthority())
                     .map(GrantedAuthority::getAuthority)
                     .toList();
        	 return jwtService.generateToken(authRequest.getUsername(), roles);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}

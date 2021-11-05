package com.cezarybek.todoApp.controller;

import com.cezarybek.todoApp.DTO.AuthResponseDTO;
import com.cezarybek.todoApp.DTO.LoginDTO;
import com.cezarybek.todoApp.model.Role;
import com.cezarybek.todoApp.model.User;
import com.cezarybek.todoApp.repository.RoleRepository;
import com.cezarybek.todoApp.repository.UserRepository;
import com.cezarybek.todoApp.security.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    @ApiOperation(value = "Login to obtain the JWT Token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Get token from tokenProvider class
        String token = jwtTokenProvider.generateToken(authentication);
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);
        AuthResponseDTO authResponse = new AuthResponseDTO(token, currentDate, expirationDate, "Bearer");
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Signup in order to start to use the API")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.IM_USED);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>("Email is already register, please login.", HttpStatus.IM_USED);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role roles = roleRepository.findByName("ROLE_USER");

        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }
}

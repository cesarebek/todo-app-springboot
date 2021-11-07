package com.cezarybek.todoApp.controller;

import com.cezarybek.todoApp.DTO.AuthResponseDTO;
import com.cezarybek.todoApp.DTO.LoginDTO;
import com.cezarybek.todoApp.DTO.UserDTO;
import com.cezarybek.todoApp.model.User;
import com.cezarybek.todoApp.repository.RoleRepository;
import com.cezarybek.todoApp.repository.UserRepository;
import com.cezarybek.todoApp.security.JwtTokenProvider;
import com.cezarybek.todoApp.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

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

    @ApiOperation(value = "Signup a new account")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody User user) {
        return userService.addUser(user);
    }
}

package com.julian.authservice.controller;

import com.julian.authservice.dto.LoginRequest;
import com.julian.authservice.dto.LoginResponse;
import com.julian.authservice.dto.UserDTO;
import com.julian.authservice.model.User;
import com.julian.authservice.security.JwtUtil;
import com.julian.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.registerUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Buscar usuario por email
        User user = userService.findByEmail(loginRequest.getEmail());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo incorrecto");
        }

        // Validar contraseña
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        // Generar JWT
        String token = jwtUtil.generateToken(user.getEmail());

        // Devolver token en JSON
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @GetMapping("/api/protegida")
    public ResponseEntity<String> protegida() {
        return ResponseEntity.ok("¡Acceso autorizado con JWT!");
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getProfile() {
        User user = userService.getCurrentUser();
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail());
        return ResponseEntity.ok(userDTO);
    }


}

package pl.edu.pk.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.edu.pk.demo.model.User;
import pl.edu.pk.demo.repository.UserRepository;
import pl.edu.pk.demo.security.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register") // Pkt 1: Rejestracja z BCrypt
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) user.setRole("ROLE_USER");
        userRepository.save(user);
        return "Zarejestrowano pomyślnie!";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        if (!passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Błędne hasło!");
        }

        String token = jwtService.generateToken(userDetails);
        return Map.of("token", token);
    }
}
package pl.edu.pk.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.edu.pk.demo.repository.UserRepository;

@Configuration
public class ApplicationConfig {

    private final UserRepository userRepository;

    public ApplicationConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // pl/edu/pk/demo/security/ApplicationConfig.java

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            pl.edu.pk.demo.model.User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Użytkownik nie istnieje"));

            // Najbezpieczniejszy sposób: bierzemy to co w bazie i upewniamy się,
            // że Spring dostanie czyste "ADMIN" do metody roles()
            String cleanRole = user.getRole().replace("ROLE_", "");

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(cleanRole) // To doda "ROLE_" automatycznie
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
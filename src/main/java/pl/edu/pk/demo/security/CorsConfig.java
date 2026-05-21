package pl.edu.pk.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Dotyczy wszystkich Twoich endpointów [cite: 156]
                        .allowedOrigins("http://localhost:4200") // Zezwól na dostęp Angularowi [cite: 156]
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Dozwolone metody [cite: 156]
                        .allowedHeaders("*") // Wszystkie nagłówki [cite: 157]
                        .allowCredentials(true); // Pozwala na przesyłanie tokenów JWT [cite: 158]
            }
        };
    }
}
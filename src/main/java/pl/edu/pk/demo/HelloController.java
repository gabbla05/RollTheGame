package pl.edu.pk.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    // Zadanie 1: Endpoint GET /hello zwracający tekst z pozdrowieniem (np. "Hello, World!")
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    // Zadanie 2: Endpoint GET /hello/{name} przyjmujący parametr ścieżki i zwracający
    //spersonalizowaną odpowiedź (np. "Hello, Anna!")
    @GetMapping("/hello/{name}")
    public String helloName(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    // Zadanie 3: Endpoint GET /greet?name=X przyjmujący parametr jako query string i zwracający odpowiedź
    @GetMapping("/greet")
    public String greet(@RequestParam(name = "name", defaultValue = "Stranger") String name) {
        return "Hello, " + name + "!";
    }

    // Zadanie 4: Endpoint GET /info zwracający obiekt JSON z polami: autor, framework, wersja aplikacji
    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of(
                "autor", "Gabrysia Błaut",
                "framework", "Spring Boot",
                "wersja aplikacji", "1.0.0"
        );
    }
}
package prologbackend.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Lazy
@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/main")
    public ResponseEntity<String> main() {
        return ResponseEntity.ok("This is main page");
    }

}

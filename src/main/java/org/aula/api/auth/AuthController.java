package org.aula.api.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aula.config.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "0. Auth", description = "Autenticação e geração de token JWT")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Retorna um token JWT para autenticação")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // Aqui você validaria usuário/senha no banco
        // Por simplicidade, aceitamos qualquer login
        if ("admin".equals(request.username()) && "admin".equals(request.senha())) {
            String token = jwtService.gerarToken(request.username());
            return ResponseEntity.ok(new LoginResponse(token));
        }
        return ResponseEntity.status(401).build();
    }

    public record LoginRequest(String username, String senha) {}
    public record LoginResponse(String token) {}
}
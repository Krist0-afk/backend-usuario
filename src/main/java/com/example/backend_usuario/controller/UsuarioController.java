package com.example.backend_usuario.controller;

import com.example.backend_usuario.model.Usuario;
import com.example.backend_usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping("/sincronizar")
    public Usuario sincronizarDesdeAzure(@AuthenticationPrincipal Jwt jwt) {
        // Extraemos la información del token que el Frontend envía al backend
        String correo = jwt.getClaimAsString("preferred_username"); // O el claim que use tu tenant para el correo
        String nombre = jwt.getClaimAsString("name");

        return service.sincronizarUsuario(nombre, correo);
    }
}
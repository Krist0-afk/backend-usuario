package com.example.backend_usuario.repository;

import com.example.backend_usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para buscar si el usuario de Azure ya está en nuestra BD local
    Optional<Usuario> findByCorreo(String correo);
}

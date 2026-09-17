package com.example.backend_usuario.service;

import com.example.backend_usuario.config.RabbitMQConfig;
import com.example.backend_usuario.dto.UsuarioEventDto;
import com.example.backend_usuario.model.Usuario;
import com.example.backend_usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor // Lombok crea el constructor automáticamente para inyectar dependencias
public class UsuarioService {

    private final UsuarioRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public Usuario sincronizarUsuario(String nombre, String correo) {
        Optional<Usuario> usuarioExistente = repository.findByCorreo(correo);

        if (usuarioExistente.isPresent()) {
            // Si el usuario ya existe, simplemente lo devolvemos, no enviamos mensaje
            return usuarioExistente.get();
        }

        // Si no existe, es su primer inicio de sesión. Lo guardamos.
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setCorreo(correo);
        Usuario guardado = repository.save(nuevoUsuario);

        // Preparamos el mensaje para RabbitMQ
        UsuarioEventDto evento = new UsuarioEventDto(guardado.getId(), guardado.getNombre(), guardado.getCorreo());

        // Enviamos el mensaje de forma asíncrona a RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, evento);
        
        System.out.println("Usuario sincronizado y mensaje enviado a RabbitMQ para: " + correo);
        return guardado;
    }
}
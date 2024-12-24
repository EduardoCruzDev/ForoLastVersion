package com.eduardocruzdev.forolastversion.controller;


import com.eduardocruzdev.forolastversion.domain.usuarios.DatosRegistroUsuario;
import com.eduardocruzdev.forolastversion.domain.usuarios.DatosRespuestaUsuario;
import com.eduardocruzdev.forolastversion.domain.usuarios.Usuario;
import com.eduardocruzdev.forolastversion.domain.usuarios.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;

    public RegisterController(UsuarioRepository usuarioRepository,PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario) {

        String passwordCifrada = passwordEncoder.encode(datosRegistroUsuario.clave());

        // Crear un nuevo usuario a partir de los datos de registro
        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario,passwordCifrada));

        // Construir la URL del recurso recién creado
        URI url = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();

        // Crear la respuesta con los datos del usuario registrado
        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(
                usuario.getLogin()
        );

        return ResponseEntity.created(url).body(datosRespuestaUsuario);
    }



}

package com.eduardocruzdev.forolastversion.controller;

import com.eduardocruzdev.forolastversion.domain.topico.*;
import com.eduardocruzdev.forolastversion.domain.usuarios.DatosRespuestaUsuario;
import com.eduardocruzdev.forolastversion.domain.usuarios.Usuario;
import com.eduardocruzdev.forolastversion.domain.usuarios.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name="bearer-key")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        // Buscar el Usuario existente por su id (o login)
        String login = datosRegistroTopico.usuario().getLogin(); // Obtienes el login del usuario
        Usuario usuario = usuarioRepository.findByLogin(login);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        // Crear el Topico y asociarlo con el Usuario
        Topico topico = new Topico(datosRegistroTopico);  // Crear Topico con datos
        topico.setUsuario(usuario);  // Asignar el Usuario al Topico
        // Guardar el Topico en la base de datos
        topico = topicoRepository.save(topico);

        // Paso 4: Crear la respuesta con los datos del Topico y Usuario
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getContenido(),
                topico.getFechaCreacion(),
                new DatosRespuestaUsuario(topico.getUsuario().getLogin())
        );
        // Construir la URL de ubicación y devolver la respuesta
        URI url = uriComponentsBuilder.path("/Topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping
    public ResponseEntity <Page<DatosListadoTopico>> listarTopicos(@PageableDefault(size = 2) Pageable paginacion) {
        return ResponseEntity.ok().body(topicoRepository.findByActivoTrue(paginacion).map(DatosListadoTopico::new));
    }
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity buscarTopico(@PathVariable Long id){
        Topico Topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosRespuestaTopico(Topico.getId(),Topico.getTitulo(),Topico.getContenido(),
                Topico.getFechaCreacion(), new DatosRespuestaUsuario(Topico.getUsuario().getLogin()));
        return ResponseEntity.ok().body(datosTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteDatosTopico(@PathVariable Long id){
        Topico Topico = topicoRepository.getReferenceById(id);
        Topico.cerrarTopico();
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        Topico Topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        Topico.actualizarTopico(datosActualizarTopico);
        return ResponseEntity.ok(new DatosRespuestaTopico(Topico.getId(),Topico.getTitulo(),Topico.getContenido(),
                Topico.getFechaCreacion(), new DatosRespuestaUsuario(Topico.getUsuario().getLogin())));
    }
    
    
}
